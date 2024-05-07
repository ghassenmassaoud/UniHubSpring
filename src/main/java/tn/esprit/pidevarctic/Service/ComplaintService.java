package tn.esprit.pidevarctic.Service;
import java.util.regex.Pattern;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.ComplaintRepository;
import tn.esprit.pidevarctic.Repository.DemandRepository;
import tn.esprit.pidevarctic.entities.Complaint;
import tn.esprit.pidevarctic.entities.ComplaintType;
import tn.esprit.pidevarctic.entities.Demand;


import java.util.List;
import java.util.Optional;
import java.util.Set;
@RequiredArgsConstructor
@Service
public class ComplaintService implements IComplaintService{

    private final ComplaintRepository complaintRepository;
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

    @Override
    public List<Complaint> getAllSeenComplaint() {
        Boolean status = true;
        List<Complaint> SeenComplaints = complaintRepository.findComplaintByStatus(status);
        return SeenComplaints;
    }

    @Override
    public List<Complaint> getAllUnseenComplaint() {
        Boolean status = false;
        List<Complaint> UnSeenComplaints = complaintRepository.findComplaintByStatus(status);
        return UnSeenComplaints;
    }



    //////////////////////////////////// Recommendation/////////////////////////

    @Override
    public String Recommend(String text) {
        if (text == null || text.isEmpty()) {
            return "Neutral";
        }
        text = text.toLowerCase();
        String[] words = {"teacher", "grade", "class", "absence"};

        for (String word : words) {
            if (text.contains(word)) {
                if (word.equals(".*teacher.*")) {
                    return "Well received, we are going to look into the matter as soon as possible.";
                }
                if (word.equals(".*grade.*")) {
                    return "Well received, we are going to look into the matter as soon as possible.";
                }
                if (word.equals(".*class.*")) {
                    return "Well received, we are going to check the class availability and respond to you.";
                }
                if (word.equals(".*absence.*")) {
                    return "Well received, we are going to discuss the matter with your teacher and correct the mistake.";
                }
            }
        }

        return text;
    }
    public Complaint SetAsSeen(long complaintId) {
        Complaint rec =complaintRepository.findById(complaintId).orElse(null);

        rec.setStatus(true);
        return complaintRepository.save(rec);
    }
}




