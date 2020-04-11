/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.bean;

/**
 *
 * @author MBS GROUP
 */
public class PerfilBean {

    private Integer idPerfil;
    private String noPerfil;
    private String estado;

    public PerfilBean() {
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNoPerfil() {
        return noPerfil;
    }

    public void setNoPerfil(String noPerfil) {
        this.noPerfil = noPerfil;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
