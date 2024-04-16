package tn.esprit.pidevarctic.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidevarctic.Service.IUserbehavior;
import tn.esprit.pidevarctic.entities.UserBehavior;
import tn.esprit.pidevarctic.entities.UserInteractionRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user-interactions")
public class UserBehaviorController {
    private IUserbehavior userbehaviorservice;



    @PostMapping
    public ResponseEntity<Void> logUserInteraction(@RequestBody UserInteractionRequest request) {
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setUserId(request.getUserId());
        userBehavior.setTimestamp(request.getDate());
        userBehavior.setActionType(request.getActionType());
        userBehavior.setResourceId(request.getResourceId());
        userbehaviorservice.logUserBehavior(userBehavior);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
