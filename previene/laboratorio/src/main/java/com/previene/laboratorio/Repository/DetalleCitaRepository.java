package com.previene.laboratorio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.DetalleCita;

public interface DetalleCitaRepository extends JpaRepository<DetalleCita, Integer>{
    
    List<DetalleCita> findByCita(Cita cita);

    void deleteByCita(Cita cita);
}
