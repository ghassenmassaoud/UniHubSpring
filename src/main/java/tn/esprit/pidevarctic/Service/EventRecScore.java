package tn.esprit.pidevarctic.Service;

import ch.qos.logback.core.joran.sanity.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.EventRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Event;
import tn.esprit.pidevarctic.entities.User;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventRecScore {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

//    @Autowired
//    public EventRecScore(EventRepository eventRepository, UserRepository userRepository) {
//        this.eventRepository = eventRepository;
//        this.userRepository = userRepository;
//    }

    public List<Map.Entry<Event, Double>> recommendEvents(Long userId) {
        // Définir les préférences de l'utilisateur
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Map<String, Integer> userPreferences = getUserPreferences(user);

        // Obtenir la liste des événements disponibles
        List<Event> events = eventRepository.findAll();

        // Calculer les scores de similarité
        Map<Event, Double> similarityScores = calculateSimilarityScores(userPreferences, events);

        // Créer une liste de paires d'événements et de scores de similarité
        List<Map.Entry<Event, Double>> eventSimilarityPairs = similarityScores.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return eventSimilarityPairs;
    }

    private Map<String, Integer> getUserPreferences(User user) {
        // Utiliser les événements auxquels l'utilisateur a déjà participé pour définir ses préférences
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
}
