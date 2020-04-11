/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.util;

import com.doctorfast.cs.data.bean.DisponibilidadBean;
import com.doctorfast.cs.data.bean.DisponibilidadEventoBean;
import com.doctorfast.cs.service.util.FuncionesUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

/**
 *
 * @author MBS GROUP
 */
public class RepetirEventoUtil {

    private final Calendar start;
    private final Calendar start2;
    private final List<DisponibilidadEventoBean> lista = new ArrayList<>();
    private final Integer diasRepeticion[];
    private final DisponibilidadBean disponibilidad;
    private final Integer caso;
    private final String timezone;

    public RepetirEventoUtil(Calendar start, Calendar start2, Integer[] diasRepeticion, DisponibilidadBean d, Integer caso, String timezone) {
        this.start = start;
        this.start2 = start2;
        this.diasRepeticion = diasRepeticion;
        this.disponibilidad = d;
        this.caso = caso;
        this.timezone = timezone;
    }

    public List<DisponibilidadEventoBean> obtenerEventosRepetidos() {
        if (FuncionesUtil.esNuloOVacio(disponibilidad.getRepeticionVeces())) {
            if (disponibilidad.getRepeticionFin() == null) {
                return null;
            } else {
                Calendar end = Calendar.getInstance();
                end.setTime(disponibilidad.getRepeticionFin());
                end.add(Calendar.DATE, 1);

                switch (disponibilidad.getRepeticionTipo()) {
                    case 1:
                        while (start.before(end)) {
                            lista.add(construirEvento(disponibilidad, start.getTime(), start2.getTime(), caso));
                            start.add(Calendar.DATE, disponibilidad.getRepeticionCada());
                            start2.add(Calendar.DATE, disponibilidad.getRepeticionCada());
                        }
                        break;
                    case 2:
                    case 3:
                    case 4:
                        while (start.before(end)) {
                            agregarSiDiaCoincideConArray();
                            start.add(Calendar.DATE, 1);
                            start2.add(Calendar.DATE, 1);
                        }
                        break;
                    default:
                        if (disponibilidad.getRepeticionCada() == 1) {
                            while (start.before(end)) {
                                agregarSiDiaCoincideConArray();
                                start.add(Calendar.DATE, 1);
                                start2.add(Calendar.DATE, 1);
                            }
                        } else {
                            int semana = obtenerDeCalendario(start, Calendar.WEEK_OF_YEAR);
                            while (start.before(end)) {
                                agregarSiDiaCoincideConArray();
                                start.add(Calendar.DATE, 1);
                                start2.add(Calendar.DATE, 1);
                                semana = saltarSemanasSiEsOtraSemana(semana);
                            }
                        }
                }
            }
        } else if (disponibilidad.getRepeticionVeces() == null) {
            return null;
        } else {

            int contador = 0;

            switch (disponibilidad.getRepeticionTipo()) {
                case 1:
                    while (contador < disponibilidad.getRepeticionVeces()) {
                        lista.add(construirEvento(disponibilidad, start.getTime(), start2.getTime(), caso));
                        start.add(Calendar.DATE, disponibilidad.getRepeticionCada());
                        start2.add(Calendar.DATE, disponibilidad.getRepeticionCada());
                        contador++;
                    }
                    break;
                case 2:
                case 3:
                case 4:

                    while (contador < disponibilidad.getRepeticionVeces()) {
                        contador = agregarSiDiaCoincideConArray(contador);
                        start.add(Calendar.DATE, 1);
                        start2.add(Calendar.DATE, 1);
                    }
                    break;
                default:
                    if (disponibilidad.getRepeticionCada() == 1) {
                        while (contador < disponibilidad.getRepeticionVeces()) {
                            contador = agregarSiDiaCoincideConArray(contador);
                            start.add(Calendar.DATE, 1);
                            start2.add(Calendar.DATE, 1);
                        }
                    } else {
                        int semana = obtenerDeCalendario(start, Calendar.WEEK_OF_YEAR);
                        while (contador < disponibilidad.getRepeticionVeces()) {
                            contador = agregarSiDiaCoincideConArray(contador);
                            start.add(Calendar.DATE, 1);
                            start2.add(Calendar.DATE, 1);
                            semana = saltarSemanasSiEsOtraSemana(semana);
                        }
                    }
            }
        }
        return lista;
    }

    public void agregarSiDiaCoincideConArray() {
        int aux = 0;
        boolean buscar = true;
        while (aux < diasRepeticion.length && buscar) {
            if (obtenerDeCalendario(start, Calendar.DAY_OF_WEEK) == diasRepeticion[aux]) {
                lista.add(construirEvento(disponibilidad, start.getTime(), start2.getTime(), caso));
                buscar = false;
            }
            aux++;
        }
    }

    public Integer agregarSiDiaCoincideConArray(Integer contador) {
        int aux = 0;
        boolean buscar = true;
        while (aux < diasRepeticion.length && buscar) {
            if (obtenerDeCalendario(start, Calendar.DAY_OF_WEEK) == diasRepeticion[aux]) {
                lista.add(construirEvento(disponibilidad, start.getTime(), start2.getTime(), caso));
                buscar = false;
                contador++;
            }
            aux++;
        }
        return contador;
    }

    public Integer saltarSemanasSiEsOtraSemana(Integer semana) {
        Integer auxSemana = semana;
        semana = obtenerDeCalendario(start, Calendar.WEEK_OF_YEAR);

        if (!Objects.equals(semana, auxSemana)) {
            start.add(Calendar.WEEK_OF_YEAR, disponibilidad.getRepeticionCada() - 1);
            start2.add(Calendar.WEEK_OF_YEAR, disponibilidad.getRepeticionCada() - 1);
            semana = obtenerDeCalendario(start, Calendar.WEEK_OF_YEAR);
        }
        return semana;
    }

    public DisponibilidadEventoBean construirEvento(DisponibilidadBean bean, Date fechaInicio, Date fechaFin, int caso) {
        DisponibilidadEventoBean d = new DisponibilidadEventoBean();
        if (caso == ConstantesUtil.EVENTO_CASO.EDITAR.getValor()) {
            d.setIdDisponibilidadEvento(bean.getIdDisponibilidadEvento());
            d.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        }
        d.setFechaInicio(fechaInicio);
        d.setFechaFin(fechaFin);
        d.setDisponibilidad(bean);
        return d;
    }
    
    public int obtenerDeCalendario(Calendar fecha, int parametro){
        fecha.setTimeZone(TimeZone.getTimeZone(timezone));
        return fecha.get(parametro);
    }

}
