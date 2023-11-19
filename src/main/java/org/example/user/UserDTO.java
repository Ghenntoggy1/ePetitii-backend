package org.example.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Location.Location;
import org.example.Location.LocationDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class UserDTO {

    private String name;
    private String surname;
    private String idnp;
    private LocationDTO region;
    private Date birthDay;


    public static UserDTO userDtoMapping(User user){
        UserDTO userDto = new UserDTO();
        userDto.setIdnp(user.getIdnp());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setBirthDay(user.getBirthDay());
        userDto.setRegion(LocationDTO.mapFromLocation(user.getLocation()));
        return userDto;
    }
    public static List<UserDTO> mapUsersToDTOs(Set<User> users) {
        return users.stream()
                .map(UserDTO::userDtoMapping)
                .collect(Collectors.toList());
    }

}
