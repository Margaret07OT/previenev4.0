package com.previene.laboratorio.Model;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "citas")
public class Cita {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
	private Integer id;

    @Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Debe ser un fecha futura")
    @NotNull(message = "La fecha no debe estar vacia")
	private Date fecha;
    
    @NotBlank(message = "La hora no debe estar vacia")
    private String hora;

	private LocalDateTime fechaR;

    @NotBlank(message = "Debe elegir el tipo de atencion")
    private String tipoCita;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "dni_pac")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "dni_tec")
    private Tecnologo tecnologo;

    @ManyToOne
    @JoinColumn(name = "dni_admin")
    private Administrador admin;

    @NumberFormat(pattern = "#.##")
    private double total; 

    @Transient
    private List<Integer> analisis;

    @Transient
    private String direccion;

    @Transient
    private String distrito;

    @Transient
    private String referencia;

    private static final long serialVersionUID = 1L;

    public Cita(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public LocalDateTime getFechaR() {
        return fechaR;
    }

    public void setFechaR(LocalDateTime fechaR) {
        this.fechaR = fechaR;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Tecnologo getTecnologo() {
        return tecnologo;
    }

    public void setTecnologo(Tecnologo tecnologo) {
        this.tecnologo = tecnologo;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public List<Integer> getAnalisis() {
        return analisis;
    }

    public void setAnalisis(List<Integer> analisis) {
        this.analisis = analisis;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @PrePersist
    public void asignarFechaRegistro(){
        fechaR=LocalDateTime.now();
        estado="Pendiente";
    }
}
