/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public class ProfesionalBean {

    private Integer idProfesional;
    private PersonaUsuarioBean personaUsuario;
    private String cop;
    private String colorDisponibilidad;
    private String colorCita;
    private Integer tiempoCita;
    private String estado;
    private Integer usCreacion;
    private Date feCreacion;
    private Integer usModificacion;
    private Date feModificacion;
    private List<SedeEspProfesionalBean> sedeEspProfesionals = new ArrayList<>();
    private ClinicaBean clinica;
    
    private SedeEspProfesionalBean sedeEspProfesional;
    private List<Integer> idProfesionals;

    public ProfesionalBean() {
    }

    public ProfesionalBean(String estado) {
        this.estado = estado;
    }

    public ProfesionalBean(SedeEspProfesionalBean sedeEspProfesional) {
        this.sedeEspProfesional = sedeEspProfesional;
    }

    public ProfesionalBean(ClinicaBean clinica) {
        this.clinica = clinica;
    }

    public ProfesionalBean(ClinicaBean clinica, String estado) {
        this.clinica = clinica;
        this.estado = estado;
    }

    public Integer getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Integer idProfesional) {
        this.idProfesional = idProfesional;
    }

    public PersonaUsuarioBean getPersonaUsuario() {
        return personaUsuario;
    }

    public void setPersonaUsuario(PersonaUsuarioBean personaUsuario) {
        this.personaUsuario = personaUsuario;
    }

    public String getCop() {
        return cop;
    }

    public void setCop(String cop) {
        this.cop = cop;
    }

    public String getColorDisponibilidad() {
        return colorDisponibilidad;
    }

    public void setColorDisponibilidad(String colorDisponibilidad) {
        this.colorDisponibilidad = colorDisponibilidad;
    }

    public String getColorCita() {
        return colorCita;
    }

    public void setColorCita(String colorCita) {
        this.colorCita = colorCita;
    }

    public Integer getTiempoCita() {
        return tiempoCita;
    }

    public void setTiempoCita(Integer tiempoCita) {
        this.tiempoCita = tiempoCita;
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

    public List<SedeEspProfesionalBean> getSedeEspProfesionals() {
        return sedeEspProfesionals;
    }

    public void setSedeEspProfesionals(List<SedeEspProfesionalBean> sedeEspProfesionals) {
        this.sedeEspProfesionals = sedeEspProfesionals;
    }

    public SedeEspProfesionalBean getSedeEspProfesional() {
        return sedeEspProfesional;
    }

    public void setSedeEspProfesional(SedeEspProfesionalBean sedeEspProfesional) {
        this.sedeEspProfesional = sedeEspProfesional;
    }

    public ClinicaBean getClinica() {
        return clinica;
    }

    public void setClinica(ClinicaBean clinica) {
        this.clinica = clinica;
    }

    public List<Integer> getIdProfesionals() {
        return idProfesionals;
    }

    public void setIdProfesionals(List<Integer> idProfesionals) {
        this.idProfesionals = idProfesionals;
    }

}
