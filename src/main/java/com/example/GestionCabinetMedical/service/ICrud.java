package com.example.GestionCabinetMedical.service;

import java.util.List;

public interface ICrud <T>{
   T ajouter(T t);
   List<T> getList();
   T searchedById(Long searchedId);
   void deleteByid(Long id);
   void update(T t);
}
