package tn.esprit.pidevarctic.Service;

import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.EventRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Event;
import tn.esprit.pidevarctic.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class EventRecommendationService {
    EventRepository eventRepository;
    UserRepository userRepository;

    public EventRecommendationService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<Event> recommendEvents(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Map<String, Integer> userPreferences = getUserPreferences(user);

        List<Event> events = eventRepository.findAll();

        Map<Event, Double> similarityScores = calculateSimilarityScores(userPreferences, events);

        List<Event> recommendedEvents = sortEventsBySimilarityScore(similarityScores);

        return recommendedEvents;
    }

    private Map<String, Integer> getUserPreferences(User user) {
        Set<Event> userEvents = user.getEvents();
        Map<String, Integer> userPreferences = new HashMap<>();

        for (Event event : userEvents) {
            String eventName = event.getEventName();
            userPreferences.put(eventName, userPreferences.getOrDefault(eventName, 0) + 1);
        }

        return userPreferences;
    }

    private Map<Event, Double> calculateSimilarityScores(Map<String, Integer> userPreferences, List<Event> events) {
        Map<Event, Double> similarityScores = new HashMap<>();

        for (Event event : events) {
            String eventName = event.getEventName();
            int eventFrequency = userPreferences.getOrDefault(eventName, 0);
            double similarityScore = (double) eventFrequency / events.size();
            similarityScores.put(event, similarityScore);
        }

        return similarityScores;
    }

    private List<Event> sortEventsBySimilarityScore(Map<Event, Double> similarityScores) {
        return similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Event, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
