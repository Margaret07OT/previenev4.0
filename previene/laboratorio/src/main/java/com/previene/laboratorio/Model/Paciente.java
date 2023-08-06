package com.previene.laboratorio.Model;

import java.time.LocalDate;
import java.util.Date;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;


import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "pacientes")
public class Paciente {
    private static final long serialVersionUID = 1L;

    @Id
    @Size(max=8 , min=8, message="Debe ser de 8 dígitos")
    @Column(name = "dni_pac")
    private String dni;

    
    @NotBlank(message = "El nombre no puede estar vacío")
	private String nombres;

	
    @NotBlank(message = "El apellido no puede estar vacío")
	private String apellidos;
	
	@NotBlank(message = "El email no puede estar vacío")
    @Column(unique = true)
    @Size(max=50)
	@Email
	private String email;

   
	private String password;

   
    private String rol;

    
    @NotBlank(message = "")
	@Size(min=9, max=9, message="Debe ser de 9 dígitos")
    @Column(unique = true)
	private String celular;

    @NotBlank(message = "Debe elegir una opcion")
	private String sexo;

    @NotNull(message = "La fecha no debe estar vacia")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Debe ser una fecha pasada")
	private Date fecnac;


    @NotBlank(message = "La direccion no puede estar vacío")
    private String direccion;


   
    private String estado;

    @NotBlank(message = "Debe elegir una opcion")
    private String medicamento;

    @NotBlank(message = "Debe elegir una opcion")
    private String enfermedad;

    
    private String menstruacion;

    private LocalDate fechaR;

    public Paciente(){
        
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFecnac() {
        return fecnac;
    }

    public void setFecnac(Date fecnac) {
        this.fecnac = fecnac;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getMenstruacion() {
        return menstruacion;
    }

    public void setMenstruacion(String menstruacion) {
        this.menstruacion = menstruacion;
    }

    public LocalDate getFechaR() {
        return fechaR;
    }

    public void setFechaR(LocalDate fechaR) {
        this.fechaR = fechaR;
    }
    
    @PrePersist
    public void asignarDatos(){
        int n = (int)(Math.random()*(999999-100000+1)+100000);
        password=n+"";
        rol="Paciente";
        estado="Activo";
        fechaR=LocalDate.now();
    }
}
