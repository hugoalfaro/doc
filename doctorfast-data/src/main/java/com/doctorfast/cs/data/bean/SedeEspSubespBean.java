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
public class SedeEspSubespBean {

    private SedeEspSubespIdBean id;
    private SedeEspecialidadBean sedeEspecialidad;
    private SubespecialidadBean subespecialidad;
    private BigDecimal costo;

    public SedeEspSubespBean() {
    }

    public SedeEspSubespBean(SedeEspSubespIdBean id, SedeEspecialidadBean sedeEspecialidad, SubespecialidadBean subespecialidad) {
        this.id = id;
        this.sedeEspecialidad = sedeEspecialidad;
        this.subespecialidad = subespecialidad;
    }

    public SedeEspSubespIdBean getId() {
        return id;
    }

    public void setId(SedeEspSubespIdBean id) {
        this.id = id;
    }

    public SedeEspecialidadBean getSedeEspecialidad() {
        return sedeEspecialidad;
    }

    public void setSedeEspecialidad(SedeEspecialidadBean sedeEspecialidad) {
        this.sedeEspecialidad = sedeEspecialidad;
    }

    public SubespecialidadBean getSubespecialidad() {
        return subespecialidad;
    }

    public void setSubespecialidad(SubespecialidadBean subespecialidad) {
        this.subespecialidad = subespecialidad;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

}
