package com.example.GestionCabinetMedical.service;

import com.example.GestionCabinetMedical.entity.Rdv;

public interface IRdvService extends ICrud<Rdv> {
     void annulerRdv(Long id);
     void validerRdv(Long id);

}
