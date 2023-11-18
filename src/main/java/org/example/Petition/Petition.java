package org.example.Petition;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.category.Category;
import org.example.receiver.Receiver;
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
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User initiator;
    @Column(name = "[name]")
    private String name;
    @Column(name = "date")
    private Date date;
    @Column(name = "currSigns")
    private int currSigns;
    @Column(name = "neededSigns")
    private int neededSigns;
    @Column(name = "[description]")
    private String description;
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName= "receiver_id")
    private Receiver receiver;
    @Column(name = "status")
    private String status;
    @Column(name = "location")
    private String location;
    @ManyToMany
    @JoinTable(
            name = "User_Petition",
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
