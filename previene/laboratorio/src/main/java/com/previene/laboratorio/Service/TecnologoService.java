package com.previene.laboratorio.Service;

import java.util.List;

import com.previene.laboratorio.Model.Tecnologo;


public interface TecnologoService {
    
    public Tecnologo findByDniAndPassword(String dni,String password);
    
    public Tecnologo findByDni(String dni);

    public List<Tecnologo> findAll();

    public Tecnologo save(Tecnologo tecnologo);

    public void update(Tecnologo tecnologo);

    public List<Tecnologo> findByEstado(String estado);
    
}
