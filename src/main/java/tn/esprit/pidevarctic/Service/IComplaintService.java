package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Complaint;
import tn.esprit.pidevarctic.entities.ComplaintType;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.DemandType;

import java.util.List;

public interface IComplaintService {
    public Complaint addComplaint(Complaint complaint);
    public Complaint updateComplaint(Complaint complaint);
    public void deleteComplaint(Long ComplaintId);
    public Complaint getComplaintById(Long complaintId);
    List<Complaint> getComplaintByType(ComplaintType complaintType);
    List<Complaint> getAllComplaints();
}
