package tn.esprit.pidevarctic.Service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.*;
import tn.esprit.pidevarctic.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ClubService implements IClubService {
    //@Autowired
    private final ClubRepository clubRepository;


    //@Autowired
    private final EventRepository eventRepository;

    //@Autowired
    private final ProfileRepository profileRepository;

    //@Autowired
    private final UserRepository userRepository;

    @Override
    public Club createClub(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public Club updateClub(Club club) {
        if (clubRepository.existsById(club.getIdClub())) {
            return clubRepository.save(club);
        } else {
            return null;
        }
    }

    @Override
    public void deleteClub(Long idClub) {
        clubRepository.deleteById(idClub);
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


    @Override
    public Club addClub(Club club, Long numUser) {
        User user = userRepository.findById(numUser).orElse(null);
        Profile profile = new Profile();
        ProfileId profileId = new ProfileId();
        profileId.setClub(club);
        profileId.setStudent(user);
        profile.setPId(profileId);
        profile.setProfileState(State.ACTIVE);
        profile.setProfileRole(ProfileRole.ADMIN);
        profile.setMark(10);
        clubRepository.save(club);
        profileRepository.save(profile);
        return club;
    }


        @Override
    public Club assignUserToClub(Long numClub, Long numUser) {
        User user = userRepository.findById(numUser).orElse(null);
        Club club = clubRepository.findById(numClub).orElse(null);
        Profile profile = new Profile();
        ProfileId profileId = new ProfileId();
        profileId.setClub(club);
        profileId.setStudent(user);
        profile.setPId(profileId);
        profile.setProfileState(State.ACTIVE);
        profile.setProfileRole(ProfileRole.MEMBER);
        profile.setMark(90);
        profileRepository.save(profile);
        return club;
    }




    @Override
    public Club getClubById(Long numClub) {
        Club club = clubRepository.findById(numClub).orElse(null);
        System.out.println(club.getProfiles().toString());
        return club;
    }

    public Profile getProfileById(ProfileId profileId) {
        return profileRepository.findById(profileId).orElse(null);
    }

    @Override
    public List<Club> getClubsForMember(Long userId) {
    return profileRepository.findClubsByUserId(userId);
    }

    @Override
    public Profile getProfileByUserIdAndClubId(Long userId, Long clubId) {
        return profileRepository.findProfileByUserIdAndClubId(userId, clubId);
    }





}
