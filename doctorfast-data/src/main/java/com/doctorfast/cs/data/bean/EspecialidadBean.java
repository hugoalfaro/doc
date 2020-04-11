/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author MBS GROUP
 */
public class EspecialidadBean {

    private Integer idEspecialidad;
    private String codigo;
    private String nombre;
    private String estado;
    private Integer usCreacion;
    private Date feCreacion;
    private Integer usModificacion;
    private Date feModificacion;

    public EspecialidadBean() {
    }

    public EspecialidadBean(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public EspecialidadBean(String estado) {
        this.estado = estado;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.idEspecialidad);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EspecialidadBean other = (EspecialidadBean) obj;
        if (!Objects.equals(this.idEspecialidad, other.idEspecialidad)) {
            return false;
        }
        return true;
    }

}
