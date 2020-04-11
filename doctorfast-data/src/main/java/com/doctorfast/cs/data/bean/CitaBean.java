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
public class CitaBean {

    private Integer idCita;
    private String codigo;
    private PacienteBean paciente;
    private SedeEspProfesionalBean sedeEspProfesional;
    private SubespecialidadBean subespecialidad;
    private Date fechaAtencion;
    private String estadoCita;
    private String comentario;
    private Integer tiempoCita;
    private String estado;
    private Integer usCreacion;
    private Date feCreacion;
    private Integer usModificacion;
    private Date feModificacion;
    
    private Date fechaAtencionFin;
    private String timezone;

    public CitaBean() {
    }

    public CitaBean(SedeEspProfesionalBean sedeEspProfesional) {
        this.sedeEspProfesional = sedeEspProfesional;
    }

    public CitaBean(SedeEspProfesionalBean sedeEspProfesional, String estadoCita, String estado) {
        this.sedeEspProfesional = sedeEspProfesional;
        this.estadoCita = estadoCita;
        this.estado = estado;
    }

    public CitaBean(SedeEspProfesionalBean sedeEspProfesional, Date fechaAtencion, String estado) {
        this.sedeEspProfesional = sedeEspProfesional;
        this.fechaAtencion = fechaAtencion;
        this.estado = estado;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public PacienteBean getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteBean paciente) {
        this.paciente = paciente;
    }

    public SedeEspProfesionalBean getSedeEspProfesional() {
        return sedeEspProfesional;
    }

    public void setSedeEspProfesional(SedeEspProfesionalBean sedeEspProfesional) {
        this.sedeEspProfesional = sedeEspProfesional;
    }

    public SubespecialidadBean getSubespecialidad() {
        return subespecialidad;
    }

    public void setSubespecialidad(SubespecialidadBean subespecialidad) {
        this.subespecialidad = subespecialidad;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getTiempoCita() {
        return tiempoCita;
    }

    public void setTiempoCita(Integer tiempoCita) {
        this.tiempoCita = tiempoCita;
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

    public Date getFechaAtencionFin() {
        return fechaAtencionFin;
    }

    public void setFechaAtencionFin(Date fechaAtencionFin) {
        this.fechaAtencionFin = fechaAtencionFin;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    
}
