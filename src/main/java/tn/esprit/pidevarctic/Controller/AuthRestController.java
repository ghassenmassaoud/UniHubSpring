package tn.esprit.pidevarctic.Controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Config.JwtService;
import tn.esprit.pidevarctic.Service.RoleService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.User;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/auth")
@AllArgsConstructor
@RestController
public class AuthRestController {
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    @PostMapping("/register/{numRole}")
    public User addUser(@RequestBody User user,@PathVariable Long numRole){
        Role role = roleService.getRoleById(numRole);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user);
    }
    @PostMapping("/login")
    public String authenticate(@RequestBody String email,@RequestBody String password){
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      email,password
              )
      );
      User user = userService.authenticate(email);
      return jwtService.generateToken(user);
    }
}
