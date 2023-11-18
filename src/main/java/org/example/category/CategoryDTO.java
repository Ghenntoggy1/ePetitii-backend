package org.example.category;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Integer id;
    private String name;
    private Map<String, String> i18n;

    public static CategoryDTO mapCategoryToDTO(Category category) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ro", category.getCategory_name_ro());
        map.put("en", category.getCategory_name_en());
        map.put("ru", category.getCategory_name_ru());
        return new CategoryDTO(category.getCategory_id(), category.getCategory_name_ro(), map);

    }
}
