package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Repository.UserRepository;
//import tn.esprit.pidevarctic.Service.PostService;
import tn.esprit.pidevarctic.Service.Recommandation;
import tn.esprit.pidevarctic.entities.Post;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/recom")
public class RecommendationController {
    private Recommandation recommandation;
    private UserRepository userRepository;


    @GetMapping("/user")
    public ResponseEntity<List<Post>> getRecommendationsForUser(@RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Post> recommendations = recommandation.generateRecommendations(user);
        return ResponseEntity.ok(recommendations);
    }



}
