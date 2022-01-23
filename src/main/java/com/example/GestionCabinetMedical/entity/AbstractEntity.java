package com.example.GestionCabinetMedical.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.Instant;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Instant crationDate;
    @JsonIgnore
    @LastModifiedDate
    private Instant lastModifiedDate;

}
