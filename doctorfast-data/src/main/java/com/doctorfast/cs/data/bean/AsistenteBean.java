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
public class AsistenteBean {

    private Integer idAsistente;
    private ClinicaBean clinica;
    private PersonaUsuarioBean personaUsuario;
    private String estado;
    private Integer usCreacion;
    private Date feCreacion;
    private Integer usModificacion;
    private Date feModificacion;

    public AsistenteBean() {
    }

    public Integer getIdAsistente() {
        return idAsistente;
    }

    public void setIdAsistente(Integer idAsistente) {
        this.idAsistente = idAsistente;
    }

    public PersonaUsuarioBean getPersonaUsuario() {
        return personaUsuario;
    }

    public void setPersonaUsuario(PersonaUsuarioBean personaUsuario) {
        this.personaUsuario = personaUsuario;
    }

    public ClinicaBean getClinica() {
        return clinica;
    }

    public void setClinica(ClinicaBean clinica) {
        this.clinica = clinica;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getUsCreacion() {
        return usCreacion;
    }

    public void setUsCreacion(Integer usCreacion) {
        this.usCreacion = usCreacion;
    }

    public Date getFeCreacion() {
        return feCreacion;
    }

    public void setFeCreacion(Date feCreacion) {
        this.feCreacion = feCreacion;
    }

    public Integer getUsModificacion() {
        return usModificacion;
    }

    public void setUsModificacion(Integer usModificacion) {
        this.usModificacion = usModificacion;
    }

    public Date getFeModificacion() {
        return feModificacion;
    }

    public void setFeModificacion(Date feModificacion) {
        this.feModificacion = feModificacion;
    }
    
}
