package com.previene.laboratorio.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Factura;
import com.previene.laboratorio.Repository.FacturaRepository;

@Service
public class FacturaServicelmpl implements FacturaService{
    
    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    @Transactional
    public Factura save(Factura factura){
        return facturaRepository.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findByCita(Cita cita){
        return facturaRepository.findByCita(cita);
    }
}
