package tn.esprit.pidevarctic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.Service.OrdonnancementEvent;
import tn.esprit.pidevarctic.entities.Event;
import tn.esprit.pidevarctic.entities.User;

import java.util.*;

@RestController
@RequestMapping("/Ordonnancement")
public class OrdonnancementController {

        @Autowired
        OrdonnancementEvent ordonnancementEvent;

        @Autowired
    UserRepository userRepository;


    @GetMapping("/user/{userId}")
    public List<Event> getEventsByUserAndSortByDate(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return ordonnancementEvent.getEventsByUserAndSortByDate(user);
        }
        return new ArrayList<>();
    }
}
