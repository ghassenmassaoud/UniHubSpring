package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Club;
import tn.esprit.pidevarctic.entities.Event;

import java.util.List;

public interface IClubService {

    Club createClub(Club club);
    Club updateClub(Club club);
    void deleteClub(Long idClub);
    Club getClubById(Long idClub);
    List<Club> getAllClubs();
    List<Event> getEventsByClub(Long clubId);

}
