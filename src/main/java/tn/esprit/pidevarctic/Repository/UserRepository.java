package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Speciality;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User>findBySpeciality(Speciality speciality);
}