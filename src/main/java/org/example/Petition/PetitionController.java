package org.example.Petition;

import org.example.CustomException.DuplicateSignException;
import org.example.CustomException.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/petition")
public class PetitionController {
    private final PetitionService petitionService;

    @Autowired
    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }
    @GetMapping
    public ResponseEntity<List<PetitionDTO>> getAllPetitions(
            @RequestParam(required = false) List<Integer> category_ids,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer region,
            @RequestParam(required = false) String search
    ) {
        List<PetitionDTO> petitions = petitionService.getPetitionsWithFilter(category_ids, status, region, search).stream()
                .map(PetitionDTO::mapPetitionToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(petitions);
    }
    @GetMapping("/{petitionId}")
    public ResponseEntity<PetitionDTO> getPetitionById(@PathVariable Integer petitionId) {
        PetitionDTO petitionDTO =  petitionService.getPetitionById(petitionId)
                .map(PetitionDTO::mapPetitionToDTO).get();
        return ResponseEntity.ok(petitionDTO);
    }
    @PostMapping("/{petitionId}/sign")
    public ResponseEntity<String> signPetition(@PathVariable Integer petitionId, @RequestBody SignPetitionRequest signRequest) throws DuplicateSignException, NotFoundException {
        boolean success = petitionService.signPetition(petitionId, signRequest.getUser_idnp());

        if (success) {
            return ResponseEntity.ok("Petition signed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to sign petition");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> createPetition(@RequestBody PetitionCreateRequest petitionCreateRequest) {
        Integer petition_id = petitionService.createPetition(petitionCreateRequest);
        return ResponseEntity.ok(petition_id);

    }
}
