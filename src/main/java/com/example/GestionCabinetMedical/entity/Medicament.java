package com.example.GestionCabinetMedical.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DynamicUpdate
public class Medicament {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String notice;
    @Enumerated(EnumType.STRING)
    private Unite unite;
    @ManyToOne
    private LineOrdonance lineOrdonanceMed;
}
