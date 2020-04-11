/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author MBS GROUP
 */
@Embeddable
public class SedeEspProfesionalId implements java.io.Serializable {

    private Integer idSede;
    private Integer idEspecialidad;
    private Integer idProfesional;

    public SedeEspProfesionalId() {
    }

    public SedeEspProfesionalId(Integer idSede, Integer idEspecialidad, Integer idProfesional) {
        this.idSede = idSede;
        this.idEspecialidad = idEspecialidad;
        this.idProfesional = idProfesional;
    }

    @Column(name = "id_sede", nullable = false)
    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    @Column(name = "id_especialidad", nullable = false)
    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    @Column(name = "id_profesional", nullable = false)
    public Integer getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Integer idProfesional) {
        this.idProfesional = idProfesional;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idSede);
        hash = 59 * hash + Objects.hashCode(this.idEspecialidad);
        hash = 59 * hash + Objects.hashCode(this.idProfesional);
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
        final SedeEspProfesionalId other = (SedeEspProfesionalId) obj;
        if (!Objects.equals(this.idSede, other.idSede)) {
            return false;
        }
        if (!Objects.equals(this.idEspecialidad, other.idEspecialidad)) {
            return false;
        }
        if (!Objects.equals(this.idProfesional, other.idProfesional)) {
            return false;
        }
        return true;
    }
    
}
