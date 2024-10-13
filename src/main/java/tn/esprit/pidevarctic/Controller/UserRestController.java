package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.IUserService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.ChangePasswordObj;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

@RequestMapping("/user")
//@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
public class UserRestController {
    private IUserService userService;
    @GetMapping("/hello")
    public String helloUser(){
        return "hello user";
    }
    @PutMapping("/update/{numUser}")
    public User updateUser(@RequestBody User user,@PathVariable Long numUser){
        return userService.updateUser(user,numUser);
    }
//    @PutMapping("/update")
//    public User updateUser(@RequestBody User user){
//        return userService.updateUser(user);
//    }
    @GetMapping("/get/{numUser}")
    public User getUser(@PathVariable Long numUser){
        return userService.getUserById(numUser);
    }
    @GetMapping("/getUser/{emailUser}")
    public User getUserByEmail(@PathVariable String emailUser){
        return userService.getUserByEmail(emailUser);
    }
    @DeleteMapping("/delete/{numUser}")
    public void removeUser(@PathVariable Long numUser){
        userService.deleteUser(numUser);
    }
    @GetMapping("/allByRole/{numRole}")
    public ResponseEntity getAll(@PathVariable Long numRole){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser(numRole));
    }
    @PostMapping("/changePass/{numUser}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordObj changePasswordObj, @PathVariable Long numUser){
        return userService.changePassword(changePasswordObj,numUser);
    }
    @PostMapping("/firstAuth/{numUser}")
    public ResponseEntity<String> firstAuth(@RequestBody ChangePasswordObj changePasswordObj, @PathVariable Long numUser){
        User user = getUser(numUser);
        user.setFirstAuth(false);
        updateUser(user,numUser);
        return userService.changePassword(changePasswordObj,numUser);
    }
    @PutMapping("/BanOrActive/{numUser}")
    public String banOrActive( @PathVariable Long numUser){
        return userService.banOrActive(numUser);
    }



}
