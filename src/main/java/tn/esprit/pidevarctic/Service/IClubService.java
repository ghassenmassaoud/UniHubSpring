package tn.esprit.pidevarctic.Service;

import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.pidevarctic.entities.*;

import java.util.List;
import java.util.Set;

public interface IClubService {

    Club createClub(Club club);
    Club updateClub(Club club);
    void deleteClub(Long idClub);
    //Club getClubById(Long idClub);

    Club getClubById(Long numClub);
    List<Club> getAllClubs();
    List<Event> getEventsByClub(Long clubId);

    Club addClub(Club club, Long numUser);

    Club assignUserToClub(Long numClub, Long numUser);


    Profile getProfileById(@PathVariable ProfileId profileId);


    public List<Club> getClubsForMember(Long userId);
    Profile getProfileByUserIdAndClubId(Long userId, Long clubId);




    }
