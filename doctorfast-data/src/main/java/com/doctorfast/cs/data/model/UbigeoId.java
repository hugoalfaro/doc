package com.doctorfast.cs.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UbigeoId generated by hbm2java
 */
@Embeddable
public class UbigeoId implements java.io.Serializable {

    private Integer coDepartamento;
    private Integer coProvincia;
    private Integer coDistrito;

    public UbigeoId() {
    }

    public UbigeoId(Integer coDepartamento, int coProvincia, int coDistrito) {
        this.coDepartamento = coDepartamento;
        this.coProvincia = coProvincia;
        this.coDistrito = coDistrito;
    }

    @Column(name = "co_departamento", nullable = false)
    public Integer getCoDepartamento() {
        return this.coDepartamento;
    }

    public void setCoDepartamento(Integer coDepartamento) {
        this.coDepartamento = coDepartamento;
    }

    @Column(name = "co_provincia", nullable = false)
    public Integer getCoProvincia() {
        return this.coProvincia;
    }

    public void setCoProvincia(Integer coProvincia) {
        this.coProvincia = coProvincia;
    }

    @Column(name = "co_distrito", nullable = false)
    public Integer getCoDistrito() {
        return this.coDistrito;
    }

    public void setCoDistrito(Integer coDistrito) {
        this.coDistrito = coDistrito;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof UbigeoId)) {
            return false;
        }
        UbigeoId castOther = (UbigeoId) other;

        return (this.getCoDepartamento() == castOther.getCoDepartamento())
                && (this.getCoProvincia() == castOther.getCoProvincia())
                && (this.getCoDistrito() == castOther.getCoDistrito());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getCoDepartamento();
        result = 37 * result + this.getCoProvincia();
        result = 37 * result + this.getCoDistrito();
        return result;
    }

}
