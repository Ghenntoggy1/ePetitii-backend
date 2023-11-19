package org.example.email;

import org.example.Petition.Petition;
import org.example.Petition.PetitionDTO;
import org.example.Petition.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin
@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PetitionService petitionService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendMail(emailDetails);
    }

    @GetMapping("/subscribe")
    public ResponseEntity<List<PetitionDTO>> getTop3PetitionsByCategoryId(@RequestBody EmailDetails emailDetails) {
        List<PetitionDTO> emailPetitions = petitionService.getTop3PetitionsByCategoryIds(emailDetails.getCategories()).stream().map(PetitionDTO::mapPetitionToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(emailPetitions);
    }
}
