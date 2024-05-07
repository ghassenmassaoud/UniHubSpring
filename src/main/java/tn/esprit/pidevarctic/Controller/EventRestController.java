package tn.esprit.pidevarctic.Controller;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Repository.ClubRepository;
import tn.esprit.pidevarctic.Service.EventService;
import tn.esprit.pidevarctic.entities.Club;
import tn.esprit.pidevarctic.entities.Event;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/events")
public class EventRestController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ClubRepository clubRepository;

    @PostMapping("/createEvent/{clubId}")
    public ResponseEntity<Event> createEventForClub(@PathVariable Long clubId, @RequestBody Event newEvent) {

        return new ResponseEntity<>( eventService.createEventForClub(clubId, newEvent), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping("/createEventForUser/{userId}")
    public Event createEvent(@RequestBody Event event, @PathVariable Long userId) {
        return eventService.createEvent(event, userId);
    }

    @GetMapping("/eventsByStudent/{studentId}")
    public List<Event> getEventsByStudent(@PathVariable Long studentId) {
        return eventService.getEventsByStudent(studentId);
    }

    @PostMapping("/addStudentToEvent/{eventId}/{studentId}")
    public void addStudentToEvent(@PathVariable Long eventId, @PathVariable Long studentId) {
        eventService.addStudentToEvent(eventId, studentId);
    }


    @PostMapping("/removeStudentFromEvent")
    public void removeStudentFromEvent(@RequestParam("eventId") Long eventId, @RequestParam("studentId") Long studentId) {
        eventService.removeStudentFromEvent(eventId, studentId);
    }

    @GetMapping("/getEvents")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/getEventByClubId/{clubId}")
    public List<Event> getEventsByClubId(@PathVariable Long clubId) {
        return eventService.getEventsByClubId(clubId);
    }

}


    /*
    @GetMapping("/getEventByClubId/{clubId}")
    public List<Event> getEventsByClubId(@PathVariable Long clubId) {
        return eventService.getEventsByClubId(clubId);
    }



    @GetMapping("/getEventByClubIdAndEventId/{clubId}/{eventId}")
    public Event getEventByClubIdAndEventId(@PathVariable Long clubId, @PathVariable Long eventId) {
        return eventService.getEventByClubIdAndEventId(clubId, eventId);
    }

    @PutMapping("/updateEventByClubId/{clubId}/{eventId}")
    public Event updateEventByClubId(@PathVariable Long clubId, @PathVariable Long eventId, @RequestBody Event updatedEvent) {
        return eventService.updateEventByClubId(clubId, eventId, updatedEvent);
    }

    @DeleteMapping("/deleteEventByClubIdAndEventId/{clubId}/{eventId}")
    public ResponseEntity<Void> deleteEventByClubIdAndEventId(@PathVariable Long clubId, @PathVariable Long eventId) {
        eventService.deleteEventByClubIdAndEventId(clubId, eventId);
        return ResponseEntity.noContent().build();
    }




   ////////////////////////////////////////////////////////////////////


    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping("/addEvent")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/updateEvent/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/deleteEvent/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

     */




