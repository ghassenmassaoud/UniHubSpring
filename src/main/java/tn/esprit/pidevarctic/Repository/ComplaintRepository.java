package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Complaint;
import tn.esprit.pidevarctic.entities.ComplaintType;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    public List<Complaint> getComplaintByComplaintType(ComplaintType complaintType);
}