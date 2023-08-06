package com.previene.laboratorio.Service;

import com.previene.laboratorio.Model.Administrador;

public interface AdministradorService {
    
    public Administrador findByDniAndPassword(String dni,String password);

    public Administrador findByDni(String dni);
    
}
