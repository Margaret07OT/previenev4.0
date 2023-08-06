package com.previene.laboratorio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.previene.laboratorio.Model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Integer>{
    
    Paciente findByDniAndPassword(String dni,String password);

    Paciente findByDni(String dni);

    List<Paciente> findAll();

    List<Paciente> findByEstado(String estado);

    @Query(value = "select*from pacientes where DATE_FORMAT(fechaR, '%Y-%m')=:mes", nativeQuery = true)
    List<Paciente> bucarPorMes(@Param("mes") String mes);
   
}
