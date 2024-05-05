package tn.esprit.pidevarctic.Service;

import jakarta.persistence.EntityNotFoundException;
import tn.esprit.pidevarctic.entities.Club;
import tn.esprit.pidevarctic.entities.Event;

import java.time.LocalDate;
import java.util.List;

public interface IEventService {


    Event createEventForClub(Long clubId, Event newEvent);
    public Event createEvent(Event event, Long userId);

    public Event getEventById(Long idEvent);

    public List<Event> getEventsByStudent(Long studentId);

    public void addStudentToEvent(Long eventId, Long studentId);

    public void removeStudentFromEvent(Long eventId, Long studentId);

    public List<Event> getAllEvents();
    public List<Event> getEventsByClubId(Long clubId);



    }





