package tn.esprit.pidevarctic.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.EventRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Event;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private EventRepository eventRepository;
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
/*
    @Override
    public void addEventToUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
        user.getEvents().add(event);
        userRepository.save(user);
    }

    @Override
    public void removeEventFromUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
        user.getEvents().remove(event);
        userRepository.save(user);
    }

 */

    @Override
    public void addEventToUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        if (!user.getRoles().contains(new Role("ROLE_STUDENT")));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
        user.getEvents().add(event);
        userRepository.save(user);
    }

    @Override
    public void removeEventFromUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
        if (!user.getRoles().contains(new Role("ROLE_STUDENT")));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
        user.getEvents().remove(event);
        userRepository.save(user);
    }




}
