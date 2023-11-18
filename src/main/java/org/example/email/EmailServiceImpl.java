package org.example.email;

import org.example.Petition.Petition;
import org.example.Petition.PetitionRepository;
import org.example.Petition.PetitionService;
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
    private PetitionService petitionService;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendMail(EmailDetails details)
    {

        try {
            List<Petition> latestPetitions = petitionService.getTop3PetitionsByCategoryIds(details.getCategories());
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
            e.printStackTrace();  // Log the exception to understand the root cause

            return "Error while Sending Mail";
        }

    }

    private String buildEmailContent(List<Petition> latestPetitions){
        StringBuilder content = new StringBuilder();
        String petitionURL = "http://172.20.115.183:3000/petitions/";

        content.append(" Hey! \nCheck some of the latest petitions you might be interested in: \n");
        for(Petition petition: latestPetitions){
            content.append(petition.getName() + " - " + "Already signed by: " + petition.getCurrSigns()
            + " people \n" + "Sign it too here: " + petitionURL+petition.getPetition_id()  + "\n\n");
        }
        return content.toString();
    }
}
