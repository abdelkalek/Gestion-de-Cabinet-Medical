package com.example.GestionCabinetMedical.service;

import com.example.GestionCabinetMedical.entity.Role;
import com.example.GestionCabinetMedical.entity.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();

    /*Gerer Patient*/
    User getID(Long id);

    User savePatient(User user);

    List<User> getPatients();

    User getPatientByNmae(String username);

    User updateUser(User user);

    void deleteUserBYid(Long id);

}
