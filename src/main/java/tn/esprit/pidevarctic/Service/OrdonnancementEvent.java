package tn.esprit.pidevarctic.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.EventRepository;
import tn.esprit.pidevarctic.entities.Event;
import tn.esprit.pidevarctic.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrdonnancementEvent {

    private final EventRepository eventRepository;

    public List<Event> getEventsByUserAndSortByDate(User user) {
        List<Event> events = eventRepository.findByStudentsContains(user);
        events.sort(Comparator.comparing(Event::getEventDate));

        for (Event event : events) {
            event.calculateRemainingDays();
        }

        return events;
    }

    public void sendNotificationOneDay(Event event) {
        // Implémentez la logique d'envoi de notification ici
        System.out.println("Notification envoyée à l'utilisateur pour l'événement : " + event.getEventName());
    }

    @Scheduled(fixedRate = 86400000) // Exécuté toutes les 24 heures
    public void checkAndSendNotifications() {
        List<Event> allEvents = eventRepository.findAll();
        for (Event event : allEvents) {
            event.calculateRemainingDays();
            if (event.getRemainingDays() == 1) {
                sendNotificationOneDay(event);
            }
        }
    }
}



