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
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

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

    /*
    @Override
    public Club getClubById(Long idClub) {
        Optional<Club> clubOptional = clubRepository.findById(idClub);
        return clubOptional.orElse(null);
    }

     */

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    public List<Event> getEventsByClub(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new EntityNotFoundException("Club not found with id " + clubId));
        return eventRepository.findByClubIdClub(clubId);
    }


    //CREER UN CLUB ET UN PROFILE AVEC LE ROLE PRESIDENT ET LE STATE ACTIVE
    //COTE ADMIN (LOG AVEC STUDENT )
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
//        profileRepository.save(profile);
        // profile.setClub(club);
        //profile.setStudent(user);
        clubRepository.save(club);
        profileRepository.save(profile);
        return club;
    }


    // AFFECTER UN USER A UN CLUB (AVEC CREATION DE PROFILE AU NOUVEL USER AFFECTé)
    // PROFILE AVEC profileRole = PRESIDENT

//    @Override
//    public Club assignUserToClub(Long numClub, Long numUser,State profileState,ProfileRole profileRole,int mark) {
//        User user = userRepository.findById(numUser).orElse(null);
//        Club club = clubRepository.findById(numClub).orElse(null);
//        Profile profile = new Profile();
//        ProfileId profileId = new ProfileId();
//        profileId.setClub(club);
//        profileId.setStudent(user);
//        profile.setPId(profileId);
//        profile.setProfileState(profileState);
//        profile.setProfileRole(profileRole);
//        profile.setMark( mark);
//        profileRepository.save(profile);
//        return club;
//    }

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
        return profileRepository. findProfileByUserIdAndClubId(userId, clubId);
    }


//    public Profile getProfileByUserIdAndClubId(Long userId, Long clubId) {
//        return profileRepository.findByPId_Student_IdAndPId_Club_Id(userId, clubId);
//    }



    /*

    @Override
    public List<Club> getClubsAssignedToUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<Profile> profiles = profileRepository.findByPId_Student(user);
        List<Club> assignedClubs = new ArrayList<>();
        for (Profile profile : profiles) {
            assignedClubs.add(profile.getPId().getClub());
        }
        return assignedClubs;
    }



     */

//    @Override
//    public List<Club> getClubsForMember(String userId) {
//        return profileRepository.findClubsByUserId(userId);
//    }






}
