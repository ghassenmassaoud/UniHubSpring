package tn.esprit.pidevarctic.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevarctic.Service.EmailService;
import tn.esprit.pidevarctic.Service.UserService;
import tn.esprit.pidevarctic.entities.Complaint;
import tn.esprit.pidevarctic.entities.ComplaintType;
import tn.esprit.pidevarctic.Service.IComplaintService;
import tn.esprit.pidevarctic.entities.Demand;
import tn.esprit.pidevarctic.entities.User;

import java.util.List;
import java.util.Set;
@RequiredArgsConstructor

@RestController
@RequestMapping("/api/complaints")

@CrossOrigin(origins = "http://localhost:4200")

public class ComplaintRestController {

    private final IComplaintService complaintService;

    private final UserService userservice ;


    private final EmailService emailService;

    @PostMapping
    public Complaint addComplaint(@RequestBody Complaint complaint) {

        emailService.sendEmail("abdouunaffeti647@gmail.com", "complaint", complaint.getDescription());

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
    public Set<Complaint> getComplaintByType(@PathVariable ComplaintType ComplaintType) {
        return complaintService.getComplaintByType(ComplaintType);
    }

    @GetMapping("/All")
    public Set<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }


    @GetMapping("/seen")
    public List<Complaint> getAllSeenComplaint() {
        return complaintService.getAllSeenComplaint();
    }


    @GetMapping("/unseen")
    public List<Complaint> getAllUnseenComplaint() {
        return complaintService.getAllUnseenComplaint();
    }
    @PostMapping("/{id}/seen")
    public Complaint setComplaintAsSeen(@PathVariable Long id) {
        return complaintService.SetAsSeen(id);
    }


























   /* @PostMapping()
    public Complaint addComplaint(@RequestBody Complaint complaint) {
        String recommendation = Recommend(complaint.getDescription());

        emailService.sendEmail("recipient@example.com", "Complaint Recommendation", recommendation);

        return complaintService.addComplaint(complaint);
    }*/






    @PostMapping("/recommend")

   public String recommend(@RequestBody String description) {
        if (description == null || description.isEmpty()) {
            return "Neutral";
        }

        description = description.toLowerCase();
        String[] words = {"teacher", "grade", "class", "absence"};

        for (String word : words) {
            if (description.contains(word)) {
                if (word.equals("teacher")) {
                    String message = "Well received, we are going to look into the matter as soon as possible.";
                    emailService.sendEmail("abdouunaffeti647@gmail.com", "Recommendation", message);
                    return message;
                } else if (word.equals("grade")) {
                    String message = "Well received, we are going to look into the matter as soon as possible.";
                    emailService.sendEmail("abdouunaffeti647@gmail.com", "Recommendation", message);
                    return message;
                } else if (word.equals("absence")) {
                    String message = "Well received, we are going to look into the matter and correct the mistake if proved.";
                    emailService.sendEmail("abdouunaffeti647@gmail.com", "Recommendation", message);
                    return message;
                } else if (word.equals("class")) {
                    String message = "Well received, we are going to check the class availability and respond to you.";
                    emailService.sendEmail("abdouunaffeti647@gmail.com", "Recommendation", message);
                    return message;
                }
            }
        }
        emailService.sendEmail("abdouunaffeti647@gmail.com", "Recommendation",description );

        return description;
    }

    /*public String Recommend(@RequestBody String text) {
        if (text == null || text.isEmpty()) {
            return "Neutral";
        }

        text = text.toLowerCase(); // Convertir en minuscules

        String[] words = {".*teacher.*", ".*grade.*", ".*class.*", ".*absence.*", ".*resources.*", ".*organization.*", ".*instructor.*", ".*professor.*"};
        String[] patterns = {
                ".*issue with my teacher.*",
                ".*problem with my instructor.*",
                ".*concerns about my professor.*",
                ".*absence related issue.*",
                ".*class changing problem.*",
                ".*lack of resources.*",
                ".*lack of organization.*",
                // Ajoutez plus de modèles ici
                // "votre_motif_ici",
        };
        String[] messages = {
                "Well received, we are going to look into the matter as soon as possible.",
                "Well received, we are going to look into the matter as soon as possible.",
                "Well received, we are going to look into the matter and correct the mistake if proven.",
                "Well received, we will address the issue regarding your absence.",
                "Well received, we will assist you with the class changing problem.",
                "Well received, we will address the lack of resources.",
                "Well received, we will address the issue regarding the lack of organization.",
                // Ajoutez plus de messages ici
                // "votre_message_ici",
        };

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (text.contains(word)) {
                String pattern = patterns[i];
                String message = messages[i];

                if (text.matches(word)) {
                    // Envoyer un e-mail
                    emailService.sendEmail("abdouunaffeti647@gmail.com", "Recommendation", message);
                    return message;
                }
            }
        }

        return "Neutral";
    }*/
}

