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

    /*

    Event createEvent(Event event);
    Event updateEvent(Long id, Event updatedEvent);
    void deleteEvent(Long id);
    Event getEventById(Long id);

    public List<Event> getEvents();

    public List<Event> getEventsByClubId(Long clubId);

     */



    }





