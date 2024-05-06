package tn.esprit.pidevarctic.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Config.AuthenticationResponse;
import tn.esprit.pidevarctic.Config.JwtService;
import tn.esprit.pidevarctic.Service.IUserService;
import tn.esprit.pidevarctic.Service.RoleService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.ChangePasswordObj;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.User;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
public class AuthRestController {
    private IUserService userService;

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    @PostMapping("/register/{numRole}")
    public ResponseEntity addUser(@RequestBody User user, @PathVariable Long numRole){

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user, numRole));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody User user){

      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      user.getEmail(),user.getPassword()
              )
      );
      User userAuth = userService.authenticate(user.getEmail());
      if(userAuth.isFirstAuth()){

          return ResponseEntity.status(HttpStatus.OK).body(AuthenticationResponse.builder()
                  .accessToken(jwtService.generateToken(userAuth))
                  .refreshToken(jwtService.generateRefreshToken(userAuth))
                  .build()
);
      }
      return ResponseEntity.status(HttpStatus.OK).body(AuthenticationResponse.builder()
              .accessToken(jwtService.generateToken(userAuth))
              .refreshToken(jwtService.generateRefreshToken(userAuth))
              .build()
      );
    }
    @GetMapping("/refresh-token")
    public String refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
       return userService.refreshToken(request, response);
    }
    @GetMapping("/ActivateAccount")
    public ResponseEntity ActivateAccount(@RequestParam("token") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(userService.ActivateAccount(token)));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
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
