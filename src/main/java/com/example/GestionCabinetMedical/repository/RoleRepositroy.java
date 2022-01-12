package com.example.GestionCabinetMedical.repository;
import com.example.GestionCabinetMedical.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositroy extends JpaRepository<Role,Long> {
Role findByName(String name);
}

