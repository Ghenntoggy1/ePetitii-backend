package org.example.category;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Petition.Petition;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer category_id;

    @Column(name = "category_name_ro")
    private String category_name_ro;

    @Column(name = "category_name_en")
    private String category_name_en;

    @Column(name = "category_name_ru")
    private String category_name_ru;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private Set<Petition> petitions = new HashSet<>();
    @Override
    public int hashCode() {
        return Objects.hash(category_id, category_name_ro, category_name_en, category_name_ru);
    }


}
