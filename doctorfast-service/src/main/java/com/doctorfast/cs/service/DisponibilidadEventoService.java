/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.DisponibilidadEventoBean;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface DisponibilidadEventoService {

    List<DisponibilidadEventoBean> findAll(DisponibilidadEventoBean bean);

    int setEstadoByIdDisponibilidadEvento(String estado, Integer id);
    
    int setEstadoByIdDisponibilidadAndFechaInicioAfter(String estado, Integer idDisponibilidad, Date fechaInicio);
    
    Long contarPorIdDisponibilidadEstado(Integer idDisponibilidad, String estado);

    DisponibilidadEventoBean save(DisponibilidadEventoBean bean);

    void save(List<DisponibilidadEventoBean> bean);
    
    void delete(List<DisponibilidadEventoBean> bean);

    DisponibilidadEventoBean findOne(Integer id);
    
    List<DisponibilidadEventoBean> findByIdProfesionalAndFecha(Integer idProfesional, Date fecha1, Date fecha2);
    
    List<DisponibilidadEventoBean> findByIdProfesionalAndFechaAndIdDisponibilidadNot(Integer idProfesional, Date fecha1, Date fecha2, Integer idDisponibilidad);
    
    List<DisponibilidadEventoBean> findByIdSedeIdEspecialidadIdProfesionalAndFecha(Integer idSede, Integer idEspecialidad, Integer idProfesional, Date fecha1, Date fecha2);
    
}
