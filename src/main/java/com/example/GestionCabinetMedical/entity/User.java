package com.example.GestionCabinetMedical.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private Long id;
    private String name;
    @Column(unique = true,updatable = true)
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Role> roles;
    @JsonIgnore
    @OneToMany (mappedBy = "userRdv")
    private Set<Rdv> rdvSet;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Consultation> consultationSet;
    public User(Long id, String name, String username, String password, Collection<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }


}