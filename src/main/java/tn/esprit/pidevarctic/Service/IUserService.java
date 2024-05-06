package tn.esprit.pidevarctic.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import tn.esprit.pidevarctic.entities.ChangePasswordObj;
import tn.esprit.pidevarctic.entities.User;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    User addUser(User user,Long numRole) ;
    User updateUser(User user,Long numUser);
    public User updateUser(User user);
    void deleteUser(Long numUser);
    User getUserById(Long numUser);
    User getUserByEmail(String email);
    List<User> getAllUser(Long roleId);
    User authenticate(String email);
    String ActivateAccount(String token);
    public ResponseEntity<String> changePassword(ChangePasswordObj changePasswordObj, Long idUser);
    public String forgetPassword(String email);
    public String resetPassword(ChangePasswordObj changePasswordObj,Long idUser);
    public String verifCode(int code);
    public String banOrActive(Long idUser);
    public String refreshToken(HttpServletRequest request, HttpServletResponse response)throws IOException;
//    public void saveUser(User user);
//    public void disconnect(User user);
//    public List<User> findConnectedUsers();

}
