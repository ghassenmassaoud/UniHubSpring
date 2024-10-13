package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Complaint;
import tn.esprit.pidevarctic.entities.ComplaintType;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;

import java.util.List;
import java.util.Set;

public interface IComplaintService {
    public Complaint addComplaint(Complaint complaint);
    public Complaint updateComplaint(Complaint complaint);
    public void deleteComplaint(Long ComplaintId);
    public Complaint getComplaintById(Long complaintId);
    Set<Complaint> getComplaintByType(ComplaintType complaintType);
    Set<Complaint> getAllComplaints();
    List<Complaint> getAllSeenComplaint();

    List<Complaint> getAllUnseenComplaint();

    public String Recommend(String text) ;
    public Complaint SetAsSeen(long complaintId);
}
