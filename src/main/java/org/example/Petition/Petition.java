package org.example.Petition;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.category.Category;
import org.example.user.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Petitions")
public class Petition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private User initiator;
    private String name;
    private Date date;
    private int currSigns;
    private int neededSigns;
    private String description;
    private String reciver;
    private String statut;
    @ManyToMany
    @JoinTable(
            name = "user_petition",
            joinColumns = @JoinColumn(name = "petition_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> signers = new HashSet<>();
    private String deadLine;
    @ManyToMany
    @JoinTable(
            name = "petition_category",
            joinColumns = @JoinColumn(name = "petition_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

}
