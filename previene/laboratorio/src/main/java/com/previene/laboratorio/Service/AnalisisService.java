package com.previene.laboratorio.Service;

import java.util.List;

import com.previene.laboratorio.Model.Analisis;

public interface AnalisisService {
    
    public List<Analisis> findAll();

    public Analisis findById(int id_analisis);

    public Analisis save(Analisis analisis);

    public void update(Analisis analisis);
}
