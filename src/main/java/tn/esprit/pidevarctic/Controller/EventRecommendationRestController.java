package tn.esprit.pidevarctic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidevarctic.Service.EventRecommendationService;
import tn.esprit.pidevarctic.entities.Event;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/recommendation")
public class EventRecommendationRestController {
    EventRecommendationService eventRecommendationService;

    @Autowired
    EventRecommendationService eventRecommendationServicee;

    @GetMapping("/users/{userId}/events/recommended")
    public List<Event> recommendEvents(@PathVariable Long userId) {
        return eventRecommendationServicee.recommendEvents(userId);
    }
}

