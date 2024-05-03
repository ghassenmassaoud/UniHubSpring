package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.User;

import java.util.List;

public interface IUserService {
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Long numUser);
    User getUserById(Long numUser);
    List<User> getAllUser();

}
