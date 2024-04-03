package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.ChangePasswordObj;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

@RequestMapping("/user")
@AllArgsConstructor
@RestController
public class UserRestController {
    private UserService userService;
    @GetMapping("/hello")
    public String helloUser(){
        return "hello user";
    }
    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @GetMapping("/get/{numUser}")
    public User getUser(@PathVariable Long numUser){
        return userService.getUserById(numUser);
    }
    @DeleteMapping("/delete/{numUser}")
    public void removeUser(@PathVariable Long numUser){
        userService.deleteUser(numUser);
    }
    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAllUser();
    }
    @PostMapping("/changePass/{numUser}")
    public String changePassword(@RequestBody ChangePasswordObj changePasswordObj, @PathVariable Long numUser){
        return userService.changePassword(changePasswordObj,numUser);
    }
    @PostMapping("/firstAuth/{numUser}")
    public String firstAuth(@RequestBody ChangePasswordObj changePasswordObj, @PathVariable Long numUser){
        return userService.changePassword(changePasswordObj,numUser);
    }

}
