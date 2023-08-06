package com.previene.laboratorio.Service;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.previene.laboratorio.Model.Paciente;
import com.previene.laboratorio.Repository.PacienteRepository;

@Service
public class PacienteServicelmpl implements PacienteService{
    
    @Autowired
	private PacienteRepository pacienteRepository;

    
    @Override
    @Transactional(readOnly = true)
    public Paciente findByDniAndPassword(String dni, String password){
        return pacienteRepository.findByDniAndPassword(dni, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente findByDni(String dni){
        return pacienteRepository.findByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findAll(){
        return pacienteRepository.findAll();
    }

    @Override
    @Transactional
    public Paciente save(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    @Override
    @Transactional
	public Paciente update(Paciente paciente) {
		return pacienteRepository.save(paciente);
	}

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findByEstado(String estado){
        return pacienteRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> bucarPorMes(String mes){
        return pacienteRepository.bucarPorMes(mes);
    }
    
}
