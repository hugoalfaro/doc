/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

import java.math.BigDecimal;

/**
 *
 * @author MBS GROUP
 */
public class SedeEspecialidadBean {

    private SedeEspecialidadIdBean id;
    private SedeBean sede;
    private EspecialidadBean especialidad;
    private BigDecimal costoConsulta;
    private BigDecimal costo;
    private String estado;

    public SedeEspecialidadBean() {
    }

    public SedeEspecialidadBean(SedeBean sede) {
        this.sede = sede;
    }

    public SedeEspecialidadBean(SedeBean sede, EspecialidadBean especialidad) {
        this.sede = sede;
        this.especialidad = especialidad;
    }

    public SedeEspecialidadBean(SedeBean sede, String estado) {
        this.sede = sede;
        this.estado = estado;
    }

    public SedeEspecialidadBean(SedeBean sede, EspecialidadBean especialidad, String estado) {
        this.sede = sede;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public SedeEspecialidadBean(SedeEspecialidadIdBean id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public SedeEspecialidadIdBean getId() {
        return id;
    }

    public void setId(SedeEspecialidadIdBean id) {
        this.id = id;
    }

    public SedeBean getSede() {
        return sede;
    }

    public void setSede(SedeBean sede) {
        this.sede = sede;
    }

    public EspecialidadBean getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadBean especialidad) {
        this.especialidad = especialidad;
    }

    public BigDecimal getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(BigDecimal costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
