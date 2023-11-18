package org.example.Receiver;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Receiver")
public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receiver_id")
    private Integer receiver_id;

    @Column(name = "receiver_name_ro")
    private String receiver_name_ro;

    @Column(name = "receiver_name_en")
    private String receiver_name_en;

    @Column(name = "receiver_name_ru")
    private String receiver_name_ru;
}
