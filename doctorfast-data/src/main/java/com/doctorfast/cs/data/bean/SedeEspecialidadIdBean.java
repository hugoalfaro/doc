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
public class SedeEspecialidadIdBean {

    private Integer idEspecialidad;
    private Integer idSede;

    public SedeEspecialidadIdBean() {
    }

    public SedeEspecialidadIdBean(Integer idEspecialidad, Integer idSede) {
        this.idEspecialidad = idEspecialidad;
        this.idSede = idSede;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

}
