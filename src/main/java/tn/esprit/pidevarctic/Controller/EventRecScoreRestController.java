package tn.esprit.pidevarctic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidevarctic.Service.EventRecScore;
import tn.esprit.pidevarctic.entities.Event;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/RecScore")
public class EventRecScoreRestController {
    EventRecScore eventRecScore;

    public EventRecScoreRestController(EventRecScore eventRecScore) {
        this.eventRecScore = eventRecScore;
    }

    @GetMapping("/users/{userId}/events/recommended")
    public List<Map.Entry<Event, Double>> recommendEvents(@PathVariable Long userId) {
        return eventRecScore.recommendEvents(userId);
    }

}
