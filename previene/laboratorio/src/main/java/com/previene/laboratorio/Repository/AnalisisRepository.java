package com.previene.laboratorio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Analisis;

public interface AnalisisRepository extends JpaRepository<Analisis, Integer>{
    
    List<Analisis> findAll();

    Analisis findById(int id_analisis);

}
