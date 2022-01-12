package com.example.GestionCabinetMedical.service;

import com.example.GestionCabinetMedical.entity.Role;
import com.example.GestionCabinetMedical.entity.User;
import com.example.GestionCabinetMedical.repository.RoleRepositroy;
import com.example.GestionCabinetMedical.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepositroy roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User {} found in the database", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user  {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }
    @Override
    public List<User> getUsers() {
        log.info("Fetching All Users");
        return userRepo.findAll();
    }

    @Override
    public User getID(Long id) {
        return userRepo.getById(id);
    }
    @Override
    public User savePatient(User user) {
        log.info("Saving new Patient  {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepo.findByName("ROLE_PATIENT")));
        return userRepo.save(user);
    }
    @Override
    public List<User> getPatients() {
        log.info("Fetching All Patients");
        List<User> usersList = new ArrayList<>();
        userRepo.findAll().forEach(user -> {
            user.getRoles().forEach(role ->
            {
                if (role.getName().equals("ROLE_PATIENT")) {
                    usersList.add(user);

                }
            });
        });
        return usersList;
    }
    @Override
    public User getPatientByNmae(String username) {
        try {
            User patient = userRepo.findByUsername(username);
            if (this.isRoleUSer(patient, "ROLE_PATIENT")) {
                return patient;
            } else
                return null;
        } catch (Exception e) {
            log.warn("Patient does Not exist", e.getMessage());
            return null;
        }
    }
    @Override
    public User updateUser(User user) {
        log.info("Update User Process");
        try {
            User patient = userRepo.getById(user.getId());
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(patient.getName());
            }
            if (user.getUsername() == null || user.getUsername().isEmpty())
                user.setUsername(patient.getUsername());
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(patient.getPassword());
                log.info("password vide");
            }else{
                user.setPassword(passwordEncoder.encode(patient.getPassword()));
                log.info("password Crypted");
            }
            user.setRoles(patient.getRoles());
            user.setConsultationSet(patient.getConsultationSet());
            userRepo.save(user);
            return user;
        } catch (Exception e) {
            log.warn("Patient does Not Updated" + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteUserBYid(Long id) {
        userRepo.deleteById(id);
    }

    public boolean isRoleUSer(User user, String role) {
        boolean isRole = false;
        Collection<Role> Roles = user.getRoles();
        for (Role r : Roles) {
            if (r.getName().equals(role)) {
                isRole = true;
            }
        }
        return isRole;
    }
}
