package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    @Override
    public User addUser(User user) {
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
        return userRepository.findByEmail(email);
    }
}
