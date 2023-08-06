package com.previene.laboratorio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Boleta;
import com.previene.laboratorio.Model.Cita;

public interface BoletaRepository extends JpaRepository<Boleta, Integer>{
    
    Boleta findByCita(Cita cita);
}
