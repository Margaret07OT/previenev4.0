package com.previene.laboratorio.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.previene.laboratorio.Model.Analisis;
import com.previene.laboratorio.Repository.AnalisisRepository;

@Service
public class AnalisisServicelmpl implements AnalisisService {
    
    @Autowired
    private AnalisisRepository analisisRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Analisis> findAll(){
        return analisisRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Analisis findById(int id_analisis){
        return analisisRepository.findById(id_analisis);
    }
    
    @Override
    @Transactional
    public Analisis save(Analisis analisis) {
        return analisisRepository.save(analisis);
    }

    @Override
    public void update(Analisis analisis) {
        analisisRepository.save(analisis);
    }
}
