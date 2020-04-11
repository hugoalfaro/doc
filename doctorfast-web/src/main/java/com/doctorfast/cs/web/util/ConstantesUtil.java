/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.util;

/**
 *
 * @author MBS GROUP
 */
public final class ConstantesUtil {

    public static enum ESTADO_REGISTRO {
        ACTIVO("1"), INACTIVO("0"), ELIMINADO("9");

        private final String estado;

        private ESTADO_REGISTRO(String estado) {
            this.estado = estado;
        }

        public String getValor() {
            return estado;
        }
    }

    public static enum EVENTO_CASO {
        CREAR(1), EDITAR(2), MIXTO(3);

        private final Integer caso;

        private EVENTO_CASO(Integer caso) {
            this.caso = caso;
        }

        public Integer getValor() {
            return caso;
        }
    }

    public static enum ESTADO_CITA {
        REGISTRADO("1"), CONFIRMADO("2"), EN_ATENCION("3"), FINALIZADO("4");

        private final String estado;

        private ESTADO_CITA(String estado) {
            this.estado = estado;
        }

        public String getValor() {
            return estado;
        }
    }

}
