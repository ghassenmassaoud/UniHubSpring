package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Event;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    //List<Event> findByClubId(Long clubId);
    List<Event> findByClubIdClub(Long clubId);

    List<Event> findByStudents(User student);

    List<Event> findByStudentsContains(User user);

//    List<Event> findByKeyword(String keyword);
//    //List<Event> findByClubId(Long clubId);
}