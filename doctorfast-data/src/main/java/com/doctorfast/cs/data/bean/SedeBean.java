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
public class SedeBean {

    private Integer idSede;
    private Integer codigo;
    private String nombre;
    private String direccion;
    private UbigeoBean ubigeo;
    private ClinicaBean clinica;
    private String estado;
    private Integer usCreacion;
    private Date feCreacion;
    private Integer usModificacion;
    private Date feModificacion;

    private String codigoLargo;

    public SedeBean() {
    }

    public SedeBean(Integer idSede) {
        this.idSede = idSede;
    }

    public SedeBean(String estado) {
        this.estado = estado;
    }

    public SedeBean(UbigeoBean ubigeo) {
        this.ubigeo = ubigeo;
    }

    public SedeBean(ClinicaBean clinica) {
        this.clinica = clinica;
    }

    public SedeBean(ClinicaBean clinica, String estado) {
        this.clinica = clinica;
        this.estado = estado;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public UbigeoBean getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(UbigeoBean ubigeo) {
        this.ubigeo = ubigeo;
    }

    public ClinicaBean getClinica() {
        return clinica;
    }

    public void setClinica(ClinicaBean clinica) {
        this.clinica = clinica;
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

    public String getCodigoLargo() {
        if (codigo == null || codigo == 0) {
            return codigoLargo;
        }
        return "sed" + String.format("%04d", codigo);
    }

    public void setCodigoLargo(String codigoLargo) {
        this.codigoLargo = codigoLargo;
        if (codigoLargo != null && !codigoLargo.isEmpty()) {
            this.codigo = Integer.valueOf(codigoLargo.replace("sed", "0"));
        }
    }

}
