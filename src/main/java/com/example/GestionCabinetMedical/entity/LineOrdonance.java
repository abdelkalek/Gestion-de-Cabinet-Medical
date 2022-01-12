package com.example.GestionCabinetMedical.entity;
import javax.persistence.*;
import java.util.Set;

@Entity
public class LineOrdonance {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private Double Qty;
    private String desc_consommation;

    public LineOrdonance(Long id, Double qty, String desc_consommation) {
        this.id = id;
        Qty = qty;
        this.desc_consommation = desc_consommation;
    }
    public LineOrdonance() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getQty() {
        return Qty;
    }
    public void setQty(Double qty) {
        Qty = qty;
    }
    public String getDesc_consommation() {
        return desc_consommation;
    }
    public void setDesc_consommation(String desc_consommation) {
        this.desc_consommation = desc_consommation;
    }
    @ManyToOne
    private Ordonnance ordonnanceMed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lineOrdonanceMed")
    private Set<Medicament> medicamentSet;
}
