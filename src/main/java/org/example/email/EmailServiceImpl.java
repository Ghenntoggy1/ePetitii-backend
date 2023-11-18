package org.example.email;

import org.example.Petition.Petition;
import org.example.Petition.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private PetitionRepository petitionRepository;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendMail(EmailDetails details)
    {

        try {
            List<Petition> latestPetitions = petitionRepository.findAll();
            String emailContent = buildEmailContent(latestPetitions);

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getEmail());
            mailMessage.setSubject("Latest Petitions");
            mailMessage.setText(emailContent);


            javaMailSender.send(mailMessage);
            return "success";
        }

        catch (Exception e) {
            return "Error while Sending Mail";
        }

    }

    private String buildEmailContent(List<Petition> latestPetitions){
        StringBuilder content = new StringBuilder();
        String petitionURL = "http://localhost:8080/api/petitions/";

        content.append("Hey! \n\n Check some of the latest petitions you might be interested in: \n");
        for(Petition petition: latestPetitions){
            content.append(petition.getName() + "\n" + "already sign by: " + petition.getCurrSigns()
            + " people \n" + "sign it here: " + petitionURL+petition.getPetition_id()  + "\n\n");
        }
        return content.toString();
    }
}
