package com.example.GestionCabinetMedical.repository;
import com.example.GestionCabinetMedical.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
User findByUsername(String username);
}
