package com.previene.laboratorio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    
    List<Resultado> findByCita(Cita cita);
    
    void deleteByCita(Cita cita);

    Resultado findById(int id);
}
