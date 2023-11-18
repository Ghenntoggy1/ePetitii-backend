package org.example.email;

import org.example.Petition.Petition;
import org.example.Petition.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PetitionService petitionService;

    @PostMapping("/sendMail")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendMail(emailDetails);
    }

    @GetMapping("/subscribe")
    public ResponseEntity<List<Petition>> getTop3PetitionsByCategoryId(@RequestBody EmailDetails emailDetails) {
        List<Petition> emailPetitions = petitionService.getTop3PetitionsByCategoryIds(emailDetails.getCategories());

        return ResponseEntity.ok(emailPetitions);
    }
}
