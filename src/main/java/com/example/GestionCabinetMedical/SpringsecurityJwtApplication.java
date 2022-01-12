package com.example.GestionCabinetMedical;

import com.example.GestionCabinetMedical.entity.Etat;
import com.example.GestionCabinetMedical.entity.Rdv;
import com.example.GestionCabinetMedical.entity.Role;
import com.example.GestionCabinetMedical.entity.User;
import com.example.GestionCabinetMedical.service.RdvServiceIplm;
import com.example.GestionCabinetMedical.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;

@SpringBootApplication
public class SpringsecurityJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityJwtApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner run(RdvServiceIplm rdvService, UserService userService) {
        return args -> {
       userService.saveRole(new Role(null, "ROLE_PATIENT"));
            userService.saveRole(new Role(null, "ROLE_SECRETAIRE"));
            userService.saveRole(new Role(null, "ROLE_MEDECIN"));
        userService.saveUser(new User(null, "Sandor Clegane", "Sandor", "0123",new ArrayList<>()));
            userService.saveUser(new User(null, "Sansa Stark", "Sansa", "0123", new ArrayList<>()));
            userService.saveUser(new User(null, "Jon  Stark", "Jon", "1234", new ArrayList<>()));
            userService.addRoleToUser("Jon", "ROLE_MEDECIN");
                userService.addRoleToUser("Sansa", "ROLE_SECRETAIRE");
            userService.addRoleToUser("Sandor", "ROLE_PATIENT");
            rdvService.ajouter(new Rdv("12/05/2022", "15:00", Etat.Brouillion, userService.getUser("Sandor")));
            rdvService.ajouter(new Rdv("12/05/2020", "08:00", Etat.Brouillion, userService.getUser("Sandor")));
            rdvService.ajouter(new Rdv("12/05/2021", "07:00", Etat.Brouillion, userService.getUser("Sandor")));

        };

    }

}
