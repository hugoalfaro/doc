package com.doctorfast.cs.data.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DisponibilidadEvento generated by hbm2java
 */
@Entity
@Table(name = "disponibilidad_evento"
)
public class DisponibilidadEvento implements java.io.Serializable {

    private Integer idDisponibilidadEvento;
    private Disponibilidad disponibilidad;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;

    public DisponibilidadEvento() {
    }

    public DisponibilidadEvento(Integer idDisponibilidadEvento, Disponibilidad disponibilidad) {
        this.idDisponibilidadEvento = idDisponibilidadEvento;
        this.disponibilidad = disponibilidad;
    }

    @Id
    @SequenceGenerator(name = "sq_disponibilidad_evento_id", sequenceName = "sq_disponibilidad_evento_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_disponibilidad_evento_id")
    @Column(name = "id_disponibilidad_evento", unique = true, nullable = false)
    public Integer getIdDisponibilidadEvento() {
        return this.idDisponibilidadEvento;
    }

    public void setIdDisponibilidadEvento(Integer idDisponibilidadEvento) {
        this.idDisponibilidadEvento = idDisponibilidadEvento;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_disponibilidad", nullable = false)
    public Disponibilidad getDisponibilidad() {
        return this.disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio", length = 35)
    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin", length = 35)
    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Column(name = "estado", length = 1)
    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @PrePersist
    protected void onCreate() {
        estado = "1";
    }

}
