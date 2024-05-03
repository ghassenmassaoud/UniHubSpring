package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}