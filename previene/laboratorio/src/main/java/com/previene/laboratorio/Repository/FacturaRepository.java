package com.previene.laboratorio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    
    Factura findByCita(Cita cita);
}
