package com.previene.laboratorio.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Paciente;

public interface CitaRepository extends JpaRepository<Cita, Integer>{
    
    List<Cita> findAll();

    Cita findById(int id_cita);

    List<Cita> findByPaciente(Paciente paciente);

    void deleteById(int id_cita);

    List<Cita> findByFecha(Date fecha);
}
