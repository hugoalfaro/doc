/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.dto;

import com.doctorfast.cs.data.bean.CitaBean;
import com.doctorfast.cs.data.bean.DisponibilidadBean;
import java.util.Date;

/**
 *
 * @author MBS GROUP
 */
public class CalendarioDto {

    private String title;
    public boolean allDay;
    private Date start;
    private Date end;
    private String color;
    private String textColor;
    private String rendering;
    private String tipo;
    private Integer idDisponibilidadEvento;
    private DisponibilidadBean disponibilidad;
    private CitaBean cita;

    public CalendarioDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getRendering() {
        return rendering;
    }

    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdDisponibilidadEvento() {
        return idDisponibilidadEvento;
    }

    public void setIdDisponibilidadEvento(Integer idDisponibilidadEvento) {
        this.idDisponibilidadEvento = idDisponibilidadEvento;
    }

    public DisponibilidadBean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadBean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public CitaBean getCita() {
        return cita;
    }

    public void setCita(CitaBean cita) {
        this.cita = cita;
    }

}
