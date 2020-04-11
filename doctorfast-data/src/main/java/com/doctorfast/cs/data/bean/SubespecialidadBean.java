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
public class SubespecialidadBean {

    private Integer idSubespecialidad;
    private EspecialidadBean especialidad;
    private String nombre;
    private String estado;
    private Integer usCreacion;
    private Date feCreacion;
    private Integer usModificacion;
    private Date feModificacion;

    public SubespecialidadBean() {
    }

    public SubespecialidadBean(String estado) {
        this.estado = estado;
    }

    public Integer getIdSubespecialidad() {
        return idSubespecialidad;
    }

    public void setIdSubespecialidad(Integer idSubespecialidad) {
        this.idSubespecialidad = idSubespecialidad;
    }

    public EspecialidadBean getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadBean especialidad) {
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
