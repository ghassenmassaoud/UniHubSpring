package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}