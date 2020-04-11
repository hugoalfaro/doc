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
public class UbigeoIdBean {

    private Integer coDepartamento;
    private Integer coProvincia;
    private Integer coDistrito;

    public UbigeoIdBean() {
    }

    public Integer getCoDepartamento() {
        return coDepartamento;
    }

    public void setCoDepartamento(Integer coDepartamento) {
        this.coDepartamento = coDepartamento;
    }

    public Integer getCoProvincia() {
        return coProvincia;
    }

    public void setCoProvincia(Integer coProvincia) {
        this.coProvincia = coProvincia;
    }

    public Integer getCoDistrito() {
        return coDistrito;
    }

    public void setCoDistrito(Integer coDistrito) {
        this.coDistrito = coDistrito;
    }

}
