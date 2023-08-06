package com.previene.laboratorio.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.DetalleCita;
import com.previene.laboratorio.Repository.DetalleCitaRepository;

@Service
public class DetalleCitaServiceImpl implements DetalleCitaService{
    
    @Autowired
	private DetalleCitaRepository detalleCitaRepository;

    @Override
    @Transactional
    public DetalleCita save(DetalleCita detalleCita){
        return detalleCitaRepository.save(detalleCita);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleCita> findByCita(Cita cita){
        return detalleCitaRepository.findByCita(cita);
    }

    @Override
	@Transactional
	public void deleteByCita(Cita cita) {
		detalleCitaRepository.deleteByCita(cita);
	}
}
