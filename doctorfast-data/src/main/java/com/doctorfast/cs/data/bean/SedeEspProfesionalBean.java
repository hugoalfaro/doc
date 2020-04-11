/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

/**
 *
 * @author MBS GROUP
 */
public class SedeEspProfesionalBean {

    private SedeEspProfesionalIdBean id;
    private SedeEspecialidadBean sedeEspecialidad;
    private ProfesionalBean profesional;
    private String estado;

    public SedeEspProfesionalBean() {
    }

    public SedeEspProfesionalBean(SedeEspProfesionalIdBean id) {
        this.id = id;
    }

    public SedeEspProfesionalBean(SedeEspProfesionalIdBean id, SedeEspecialidadBean sedeEspecialidad, ProfesionalBean profesional, String estado) {
        this.id = id;
        this.sedeEspecialidad = sedeEspecialidad;
        this.profesional = profesional;
        this.estado = estado;
    }

    public SedeEspProfesionalIdBean getId() {
        return id;
    }

    public void setId(SedeEspProfesionalIdBean id) {
        this.id = id;
    }

    public SedeEspecialidadBean getSedeEspecialidad() {
        return sedeEspecialidad;
    }

    public void setSedeEspecialidad(SedeEspecialidadBean sedeEspecialidad) {
        this.sedeEspecialidad = sedeEspecialidad;
    }

    public ProfesionalBean getProfesional() {
        return profesional;
    }

    public void setProfesional(ProfesionalBean profesional) {
        this.profesional = profesional;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
