package tn.esprit.pidevarctic.Controller;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.IRessourceService;
import tn.esprit.pidevarctic.Service.IUserbehavior;
import tn.esprit.pidevarctic.entities.Ressource;
import tn.esprit.pidevarctic.entities.UserBehavior;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user-interactions")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:4200")
public class UserBehaviorController {
    private IRessourceService ressourceService;
    private IUserbehavior userbehaviorservice;


    @GetMapping("/All")
    List<UserBehavior>getall(){

        return userbehaviorservice.getAll();
    }

    @PostMapping("/setCoockie")
    public ResponseEntity<?> setCookie(HttpServletResponse response , @RequestBody Long resourceId){
        try {
            Cookie cookie= new Cookie("ResourceId_"+resourceId,String.valueOf(resourceId))  ;

            cookie.setPath("/");
            cookie.setDomain("localhost");
            response.addCookie(cookie);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            // If there's an error, return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/cookie")
    public ResponseEntity<Void> userBehaviour(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                try {
                    Long resourceId = Long.parseLong(cookie.getValue());
                    Ressource resource = ressourceService.getRessourceById(resourceId);
                    if (resource != null) {
                        UserBehavior userBehavior = userbehaviorservice.getByResource(resourceId);
                        if (userBehavior != null) {
                            userBehavior.setVisited(userBehavior.getVisited() + 1);
                        } else {
                            userBehavior = new UserBehavior();
                            userBehavior.setResourceId(resourceId);
                            userBehavior.setTimestamp(LocalDateTime.now());
                            userBehavior.setVisited(1);
                            userBehavior.setSection(String.valueOf(resource.getRessourceSpace()));
                        }
                        userbehaviorservice.logUserBehavior(userBehavior);
                    }
                } catch (NumberFormatException ex) {
                    // Handle invalid cookie value
                    // Log or return an error response as needed
                }
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/populaire")
    public List<Ressource>getPopulaireResource(){
        List<UserBehavior>topBehaviour=userbehaviorservice.findtop();
        List<Ressource>resources = new ArrayList<>();
        for(UserBehavior userBehavior:topBehaviour){
            resources.add(ressourceService.getRessourceById(userBehavior.getResourceId()));

        }
        return resources;
    }
}