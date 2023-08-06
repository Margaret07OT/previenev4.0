package com.previene.laboratorio.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;



@Entity
@Table(name = "analisis")
public class Analisis {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_analisis;

  
    @NotBlank
    private String descripcion;

    @NotNull
    @NumberFormat(pattern = "#.##")
    private double precio;

    @ManyToOne
    @JoinColumn(name = "id_area")
    private Area area;

    private String estado;

    public Analisis(){

    }

    public Integer getId_analisis() {
        return id_analisis;
    }

    public void setId_analisis(Integer id_analisis) {
        this.id_analisis = id_analisis;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
