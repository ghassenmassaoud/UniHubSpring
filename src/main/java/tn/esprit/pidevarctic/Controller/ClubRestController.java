package tn.esprit.pidevarctic.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.ClubService;
import tn.esprit.pidevarctic.Service.IClubService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.*;

import java.util.List;
import java.util.Map;
import java.util.Set;



@RestController
@RequestMapping("/clubs")
public class ClubRestController {

    @Autowired
    private IClubService clubService;




    @PostMapping("/createClub")
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        Club createdClub = clubService.createClub(club);
        return new ResponseEntity<>(createdClub, HttpStatus.CREATED);
    }

    @GetMapping("/getClub/{idClub}")
    public ResponseEntity<Club> getClubById(@PathVariable Long idClub) {
        Club club = clubService.getClubById(idClub);
        if (club != null) {
            return new ResponseEntity<>(club, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getClubs")
    public ResponseEntity<List<Club>> getAllClubs() {
        List<Club> clubs = clubService.getAllClubs();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }

    @PutMapping("/updateClub/{idClub}")
    public ResponseEntity<Club> updateClub(@PathVariable Long idClub, @RequestBody Club club) {
        Club updatedClub = clubService.updateClub(club);
        if (updatedClub != null) {
            return new ResponseEntity<>(updatedClub, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteClub/{idClub}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long idClub) {
        clubService.deleteClub(idClub);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/ListEventByClub/{idclub}/events")
    public ResponseEntity<List<Event>> getEventsByClub(@PathVariable Long idclub) {
        List<Event> events = clubService.getEventsByClub(idclub);
        return ResponseEntity.ok(events);
    }
    /*
    @GetMapping("/getClub/{numClub}")
    public Club getClub( @PathVariable Long numClub){
        return clubService.getClubById(numClub);
    }

     */


    @PostMapping("/addClub/{numUser}")
    public Club addClub(@RequestBody Club club, @PathVariable Long numUser) {
        return clubService.addClub(club, numUser);
    }


    @PostMapping("/assignUserToClub/{numClub}/{numUser}")
    public Club assignUserToClub(@PathVariable Long numClub, @PathVariable Long numUser) {
//        String profileStateStr = requestBody.get("profileState");
//        String profileRoleStr = requestBody.get("profileRole");
//        int mark = Integer.parseInt(requestBody.get("mark"));

//        State profileState = State.valueOf(profileStateStr);
//        ProfileRole profileRole = ProfileRole.valueOf(profileRoleStr);

        return clubService.assignUserToClub(numClub, numUser);
    }


    @GetMapping("/profile/{profileId}")
    public Profile getProfileById(@PathVariable ProfileId profileId) {
        return clubService.getProfileById(profileId);
    }

    @GetMapping("/getClubsForMember/{userId}")
    public List<Club> getClubsForMember(@PathVariable Long userId) {
        return clubService.getClubsForMember(userId);
    }

    @GetMapping("/getProfileById/{userId}/{clubId}")
    public Profile getProfileById(@PathVariable Long userId, @PathVariable Long clubId) {
        return clubService.getProfileByUserIdAndClubId(userId, clubId);
    }


//    @GetMapping("/getProfileById/{userId}/{clubId}")
//    public Profile getProfileById(@PathVariable Long userId, @PathVariable Long clubId) {
//        return clubService.getProfileByUserIdAndClubId(userId, clubId);
//    }



//    @GetMapping("/assigned-clubs/{userId}")
//    public List<Club> getClubsAssignedToUser(@PathVariable Long userId) {
//        return clubService.getClubsAssignedToUser(userId);
//    }



    }






    /*



    @PostMapping("/addClub/{numUser}")
    public Club addClub(@RequestBody Club club, @PathVariable Long numUser){
        return clubService.addClub(club,numUser);
    }
    @PostMapping("/assignUserToClub/{numClub}/{numUser}")
    public Club assignUserToClub(@PathVariable Long numClub, @PathVariable Long numUser) {
        return clubService.assignUserToClub(numClub, numUser);
    }

     */



