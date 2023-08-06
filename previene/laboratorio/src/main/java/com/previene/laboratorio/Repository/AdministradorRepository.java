package com.previene.laboratorio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.previene.laboratorio.Model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
    
    Administrador findByDniAndPassword(String dni,String password);

    Administrador findByDni(String dni);
}
