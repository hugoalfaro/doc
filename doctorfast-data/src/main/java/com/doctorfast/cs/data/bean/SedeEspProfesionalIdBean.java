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
public class SedeEspProfesionalIdBean {

    private Integer idSede;
    private Integer idEspecialidad;
    private Integer idProfesional;

    public SedeEspProfesionalIdBean() {
    }

    public SedeEspProfesionalIdBean(Integer idSede, Integer idEspecialidad, Integer idProfesional) {
        this.idSede = idSede;
        this.idEspecialidad = idEspecialidad;
        this.idProfesional = idProfesional;
    }

    public SedeEspProfesionalIdBean(Integer idSede, Integer idEspecialidad) {
        this.idSede = idSede;
        this.idEspecialidad = idEspecialidad;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Integer getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Integer idProfesional) {
        this.idProfesional = idProfesional;
    }

}
