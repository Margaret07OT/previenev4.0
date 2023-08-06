package com.previene.laboratorio.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Tecnologo;

public interface TecnologoRepository extends JpaRepository<Tecnologo, Integer> {
    
    Tecnologo findByDniAndPassword(String dni,String password);

    Tecnologo findByDni(String dni);

    List<Tecnologo> findAll();

    List<Tecnologo> findByEstado(String estado);
}
