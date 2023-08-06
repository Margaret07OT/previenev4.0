package com.previene.laboratorio.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.previene.laboratorio.Model.Tecnologo;
import com.previene.laboratorio.Repository.TecnologoRepository;

@Service
public class TecnologoServicelmpl implements TecnologoService{
    
    @Autowired
	private TecnologoRepository tecnologoRepository;

    @Override
    @Transactional(readOnly = true)
    public Tecnologo findByDniAndPassword(String dni, String password){
        return tecnologoRepository.findByDniAndPassword(dni, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Tecnologo findByDni(String dni){
        return tecnologoRepository.findByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tecnologo> findAll(){
        return tecnologoRepository.findAll();
    }

    @Override
    @Transactional
    public Tecnologo save(Tecnologo doctor){
        return tecnologoRepository.save(doctor);
    }

    @Override
    public void update(Tecnologo doctor){
        tecnologoRepository.save(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tecnologo> findByEstado(String estado){
        return tecnologoRepository.findByEstado(estado);
    }
}
