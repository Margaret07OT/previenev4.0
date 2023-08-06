package com.previene.laboratorio.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




@Entity
@Table(name = "resultados")
public class Resultado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultados")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name="id_area")
    private Area area;

    @Lob
    private byte[]resultado;

    private String nombreResultado;

    private String tipo;

    private LocalDateTime fechaR;

    private String estado;

    private double subTotal;

    public Resultado(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public byte[] getResultado() {
        return resultado;
    }

    public void setResultado(byte[] resultado) {
        this.resultado = resultado;
    } 

    public LocalDateTime getFechaR() {
        return fechaR;
    }

    public void setFechaR(LocalDateTime fechaR) {
        this.fechaR = fechaR;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getNombreResultado() {
        return nombreResultado;
    }

    public void setNombreResultado(String nombreResultado) {
        this.nombreResultado = nombreResultado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    } 
    
}
