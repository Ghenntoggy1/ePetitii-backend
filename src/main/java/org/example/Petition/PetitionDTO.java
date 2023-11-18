package org.example.Petition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.category.Category;
import org.example.Receiver.Receiver;
import org.example.user.UserDTO;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetitionDTO {
    private Integer id;
    private UserDTO initiator;
    private String name;
    private Date date;
    private int currSigns;
    private int neededSigns;
    private String description;
    private Receiver receiver;
    private String status;
    private List<UserDTO> signers;
    private String deadLine;
    private List<Category> categories;

    public static PetitionDTO mapPetitionToDTO(Petition petition) {
        PetitionDTO petitionDTO = new PetitionDTO();
        petitionDTO.setId(petition.getPetition_id());
        petitionDTO.setInitiator(UserDTO.userDtoMapping(petition.getInitiator()));
        petitionDTO.setName(petition.getName());
        petitionDTO.setDate(petition.getDate());
        petitionDTO.setCurrSigns(petition.getCurrSigns());
        petitionDTO.setNeededSigns(petition.getNeededSigns());
        petitionDTO.setDescription(petition.getDescription());
        petitionDTO.setReceiver(petition.getReceiver());
        petitionDTO.setStatus(petition.getStatus());
        petitionDTO.setSigners(UserDTO.mapUsersToDTOs(petition.getSigners()));
        petitionDTO.setDeadLine(petition.getDeadLine());
        petitionDTO.setCategories(petition.getCategories().stream().toList());
        return petitionDTO;
    }
}
