/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 *
 * @author MBS GROUP
 */
@Entity
@Table(name = "sede_esp_prof"
)
public class SedeEspProfesional implements java.io.Serializable {

    private SedeEspProfesionalId id;
    private SedeEspecialidad sedeEspecialidad;
    private Profesional profesional;
    private String estado;
    private List<Cita> citas = new ArrayList<>();
    private List<Disponibilidad> disponibilidads = new ArrayList<>();

    public SedeEspProfesional() {
    }

    public SedeEspProfesional(SedeEspProfesionalId id, SedeEspecialidad sedeEspecialidad, Profesional profesional) {
        this.id = id;
        this.sedeEspecialidad = sedeEspecialidad;
        this.profesional = profesional;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "idSede", column = @Column(name = "id_sede", nullable = false)),
        @AttributeOverride(name = "idEspecialidad", column = @Column(name = "id_especialidad", nullable = false)),
        @AttributeOverride(name = "idProfesional", column = @Column(name = "id_profesional", nullable = false))})
    public SedeEspProfesionalId getId() {
        return id;
    }

    public void setId(SedeEspProfesionalId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "id_sede", referencedColumnName = "id_sede", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "id_especialidad", referencedColumnName = "id_especialidad", nullable = false, insertable = false, updatable = false)})
    public SedeEspecialidad getSedeEspecialidad() {
        return sedeEspecialidad;
    }

    public void setSedeEspecialidad(SedeEspecialidad sedeEspecialidad) {
        this.sedeEspecialidad = sedeEspecialidad;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_profesional", nullable = false, insertable = false, updatable = false)
    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    @Column(name = "estado", length = 1)
    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sedeEspProfesional")
    public List<Cita> getCitas() {
        return this.citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sedeEspProfesional")
    public List<Disponibilidad> getDisponibilidads() {
        return disponibilidads;
    }

    public void setDisponibilidads(List<Disponibilidad> disponibilidads) {
        this.disponibilidads = disponibilidads;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.sedeEspecialidad);
        hash = 29 * hash + Objects.hashCode(this.profesional);
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
        final SedeEspProfesional other = (SedeEspProfesional) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sedeEspecialidad, other.sedeEspecialidad)) {
            return false;
        }
        if (!Objects.equals(this.profesional, other.profesional)) {
            return false;
        }
        return true;
    }

    @PrePersist
    protected void onCreate() {
        estado = "1";
    }

}
