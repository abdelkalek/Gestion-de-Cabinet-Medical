package com.example.GestionCabinetMedical.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consultation {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String date;
    private String heure;
    private String interpretation;
    @ManyToOne
    private User userc;
    @OneToOne()
    private Ordonnance ordonnanceCons;
}
