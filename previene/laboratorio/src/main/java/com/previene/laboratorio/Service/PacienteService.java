package com.previene.laboratorio.Service;



import java.util.List;

import com.previene.laboratorio.Model.Paciente;

public interface PacienteService {

    public Paciente findByDniAndPassword(String dni,String password);
    
    public Paciente findByDni(String dni);

    public List<Paciente> findAll();

    public Paciente save(Paciente paciente);

    public Paciente update(Paciente paciente);

    public List<Paciente> findByEstado(String estado);

    public List<Paciente> bucarPorMes(String mes);
}
