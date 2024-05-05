package tn.esprit.pidevarctic.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.ClubRepository;
import tn.esprit.pidevarctic.Repository.EventRepository;
import tn.esprit.pidevarctic.entities.Club;
import tn.esprit.pidevarctic.entities.Event;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.User;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EventService implements IEventService{


   //private  Club club ;


    //@Autowired
    private final  ClubRepository clubRepository;

    //@Autowired
    private final EventRepository eventRepository;


    private final UserService userService;

    @Override
    public Event createEventForClub(Long clubId, Event newEvent) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new EntityNotFoundException("Club not found"));

        newEvent.setClub(club);
        newEvent.setDecription("Set description");
        newEvent.setEventDate(LocalDate.now());
        Event savedEvent = eventRepository.save(newEvent);

        return savedEvent;

    }

    // ADD USER TO EVENT
    //REMOVE USER FROM EVENT

    @Override
    public Event createEvent(Event event, Long userId) {
        User user = userService.getUserById(userId);
        if (!user.getRoles().contains(new Role("ROLE_STUDENT")));
        return eventRepository.save(event);
    }

    @Override
    public Event getEventById(Long idEvent) {
        return eventRepository.findById(idEvent).orElse(null);
    }

    @Override
    public List<Event> getEventsByStudent(Long studentId) {
        User student = userService.getUserById(studentId);
        if (!student.getRoles().contains(new Role("ROLE_STUDENT")));
        return eventRepository.findByStudents(student);
    }

    @Override
    public void addStudentToEvent(Long eventId, Long studentId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        User student = userService.getUserById(studentId);
        if (event == null || student == null) {
            throw new EntityNotFoundException("Event or student not found");
        }
        if (!student.getRoles().contains(new Role("ROLE_STUDENT")));
        event.getStudents().add(student);
        eventRepository.save(event);
    }

    @Override
    public void removeStudentFromEvent(Long eventId, Long studentId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        User student = userService.getUserById(studentId);
        if (event == null || student == null) {
            throw new EntityNotFoundException("Event or student not found");
        }
        event.getStudents().remove(student);
        eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getEventsByClubId(Long clubId) {
        return eventRepository.findByClubIdClub(clubId);
    }







}
