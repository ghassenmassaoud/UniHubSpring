package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Config.JwtService;
import tn.esprit.pidevarctic.Service.RoleService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.ChangePasswordObj;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.User;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/auth")
@AllArgsConstructor
@RestController
public class AuthRestController {
    private UserService userService;

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    @PostMapping("/register/{numRole}")
    public ResponseEntity<User> addUser(@RequestBody User user, @PathVariable Long numRole){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user,numRole)) ;
    }
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody User user){

      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      user.getEmail(),user.getPassword()
              )
      );
      User userAuth = userService.authenticate(user.getEmail());
      if(userAuth.isFirstAuth()){
          return ResponseEntity.status(HttpStatus.OK).body("Reset Password and jwtToken="+jwtService.generateToken(userAuth));
      }
      return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(userAuth));
    }
    @PostMapping("/ActivateAccount")
    public String ActivateAccount(@RequestParam("token") String token){
    return userService.ActivateAccount(token);
    }
    @PostMapping("/ForgetPassword")
    public String forgetPassword(@RequestBody User user){
        return userService.forgetPassword(user.getEmail());
    }
    @PostMapping("/VerifCode")
    public String verifCode(@RequestBody User user){
        return userService.verifCode(user.getCode());
    }
    @PostMapping("/ResetPassword/{numUser}")
    public String ResetPassword(@RequestBody ChangePasswordObj changePasswordObj,@PathVariable Long numUser){
        return userService.resetPassword(changePasswordObj,numUser);
    }

}
