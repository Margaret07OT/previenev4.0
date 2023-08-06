package com.previene.laboratorio.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Paciente;
import com.previene.laboratorio.Repository.CitaRepository;

@Service
public class CitaServicelmpl implements CitaService {
    
    @Autowired
	private CitaRepository citaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cita> findAll(){
        return citaRepository.findAll();
    }

    @Override
    @Transactional
    public Cita save(Cita cita){
        return citaRepository.save(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public Cita findById(int id_cita){
        return citaRepository.findById(id_cita);
    }

    @Override
	public void update(Cita cita) {
		citaRepository.save(cita);
	}

    @Override
    @Transactional(readOnly = true)
    public List<Cita> findByPaciente(Paciente paciente){
        return citaRepository.findByPaciente(paciente);
    }

    @Override
	@Transactional
	public void deleteById(int id_cita) {
		citaRepository.deleteById(id_cita);
	}

    @Override
    @Transactional(readOnly = true)
    public List<Cita> findByFecha(Date fecha){
        return citaRepository.findByFecha(fecha);
    }
}
