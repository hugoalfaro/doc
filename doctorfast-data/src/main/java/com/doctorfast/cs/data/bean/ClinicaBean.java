/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MBS GROUP
 */
public class ClinicaBean {

    private Integer idClinica;
    private String ruc;
    private String nombreComercial;
    private String nombreAbreviado;
    private String direccionComercial;
    private String logo;
    private Integer tiempoCita;
    private Integer orden;
    private String paginaWeb;
    private String estado;
    private Integer usCreacion;
    private Date feCreacion;
    private Integer usModificacion;
    private Date feModificacion;
    private List<ProfesionalBean> profesionals;
    
    private MultipartFile archivo;
    private ProfesionalBean profesional;

    public ClinicaBean() {
    }

    public ClinicaBean(String estado) {
        this.estado = estado;
    }

    public ClinicaBean(Integer idClinica) {
        this.idClinica = idClinica;
    }

    public Integer getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Integer idClinica) {
        this.idClinica = idClinica;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNombreAbreviado() {
        return nombreAbreviado;
    }

    public void setNombreAbreviado(String nombreAbreviado) {
        this.nombreAbreviado = nombreAbreviado;
    }

    public String getDireccionComercial() {
        return direccionComercial;
    }

    public void setDireccionComercial(String direccionComercial) {
        this.direccionComercial = direccionComercial;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getTiempoCita() {
        return tiempoCita;
    }

    public void setTiempoCita(Integer tiempoCita) {
        this.tiempoCita = tiempoCita;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
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

    public List<ProfesionalBean> getProfesionals() {
        return profesionals;
    }

    public void setProfesionals(List<ProfesionalBean> profesionals) {
        this.profesionals = profesionals;
    }

    public MultipartFile getArchivo() {
        return archivo;
    }

    public void setArchivo(MultipartFile archivo) {
        this.archivo = archivo;
    }

    public ProfesionalBean getProfesional() {
        return profesional;
    }

    public void setProfesional(ProfesionalBean profesional) {
        this.profesional = profesional;
    }

}
