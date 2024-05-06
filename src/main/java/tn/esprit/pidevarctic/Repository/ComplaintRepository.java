package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Complaint;
import tn.esprit.pidevarctic.entities.ComplaintType;
import tn.esprit.pidevarctic.entities.Demand;

import java.util.List;
import java.util.Set;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    public Set<Complaint> getComplaintByComplaintType(ComplaintType complaintType);

    public List<Complaint> findComplaintByStatus(boolean Status);
}