package com.previene.laboratorio.Service;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Factura;

public interface FacturaService {
    
    public Factura save(Factura factura);

    public Factura findByCita(Cita cita);
}
