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
public class UbigeoBean {

    private UbigeoIdBean id;
    private String noDepartamento;
    private String noProvincia;
    private String noDistrito;
    private String estado;

    public UbigeoBean() {
    }

    public UbigeoBean(UbigeoIdBean id) {
        this.id = id;
    }

    public UbigeoIdBean getId() {
        return id;
    }

    public void setId(UbigeoIdBean id) {
        this.id = id;
    }

    public String getNoDepartamento() {
        return noDepartamento;
    }

    public void setNoDepartamento(String noDepartamento) {
        this.noDepartamento = noDepartamento;
    }

    public String getNoProvincia() {
        return noProvincia;
    }

    public void setNoProvincia(String noProvincia) {
        this.noProvincia = noProvincia;
    }

    public String getNoDistrito() {
        return noDistrito;
    }

    public void setNoDistrito(String noDistrito) {
        this.noDistrito = noDistrito;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
