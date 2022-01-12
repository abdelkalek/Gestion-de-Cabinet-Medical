package com.example.GestionCabinetMedical.service;
import com.example.GestionCabinetMedical.entity.Etat;
import com.example.GestionCabinetMedical.entity.Rdv;
import com.example.GestionCabinetMedical.repository.IRdvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RdvServiceIplm implements IRdvService {
    @Autowired
    private IRdvRepository rdvRepository;

    @Override
    public Rdv ajouter(Rdv rdv) {
        return this.rdvRepository.save(rdv);
    }

    @Override
    public List<Rdv> getList() {
        return this.rdvRepository.findAll();
    }

    @Override
    public Rdv searchedById(Long searchedId) {
        return this.rdvRepository.findById(searchedId).orElse(null);
    }

    @Override
    public void deleteByid(Long id) {
        this.rdvRepository.deleteById(id);
    }

    @Override
    public void update(Rdv rdv) {
        if (this.rdvRepository.findById(rdv.getId()).isPresent()) {
            this.rdvRepository.save(rdv);
        }
    }

    @Override
    public void annulerRdv(Long id) {
        if (this.rdvRepository.findById(id).isPresent()) {
            Rdv rdv = this.rdvRepository.getById(id);
            rdv.setEtat(Etat.Annuler);
            this.rdvRepository.save(rdv);
        }
    }

    @Override
    public void validerRdv(Long id) {
        if (this.rdvRepository.findById(id).isPresent()) {
            Rdv rdv = this.rdvRepository.getById(id);
            rdv.setEtat(Etat.Valide);
            this.rdvRepository.save(rdv);
        }
    }
}
