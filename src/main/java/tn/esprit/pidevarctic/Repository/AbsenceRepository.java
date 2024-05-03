package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Absence;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}