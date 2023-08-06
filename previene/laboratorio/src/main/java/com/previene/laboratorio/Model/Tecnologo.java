package com.previene.laboratorio.Model;

import java.io.Serializable;
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
@Table(name = "tecnologos")
public class Tecnologo implements Serializable{
    
    @Id
    @Size(max=8 , min=8, message="Campo debe ser de 8 dígitos")
    @Column(name = "dni_tec")
    private String dni;

   
    @NotBlank(message = "Los nombres no debe estar vacio") 
	private String nombres;

	
    @NotBlank (message = "Los apellidos no debe estar vacio") 
	private String apellidos;
	
    @NotNull(message = "La fecha no debe estar vacia")
    @Temporal(TemporalType.DATE) 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Debe ser una fecha pasada")
	private Date fecNac;

    
	private String password;

    private String rol;

    @NotBlank(message = "El email no puede estar vacío")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "")
	@Size(min=9, max=9, message="Debe ser de 9 dígitos")
    @Column(unique = true)
	private String celular;

    @NotBlank(message = "Debe elegir una opcion")
	private String sexo;

    @NotBlank(message = "La direccion no puede estar vacío")
    private String direccion;

    @NotBlank(message = "Debe elegir una opcion")
    private String turno;

    private String estado;

    
    private String cip;

    
    private String rne;

    public Tecnologo(){
        
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

    public Date getFecNac() {
        return fecNac;
    }

    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getRne() {
        return rne;
    }

    public void setRne(String rne) {
        this.rne = rne;
    }

    @PrePersist
    public void asignarDatos(){
        int n = (int)(Math.random()*(999999-100000+1)+100000);
        password=n+"";
        rol="Tecnologo";
        estado="Activo";
    }


    private static final long serialVersionUID = 1L;
}
