package org.example.Location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Integer id;
    private String name;
    private Map<String, String> i18n;

    public static LocationDTO mapFromLocation(Location location) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ro", location.getLocation_name_ro());
        map.put("ru", location.getLocation_name_ru());
        map.put("en", location.getLocation_name_en());
        return new LocationDTO(location.getLocation_id(), location.getLocation_name_ro(), map);
    }
}
