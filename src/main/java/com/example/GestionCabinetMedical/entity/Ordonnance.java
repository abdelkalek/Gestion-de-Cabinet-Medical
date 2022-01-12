package com.example.GestionCabinetMedical.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DynamicUpdate
public class Ordonnance {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String description;
    @OneToMany
    private List<LineOrdonance> lineOrdonanceSet;
    @OneToOne()
    private Consultation consultationOrd;

}
