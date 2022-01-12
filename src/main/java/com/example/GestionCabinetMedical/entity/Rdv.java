package com.example.GestionCabinetMedical.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@DynamicUpdate
public class Rdv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String heure;
    @Enumerated(EnumType.STRING)
    private Etat etat;
    @ManyToOne
    private User userRdv;

    public Rdv(String date, String heure, Etat etat, User userRdv) {
        this.date = date;
        this.heure = heure;
        this.etat = etat;
        this.userRdv = userRdv;
    }

    public Rdv() {
    }
}
