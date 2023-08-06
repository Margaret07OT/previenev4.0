package com.previene.laboratorio.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Resultado;
import com.previene.laboratorio.Repository.ResultadoRepository;

@Service
public class ResultadoServicelmpl implements ResultadoService {
    
    @Autowired
    private ResultadoRepository resultadoRepository;

    @Override
    @Transactional
    public Resultado save(Resultado resultado){
        return resultadoRepository.save(resultado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resultado> findByCita(Cita cita){
        return resultadoRepository.findByCita(cita);
    }

    @Override
	@Transactional
	public void deleteByCita(Cita cita) {
		resultadoRepository.deleteByCita(cita);
	}

    @Override
    @Transactional(readOnly = true)
    public Resultado findById(int id){
        return resultadoRepository.findById(id);
    }
}
