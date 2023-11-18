package org.example.JoiningTables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Petition.Petition;
import org.example.user.User;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User_Petition")
public class User_Petition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user_id;
    @ManyToOne
    @JoinColumn(name = "petition_id", referencedColumnName = "petition_id")
    private Petition petition_id;
    private Date sign_date;
}
