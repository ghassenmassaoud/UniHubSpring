package tn.esprit.pidevarctic.Service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.ClubRepository;
import tn.esprit.pidevarctic.Repository.DemandRepository;
import tn.esprit.pidevarctic.Repository.EventRepository;
import tn.esprit.pidevarctic.Repository.ProfileRepository;
import tn.esprit.pidevarctic.entities.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService implements IClubService {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Club createClub(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public Club updateClub(Club club) {
        if (clubRepository.existsById(club.getIdClub())) {
            return clubRepository.save(club);
        } else {
            return null; // or throw exception, handle as needed
        }
    }

    @Override
    public void deleteClub(Long idClub) {
        clubRepository.deleteById(idClub);
    }

    @Override
    public Club getClubById(Long idClub) {
        Optional<Club> clubOptional = clubRepository.findById(idClub);
        return clubOptional.orElse(null);
    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    public List<Event> getEventsByClub(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new EntityNotFoundException("Club not found with id " + clubId));
        return eventRepository.findByClubIdClub(clubId);
    }






}
