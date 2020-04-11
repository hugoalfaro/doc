/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.CitaBean;
import com.doctorfast.cs.service.exception.ApplicationException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface CitaService {

    List<CitaBean> findAll(CitaBean bean);

    int setEstadoByIdCita(String estado, Integer id);

    int setEstadoCitaAndComentarioByIdCita(String estadoCita, String comentario, Integer id);

    CitaBean save(CitaBean bean) throws ApplicationException;

    CitaBean findOne(Integer id);

    List<CitaBean> findByIdProfesionalAndFecha(Integer idProfesional, Date fecha1, Date fecha2);

    List<CitaBean> findByIdProfesionalAndFechaAndIdCitaNot(Integer idProfesional, Date fecha1, Date fecha2, Integer idCita);

    List<CitaBean> findByIdSedeIdEspecialidadIdProfesionalAndFecha(Integer idSede, Integer idEspecialidad, Integer idProfesional, Date fecha1, Date fecha2);

    List<CitaBean> findByCodigoAndPacientePersonaUsuarioCorreo(String codigo, String correo);

}
