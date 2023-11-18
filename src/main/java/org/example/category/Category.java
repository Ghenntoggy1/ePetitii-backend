package org.example.category;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Petition.Petition;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categories")
public class Category {
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
    private Set<Petition> petitions = new HashSet<>();
}
