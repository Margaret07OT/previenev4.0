package com.previene.laboratorio.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.previene.laboratorio.Model.Boleta;
import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Repository.BoletaRepository;

@Service
public class BoletaServicelmpl implements BoletaService{
    
    @Autowired
    private BoletaRepository boletaRepository;

    @Override
    @Transactional
    public Boleta save(Boleta boleta){
        return boletaRepository.save(boleta);
    }

    @Override
    @Transactional(readOnly = true)
    public Boleta findByCita(Cita cita){
        return boletaRepository.findByCita(cita);
    }
}
