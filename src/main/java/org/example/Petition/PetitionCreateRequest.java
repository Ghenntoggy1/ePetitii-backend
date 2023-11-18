package org.example.Petition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetitionCreateRequest {
    private String initiator_idnp;
    private String name;
    private String description;
    private Integer receiver;
    private Integer region;
    private List<Integer> categories;
}

