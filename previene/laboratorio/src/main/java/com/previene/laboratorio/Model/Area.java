package com.previene.laboratorio.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "areas")
public class Area {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_area;

  
    @NotBlank
    private String descripcion;

    private String estado;

    public Area(){

    }

    public Integer getId_area() {
        return id_area;
    }

    public void setId_area(Integer id_area) {
        this.id_area = id_area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   
}
