package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pidevarctic.entities.Club;
import tn.esprit.pidevarctic.entities.Profile;
import tn.esprit.pidevarctic.entities.ProfileId;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, ProfileId> {

    //Profile findByPId_Student_IdAndPId_Club_Id(Long userId, Long clubId);

    //List<Profile> findByStudent(User user);
    //List<Profile> findByPId_Student(User student);
    //List<Profile> findByStudent(User student);
   // List<Profile> findByPId_student(User student);
    //List<Profile> findByPId_Student(User student);
    @Query("SELECT p.pId.club FROM Profile p WHERE p.pId.student.idUser= :userId")
    List<Club> findClubsByUserId(Long userId);

    @Query("SELECT p FROM Profile p WHERE p.pId.student.idUser = :userId AND p.pId.club.idClub = :clubId")
    Profile findProfileByUserIdAndClubId(Long userId, Long clubId);


    @Query("SELECT p FROM Profile p WHERE p.pId.student= :student AND p.pId.club= :club")
    Profile findByPId_StudentAndPId_Club(User student, Club club);

    @Query("SELECT p FROM Profile p WHERE p.pId.student.idUser= :userId")
    Profile findByPId_StudentIdUser(Long userId);


    @Query("SELECT p FROM Profile p WHERE p.pId.student.idUser = :userId AND p.pId.club.idClub = :clubId")
    List<Profile> findProfilesByUserIdAndClubId(Long userId, Long clubId);

    @Query("SELECT p FROM Profile p WHERE p.pId.student.idUser = :userId AND p.pId.club.idClub = :clubId")
    Profile findFirstByUserIdAndClubId(@Param("userId") Long userId, @Param("clubId") Long clubId);


}