package tn.esprit.pidevarctic.Controller;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/user-interactions")
public class UserBehaviorController {
    private IRessourceService ressourceService;
    private IUserbehavior userbehaviorservice;


    @GetMapping("/All")
    List<UserBehavior>getall(){

        return userbehaviorservice.getAll();
    }
    @PostMapping("/setCoockie")
    public ResponseEntity<?> setCookie(HttpServletResponse response , @RequestBody Long resourceId){
        Cookie cookie= new Cookie("ResourceId: "+resourceId,String.valueOf(resourceId))  ;
        cookie.setHttpOnly(true);
        cookie.setPath("/api/ressource/");

        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
@GetMapping("/cookie")
    public ResponseEntity<UserBehavior> userBehaviour (HttpServletRequest response){
        Cookie[] cookies=response.getCookies();
    Ressource resource=new Ressource();
    UserBehavior userBehavior=new UserBehavior();
        if(cookies !=null){
            for(Cookie cookie : cookies){
               resource=ressourceService.getRessourceById(Long.parseLong(cookie.getValue()));
               userBehavior=userbehaviorservice.getByResource(resource.getRessourceId());
               if(userBehavior != null){
                   userBehavior.setVisited(userBehavior.getVisited()+1);

              }else
              {
                   userBehavior.setResourceId(resource.getRessourceId());
               userBehavior.setTimestamp(LocalDateTime.now());
               userBehavior.setVisited(1);
               userBehavior.setSection(String.valueOf(resource.getRessourceSpace()));
              }
            userbehaviorservice.logUserBehavior(userBehavior);
        }


    }
return ResponseEntity.ok().build();
}
@GetMapping("/populaire")
    public List<Ressource>getPopulaireResource(){
        List<UserBehavior>topbehaviour=userbehaviorservice.findtop();
        List<Ressource>ressources = new ArrayList<>();
        for(UserBehavior userBehavior:topbehaviour){
            ressources.add(ressourceService.getRessourceById(userBehavior.getResourceId()));
        }
        return ressources;
 }
}
