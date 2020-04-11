/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import org.apache.commons.validator.routines.LongValidator;
import org.springframework.validation.Errors;

/**
 *
 * @author MBS GROUP
 */
public final class FuncionesUtil {

    public static void validarTexto(String nombreCampo, String campo, int tamanioMax, Errors errors) {
        if (campo != null && campo.trim().length() > 0) {
            if (campo.trim().length() > tamanioMax) {
                errors.rejectValue(nombreCampo, "validation." + nombreCampo + ".max");
            }
        }
    }

    public static void validarNumero(String nombreCampo, String campo, int tamanioMax, Errors errors) {

        if (campo != null && campo.trim().length() > 0) {
            if (LongValidator.getInstance().isValid(campo)) {
                if (campo.trim().length() > tamanioMax) {
                    errors.rejectValue(nombreCampo, "validation." + nombreCampo + ".max");
                }
            } else {
                errors.rejectValue(nombreCampo, "validation." + nombreCampo + ".notValidValue");
            }
        }
    }

    public static boolean esNuloOVacio(String valor) {
        return valor == null || valor.isEmpty();
    }

    public static boolean esNuloOVacio(Integer valor) {
        return valor == null || valor == 0;
    }

    public static boolean esNuloOVacio(List valor) {
        return valor == null || valor.isEmpty();
    }

    public static String md5(String s) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(s.getBytes(), 0, s.length());
        return new BigInteger(1, m.digest()).toString(16);
    }

    public static Integer convertirStringAInteger(String cadena) {
        try {
            return Integer.parseInt(cadena);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer[] convertirStringAArrayDays(String array) {
        if (array == null || array.equals("")) {
            return new Integer[0];
        }
        array = array.replaceFirst("L", "" + Calendar.MONDAY)
                .replaceFirst("M", "" + Calendar.TUESDAY)
                .replaceFirst("X", "" + Calendar.WEDNESDAY)
                .replaceFirst("J", "" + Calendar.THURSDAY)
                .replaceFirst("V", "" + Calendar.FRIDAY)
                .replaceFirst("S", "" + Calendar.SATURDAY)
                .replaceFirst("D", "" + Calendar.SUNDAY);

        String a[] = array.split(",");
        Integer r[] = new Integer[a.length];

        for (int i = 0; i < a.length; i++) {
            r[i] = convertirStringAInteger(a[i]);
        }
        return r;
    }

    public static Date removerTiempo(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date sumarHorasMinutosFecha(Date fecha, Integer horas, Integer minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.HOUR, horas);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    public static String obtenerCadenaAleatoria(int tamanio) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < tamanio; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static String obtenerDateConFormato(Date date, String formato) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(formato);
        return sdfDate.format(date);
    }

    public static Date obtenerMenor(Date a, Date b) {
        return (a.before(b)) ? a : b;
    }

    public static Date obtenerMayor(Date a, Date b) {
        return (a.before(b)) ? b : a;
    }

    public static boolean existeCruce(Date inicio1, Date fin1, Date inicio2, Date fin2) {
        return FuncionesUtil.obtenerMayor(inicio1, inicio2).before(FuncionesUtil.obtenerMenor(fin1, fin2));
    }

    public static boolean estaDentro(Date inicio1, Date fin1, Date inicio2, Date fin2) {
        return inicio1.compareTo(inicio2) <= 0 && fin1.compareTo(fin2) >= 0;
    }

    public static String mensajeCruce(Date inicio, Date fin) {
        return "- " + FuncionesUtil.obtenerDateConFormato(inicio, "dd/MM/yyyy") + " de "
                + FuncionesUtil.obtenerDateConFormato(inicio, "HH:mm") + " a "
                + FuncionesUtil.obtenerDateConFormato(fin, "HH:mm") + ". ";
    }

    public static String obtenerSiguienteCodigoAlfaNumerico(String number) {
        char[] cars = number.toUpperCase().toCharArray();
        for (int i = cars.length - 1; i >= 0; i--) {
            if (cars[i] == 'Z') {
                cars[i] = 'A';
            } else if (cars[i] == '9') {
                cars[i] = '0';
            } else {
                cars[i]++;
                break;
            }
        }
        return String.valueOf(cars);
    }

    public static String obtenerFechaEnOtroTimezone(Date fecha, String timezone) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        return formatter.format(fecha);
    }

}
