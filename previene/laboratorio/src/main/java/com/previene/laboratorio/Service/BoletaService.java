package com.previene.laboratorio.Service;

import com.previene.laboratorio.Model.Boleta;
import com.previene.laboratorio.Model.Cita;

public interface BoletaService {
    
    public Boleta save(Boleta boleta);

    public Boleta findByCita(Cita cita);
}
