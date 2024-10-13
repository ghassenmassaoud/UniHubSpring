package tn.esprit.pidevarctic.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Config.EmailServices;
import tn.esprit.pidevarctic.Config.JwtService;
import tn.esprit.pidevarctic.Config.PasswordStrengthService;
import tn.esprit.pidevarctic.Config.UserException;
import tn.esprit.pidevarctic.Repository.RoleRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private EmailServices emailServices;
    private JwtService jwtService;
    private PasswordStrengthService passwordStrengthService;
    @Override
    public User addUser(User user,Long numRole) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists){
            throw new IllegalArgumentException("User already exists");
        }
        if(passwordStrengthService.getPasswordStrength(user.getPassword()).equals(PasswordStrength.STRONG)){
        Role role = roleService.getRoleById(numRole);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnableToken( UUID.randomUUID().toString());
//        String link = "http://localhost:8081/api/auth/ActivateAccount?token=" + user.getEnableToken();
//        String body = emailServices.buildEmail(user.getFirstName(), link);
//        emailServices.sendSimpleEmail(
//                user.getEmail(),
//                "Please confirm your account",
//                body
//        );
        return userRepository.save(user);
    }else if(passwordStrengthService.getPasswordStrength(user.getPassword()).equals(PasswordStrength.MODERATE)){
            throw new IllegalArgumentException("Password is MODERATE");
        }
        else{
        throw new IllegalArgumentException("Password is WEAK");}
    }

    @Override
    public User updateUser(User user,Long numUser) {
        User userExist = userRepository.findById(numUser).orElse(null);
        for (Field field : user.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(user);
                if (value != null) {
                    field.set(userExist, value);
                }
            } catch (IllegalAccessException e) {
                // Handle exception if necessary
            }
        }

        return userRepository.save(userExist);
    }
    @Override
    public User updateUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long numUser) {
        userRepository.deleteById(numUser);

    }

    @Override
    public User getUserById(Long numUser) {
        return userRepository.findById(numUser).orElse(null);
    }
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> getAllUser(Long idRole) {
        Role role = roleRepository.findById(idRole).orElse(null);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return userRepository.findByRoles(roles);
    }

    @Override
    public User authenticate(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    public String refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return "no token passed";
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                return "access token = "+accessToken+" , refresh token = "+refreshToken;
            }

        }
        return "user not exist";
    }


    @Override
    public String ActivateAccount(String token) {
        User user = userRepository.findByEnableToken(token).orElse(null);
        if(user==null){
            return "user not enabled invalide token";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "http://localhost:4200/#/authentication/signin";
    }
    @Override
    public ResponseEntity<String> changePassword(ChangePasswordObj changePasswordObj,Long idUser){

        User user = userRepository.findById(idUser).orElse(null);
        System.out.println(passwordEncoder.matches(changePasswordObj.getOldPassword(),user.getPassword()+"helooo"));
        if(!passwordEncoder.matches(changePasswordObj.getOldPassword(),user.getPassword())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( "Old password is incorrect");
        }
        if(!changePasswordObj.getNewPassword().equals(changePasswordObj.getConfirmationPassword())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("passwords are not the same");
        }
        if(!(changePasswordObj.getNewPassword().length() >= 8 && changePasswordObj.getNewPassword().
                matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).*"))){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(" Password does not meet complexity requirements");
        }
        user.setPassword(passwordEncoder.encode(changePasswordObj.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("password changed successfully");

    }
    @Override
    public String forgetPassword(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return email+" Not Found";
        }
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        user.setCode(code);
        emailServices.sendSimpleEmail(
                user.getEmail(),
                "verif code to reset password",
                String.valueOf(code)
        );
        return "code sent successfully";
    }
    @Override
    public String verifCode(int code){
        User user = userRepository.findByCode(code).orElse(null);
        if(user!=null){
            return "code valid";
        }
        return "invalid code";
    }
    @Override
    public String resetPassword(ChangePasswordObj changePasswordObj,Long idUser){
        User user = userRepository.findById(idUser).orElse(null);

        if(!changePasswordObj.getNewPassword().equals(changePasswordObj.getConfirmationPassword())){
            return "passwords are not the same";
        }
        user.setPassword(passwordEncoder.encode(changePasswordObj.getNewPassword()));
        userRepository.save(user);
        return "password changed successfully";

    }
    @Override
    public String banOrActive(Long idUser){
        User user = userRepository.findById(idUser).orElse(null);

        if(user!=null){
            user.setLocked(!user.isAccountNonLocked());
            userRepository.save(user);
            return "user is "+ (user.isAccountNonLocked() ? "activated" : "banned");
        }

        return "User not found";

    }
//    public void saveUser(User user) {
//        user.setState(State.ONLINE);
//        userRepository.save(user);
//    }
//
//    public void disconnect(User user) {
//        var storedUser = userRepository.findById(user.getIdUser()).orElse(null);
//        if (storedUser != null) {
//            storedUser.setState(State.OFFLINE);
//            userRepository.save(storedUser);
//        }
//    }
//
//    public List<User> findConnectedUsers() {
//        return userRepository.findAllByState(State.ONLINE);
//    }
//
public List<Post> getUserFavoritePosts(User user) {
    // Assurez-vous que l'utilisateur et son ID ne sont pas null
    if (user == null || user.getIdUser() == null) {
        throw new IllegalArgumentException("Invalid user or user ID");
    }

    // Récupérez l'utilisateur de la base de données en fonction de son ID
    User fetchedUser = userRepository.findById(user.getIdUser())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    // Récupérez la liste des posts favoris de l'utilisateur
    Set<Post> favoritePosts = fetchedUser.getFavoritePosts();

    return new ArrayList<>(favoritePosts); // Convertissez l'ensemble en une liste pour la gestion plus facile
}
}
