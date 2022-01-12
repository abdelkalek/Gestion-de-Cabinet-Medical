package com.example.GestionCabinetMedical.contoller;

import com.example.GestionCabinetMedical.entity.Rdv;
import com.example.GestionCabinetMedical.service.RdvServiceIplm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rdv")
public class RdvController {
    @Autowired
    private RdvServiceIplm service;

    @GetMapping()
    public ResponseEntity<List<Rdv>> getAllRdvs() {
        try {
            List<Rdv> rdvList = new ArrayList<>();
            service.getList().forEach(rdvList::add);
            if (rdvList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(rdvList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Rdv> getRdvById(@PathVariable("id") long id) {
        Optional<Rdv> rdv = Optional.ofNullable(service.searchedById(id));
        if (rdv.isPresent()) {
            return new ResponseEntity<>(rdv.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Rdv> createRdv(@RequestBody Rdv rdv) {
        try {
            Rdv _rdv = service.ajouter(rdv);
            return new ResponseEntity<>(_rdv, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Rdv> updateRdv(@PathVariable("id") long id, @RequestBody Rdv rdv) {
        Optional<Rdv> rdvData = Optional.ofNullable(service.searchedById(id));
        if (rdvData.isPresent()) {
            return new ResponseEntity<>(service.ajouter(rdv), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRdv(@PathVariable("id") long id) {
        try {
            service.deleteByid(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("annulerRdv/{id}")
    public ResponseEntity<Rdv> annulerRdv(@PathVariable("id") long id) {
        Optional<Rdv> rdvData = Optional.ofNullable(service.searchedById(id));
        if (rdvData.isPresent()) {
            service.annulerRdv(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("validerRdv/{id}")
    public ResponseEntity<Rdv> validerRdv(@PathVariable("id") long id) {
        Optional<Rdv> rdvData = Optional.ofNullable(service.searchedById(id));
        if (rdvData.isPresent()) {
            service.validerRdv(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}