package com.previene.laboratorio.Service;

import java.util.List;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.DetalleCita;

public interface DetalleCitaService {
    
    public DetalleCita save(DetalleCita detalleCita);

    public List<DetalleCita> findByCita(Cita cita);

    public void deleteByCita(Cita cita);
}
