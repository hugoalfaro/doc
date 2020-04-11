/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

import java.util.Date;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public class DisponibilidadBean {

    private Integer idDisponibilidad;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer repeticionTipo;
    private Integer repeticionCada;
    private String repeticionDias;
    private Date repeticionInicio;
    private Date repeticionFin;
    private Integer repeticionVeces;
    private String estado;
    private Integer usCreacion;
    private Date feCreacion;
    private Integer usModificacion;
    private Date feModificacion;
    private SedeEspProfesionalBean sedeEspProfesional;
    private List<DisponibilidadEventoBean> disponibilidadEventos;
    
    private Integer idDisponibilidadEvento;
    private Boolean sinRepeticion;
    private Boolean editarSiguientes;
    private String timezone;

    public DisponibilidadBean() {
    }

    public DisponibilidadBean(SedeEspProfesionalBean sedeEspProfesional) {
        this.sedeEspProfesional = sedeEspProfesional;
    }

    public DisponibilidadBean(Integer idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
    }

    public DisponibilidadBean(SedeEspProfesionalBean sedeEspProfesional, String estado) {
        this.sedeEspProfesional = sedeEspProfesional;
        this.estado = estado;
    }

    public Integer getIdDisponibilidad() {
        return idDisponibilidad;
    }

    public void setIdDisponibilidad(Integer idDisponibilidad) {
        this.idDisponibilidad = idDisponibilidad;
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

    public Integer getRepeticionTipo() {
        return repeticionTipo;
    }

    public void setRepeticionTipo(Integer repeticionTipo) {
        this.repeticionTipo = repeticionTipo;
    }

    public Integer getRepeticionCada() {
        return repeticionCada;
    }

    public void setRepeticionCada(Integer repeticionCada) {
        this.repeticionCada = repeticionCada;
    }

    public String getRepeticionDias() {
        return repeticionDias;
    }

    public void setRepeticionDias(String repeticionDias) {
        this.repeticionDias = repeticionDias;
    }

    public Date getRepeticionInicio() {
        return repeticionInicio;
    }

    public void setRepeticionInicio(Date repeticionInicio) {
        this.repeticionInicio = repeticionInicio;
    }

    public Date getRepeticionFin() {
        return repeticionFin;
    }

    public void setRepeticionFin(Date repeticionFin) {
        this.repeticionFin = repeticionFin;
    }

    public Integer getRepeticionVeces() {
        return repeticionVeces;
    }

    public void setRepeticionVeces(Integer repeticionVeces) {
        this.repeticionVeces = repeticionVeces;
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

    public SedeEspProfesionalBean getSedeEspProfesional() {
        return sedeEspProfesional;
    }

    public void setSedeEspProfesional(SedeEspProfesionalBean sedeEspProfesional) {
        this.sedeEspProfesional = sedeEspProfesional;
    }

    public List<DisponibilidadEventoBean> getDisponibilidadEventos() {
        return disponibilidadEventos;
    }

    public void setDisponibilidadEventos(List<DisponibilidadEventoBean> disponibilidadEventos) {
        this.disponibilidadEventos = disponibilidadEventos;
    }

    public Integer getIdDisponibilidadEvento() {
        return idDisponibilidadEvento;
    }

    public void setIdDisponibilidadEvento(Integer idDisponibilidadEvento) {
        this.idDisponibilidadEvento = idDisponibilidadEvento;
    }

    public Boolean getSinRepeticion() {
        return sinRepeticion;
    }

    public void setSinRepeticion(Boolean sinRepeticion) {
        this.sinRepeticion = sinRepeticion;
    }

    public Boolean getEditarSiguientes() {
        return editarSiguientes;
    }

    public void setEditarSiguientes(Boolean editarSiguientes) {
        this.editarSiguientes = editarSiguientes;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
