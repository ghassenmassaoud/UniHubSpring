package tn.esprit.pidevarctic.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidevarctic.EmailRequest.EmailRequest;
import tn.esprit.pidevarctic.Service.EmailService;
@RestController

public class EmailRestController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendEmail(request.getTo(), request.getSubject(), request.getText());
            System.out.println("aaa"+request.getTo()+ request.getSubject()+ request.getText());
            return "Email sent successfully.";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }}