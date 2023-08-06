package com.previene.laboratorio.Service;

import java.util.Date;
import java.util.List;

import com.previene.laboratorio.Model.Cita;
import com.previene.laboratorio.Model.Paciente;

public interface CitaService {
    
    public List<Cita> findAll();

    public Cita save(Cita cita);

    public Cita findById(int id_cita);

    public void update(Cita cita);

    public List<Cita> findByPaciente(Paciente paciente);

    public void deleteById(int id_cita);

    public List<Cita> findByFecha(Date fecha);
}
