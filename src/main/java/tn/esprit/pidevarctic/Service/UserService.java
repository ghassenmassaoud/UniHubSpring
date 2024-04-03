package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.ChangePasswordObj;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
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
        return userRepository.save(user);
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

}
