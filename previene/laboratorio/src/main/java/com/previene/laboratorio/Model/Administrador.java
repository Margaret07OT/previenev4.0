package com.previene.laboratorio.Model;

import java.util.Date;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "administrador")
public class Administrador {
    
    @Id
    @Size(max=8 , min=8, message="Campo debe ser de 8 dígitos")
    @Column(name = "dni_admin")
    private String dni;
    
    @NotNull
	private String nombres;

	@NotNull
	private String apellidos;

    @NotNull
    @NotBlank
	@Email
    @Column(unique = true)
	private String email;

    @NotNull
	private String password;

    @NotNull
    private String rol;

    @NotNull
    @NotBlank 
	@Size(min=9, max=9, message="Debe ser de 9 dígitos")
	private String celular;

    @NotNull
	@Size(min=1, max=1, message="Debe ser M o F")
	private char sexo;

    @NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
	private Date fecnac;

    public Administrador(){
        
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

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getFecnac() {
        return fecnac;
    }

    public void setFecnac(Date fecnac) {
        this.fecnac = fecnac;
    }

    
}
