/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.util;

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

}
