/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author MBS GROUP
 */
public class PromocionBean {

    private Integer idPromocion;
    private BigDecimal costo;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer orden;
    private String imagen;
    private String descripcion;
    private String estado;
    private String usCreacion;
    private Date feCreacion;
    private String usModificacion;
    private Date feModificacion;
    private SedeEspecialidadBean sedeEspecialidad;

    public PromocionBean() {
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsCreacion() {
        return usCreacion;
    }

    public void setUsCreacion(String usCreacion) {
        this.usCreacion = usCreacion;
    }

    public Date getFeCreacion() {
        return feCreacion;
    }

    public void setFeCreacion(Date feCreacion) {
        this.feCreacion = feCreacion;
    }

    public String getUsModificacion() {
        return usModificacion;
    }

    public void setUsModificacion(String usModificacion) {
        this.usModificacion = usModificacion;
    }

    public Date getFeModificacion() {
        return feModificacion;
    }

    public void setFeModificacion(Date feModificacion) {
        this.feModificacion = feModificacion;
    }

    public SedeEspecialidadBean getSedeEspecialidad() {
        return sedeEspecialidad;
    }

    public void setSedeEspecialidad(SedeEspecialidadBean sedeEspecialidad) {
        this.sedeEspecialidad = sedeEspecialidad;
    }

}
