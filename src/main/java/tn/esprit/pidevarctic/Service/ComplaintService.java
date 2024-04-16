package tn.esprit.pidevarctic.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.ComplaintRepository;
import tn.esprit.pidevarctic.Repository.DemandRepository;
import tn.esprit.pidevarctic.entities.Complaint;
import tn.esprit.pidevarctic.entities.ComplaintType;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ComplaintService implements IComplaintService{
    @Autowired
    private ComplaintRepository complaintRepository;
    @Override
    public Complaint addComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public Complaint updateComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public void deleteComplaint(Long complaintId) {
        Optional<Complaint> c = complaintRepository.findById(complaintId);
        //Verify if the demand exist or not
        if (c.isPresent())
        {
            complaintRepository.deleteById(complaintId);
        }
        else {
            throw new IllegalArgumentException("Reclamation inexistante");

        }

    }

    @Override
    public Complaint getComplaintById(Long complaintId) {
        return complaintRepository.findById(complaintId).orElse(null);
    }

    @Override
    public Set<Complaint> getComplaintByType(ComplaintType complaintType) {
        return complaintRepository.getComplaintByComplaintType(complaintType);
    }

    @Override
    public Set<Complaint> getAllComplaints() {
        return (Set<Complaint>) complaintRepository.findAll();
    }
}

