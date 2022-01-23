package com.example.GestionCabinetMedical.repository;
import com.example.GestionCabinetMedical.entity.Rdv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IRdvRepository extends JpaRepository<Rdv,Long> {
}
