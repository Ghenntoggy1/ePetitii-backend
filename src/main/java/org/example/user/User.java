package org.example.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Petition.Petition;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User1")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "idnp")
    private String  idnp;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthDay")
    private Date birthDay;

    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(mappedBy = "signers")
    private Set<Petition> petitions = new HashSet<>();
}
