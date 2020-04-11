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
public class DisponibilidadEventoBean {

    private Integer idDisponibilidadEvento;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private DisponibilidadBean disponibilidad;

    public DisponibilidadEventoBean() {
    }

    public DisponibilidadEventoBean(DisponibilidadBean disponibilidad, String estado) {
        this.disponibilidad = disponibilidad;
        this.estado = estado;
    }

    public DisponibilidadEventoBean(Date fechaInicio, DisponibilidadBean disponibilidad, String estado) {
        this.fechaInicio = fechaInicio;
        this.disponibilidad = disponibilidad;
        this.estado = estado;
    }

    public Integer getIdDisponibilidadEvento() {
        return idDisponibilidadEvento;
    }

    public void setIdDisponibilidadEvento(Integer idDisponibilidadEvento) {
        this.idDisponibilidadEvento = idDisponibilidadEvento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DisponibilidadBean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadBean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

}
