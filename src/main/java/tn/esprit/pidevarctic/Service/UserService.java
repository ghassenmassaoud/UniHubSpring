package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Config.EmailService;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.ChangePasswordObj;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.User;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    @Override
    public User addUser(User user,Long numRole) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("user already exist");
        }
        Role role = roleService.getRoleById(numRole);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnableToken( UUID.randomUUID().toString());
        String link = "http://localhost:8081/api/auth/ActivateAccount?token=" + user.getEnableToken();
        String body = emailService.buildEmail(user.getFirstName(), link);
        emailService.sendSimpleEmail(
                user.getEmail(),
                "Please confirm your account",
                body
        );
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user,Long numUser) {
        User userExist = userRepository.findById(numUser).orElse(null);
        return userRepository.save(userExist);
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
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User authenticate(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public String ActivateAccount(String token) {
        User user = userRepository.findByEnableToken(token).orElse(null);
        if(user==null){
            return "user not enabled invalide token";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "User is enabled now";
    }
    @Override
    public String changePassword(ChangePasswordObj changePasswordObj,Long idUser){
        User user = userRepository.findById(idUser).orElse(null);
        if(!passwordEncoder.matches(changePasswordObj.getOldPassword(),user.getPassword())){
            return "Old password is incorrect";
        }
        if(!changePasswordObj.getNewPassword().equals(changePasswordObj.getConfirmationPassword())){
            return "passwords are not the same";
        }
        user.setPassword(passwordEncoder.encode(changePasswordObj.getNewPassword()));
        userRepository.save(user);
        return "password changed successfully";

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
        emailService.sendSimpleEmail(
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


}
