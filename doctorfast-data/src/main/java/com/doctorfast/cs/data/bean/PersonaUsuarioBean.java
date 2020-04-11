/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

import java.util.Date;

/**
 *
 * @author MBS GROUP
 */
public class PersonaUsuarioBean {

    private Integer idPersonaUsuario;
    private PerfilBean perfil;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String documentoIdentidad;
    private String tipoDocumento;
    private Date fechaNacimiento;
    private Integer sexo;
    private String telefono;
    private String correo;
    private String usuario;
    private String clave;
    private String estado;
    
    private String repetirClave;

    public PersonaUsuarioBean() {
    }

    public PersonaUsuarioBean(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public Integer getIdPersonaUsuario() {
        return idPersonaUsuario;
    }

    public void setIdPersonaUsuario(Integer idPersonaUsuario) {
        this.idPersonaUsuario = idPersonaUsuario;
    }

    public PerfilBean getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilBean perfil) {
        this.perfil = perfil;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRepetirClave() {
        return repetirClave;
    }

    public void setRepetirClave(String repetirClave) {
        this.repetirClave = repetirClave;
    }

}
