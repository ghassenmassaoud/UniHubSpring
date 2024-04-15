package tn.esprit.pidevarctic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.entities.Complaint;
import tn.esprit.pidevarctic.entities.ComplaintType;
import tn.esprit.pidevarctic.Service.IComplaintService;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintRestController {
    @Autowired
    private IComplaintService complaintService;

    @PostMapping
    public Complaint addComplaint(@RequestBody Complaint complaint) {
        return complaintService.addComplaint(complaint);
    }

    @PutMapping("/{id}")
    public Complaint updateComplaint(@PathVariable Long id, @RequestBody Complaint complaint) {
        complaint.setComplaintId(id);
        return complaintService.updateComplaint(complaint);
    }

    @DeleteMapping("/{id}")
    public void deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
    }

    @GetMapping("/{id}")
    public Complaint getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id);
    }

    @GetMapping("/type/{ComplaintType}")
    public List<Complaint> getComplaintByType(@PathVariable ComplaintType ComplaintType) {
        return complaintService.getComplaintByType(ComplaintType);
    }

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }
}

