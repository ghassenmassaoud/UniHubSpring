package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.RessourceSpace;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

public interface RessourceSpaceRepository extends JpaRepository<RessourceSpace, Long> {
   // public List<RessourceSpace> findByStudents(User user);

}