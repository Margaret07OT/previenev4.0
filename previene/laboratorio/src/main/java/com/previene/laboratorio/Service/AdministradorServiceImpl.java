package com.previene.laboratorio.Service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.previene.laboratorio.Model.Administrador;
import com.previene.laboratorio.Repository.AdministradorRepository;

@Service
public class AdministradorServiceImpl implements AdministradorService{
    
    @Autowired
	private AdministradorRepository administradorRepository;

    @Override
    @Transactional(readOnly = true)
    public Administrador findByDniAndPassword(String dni, String password) {
        return administradorRepository.findByDniAndPassword(dni, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Administrador findByDni(String dni) {
        return administradorRepository.findByDni(dni);
    }
    
	

    
}
