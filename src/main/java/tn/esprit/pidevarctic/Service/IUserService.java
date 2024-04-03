package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.ChangePasswordObj;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

public interface IUserService {
    User addUser(User user,Long numRole);
    User updateUser(User user);
    void deleteUser(Long numUser);
    User getUserById(Long numUser);
    List<User> getAllUser();
    User authenticate(String email);
    String ActivateAccount(String token);
    public String changePassword(ChangePasswordObj changePasswordObj, Long idUser);

}
