package com.previene.laboratorio.Service;

import java.util.List;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Resultado;

public interface ResultadoService  {
    
    public Resultado save(Resultado resultado);
    
    public List<Resultado> findByCita(Cita cita);

    public void deleteByCita(Cita cita);

    public Resultado findById(int id);
}
