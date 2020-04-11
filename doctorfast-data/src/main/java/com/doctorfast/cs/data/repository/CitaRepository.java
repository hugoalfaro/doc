/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Cita;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MBS GROUP
 */
public interface CitaRepository extends JpaRepository<Cita, Integer>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query("update Cita o set o.estado = :estado where o.idCita = :idCita")
    int setEstadoByIdCita(@Param("estado") String estado, @Param("idCita") Integer idCita);

    @Modifying
    @Transactional
    @Query("update Cita o set o.estadoCita = :estadoCita, o.comentario = :comentario where o.idCita = :idCita")
    int setEstadoCitaAndComentarioByIdCita(@Param("estadoCita") String estadoCita, @Param("comentario") String comentario, @Param("idCita") Integer idCita);

    @Query(value = "select o.* "
            + "from cita o "
            + "inner join sede_esp_prof sep on o.id_especialidad = sep.id_especialidad and o.id_profesional = sep.id_profesional and o.id_sede = sep.id_sede "
            + "inner join sede_especialidad se on sep.id_especialidad = se.id_especialidad and sep.id_sede = se.id_sede "
            + "inner join sede s on se.id_sede = s.id_sede "
            + "inner join especialidad e on se.id_especialidad = e.id_especialidad "
            + "inner join profesional p on sep.id_profesional = p.id_profesional "
            + "where o.estado = '1' and sep.estado = '1' and se.estado = '1' and p.estado = '1' and s.estado = '1' and e.estado = '1' "
            + "and sep.id_profesional = :idProfesional and (date_trunc('day', o.fecha_atencion) = :fecha1 or date_trunc('day', o.fecha_atencion) = :fecha2) ",
            nativeQuery = true)
    List<Cita> findByIdProfesionalAndFecha(@Param("idProfesional") Integer idProfesional, @Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);

    @Query(value = "select o.* "
            + "from cita o "
            + "inner join sede_esp_prof sep on o.id_especialidad = sep.id_especialidad and o.id_profesional = sep.id_profesional and o.id_sede = sep.id_sede "
            + "inner join sede_especialidad se on sep.id_especialidad = se.id_especialidad and sep.id_sede = se.id_sede "
            + "inner join sede s on se.id_sede = s.id_sede "
            + "inner join especialidad e on se.id_especialidad = e.id_especialidad "
            + "inner join profesional p on sep.id_profesional = p.id_profesional "
            + "where o.estado = '1' and sep.estado = '1' and se.estado = '1' and p.estado = '1' and s.estado = '1' and e.estado = '1' "
            + "and sep.id_profesional = :idProfesional and (date_trunc('day', o.fecha_atencion) = :fecha1 or date_trunc('day', o.fecha_atencion) = :fecha2) and o.id_cita != :idCita ",
            nativeQuery = true)
    List<Cita> findByIdProfesionalAndFechaAndIdCitaNot(@Param("idProfesional") Integer idProfesional, @Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("idCita") Integer idCita);

    @Query(value = "select o.* "
            + "from cita o "
            + "inner join sede_esp_prof sep on o.id_especialidad = sep.id_especialidad and o.id_profesional = sep.id_profesional and o.id_sede = sep.id_sede "
            + "inner join sede_especialidad se on sep.id_especialidad = se.id_especialidad and sep.id_sede = se.id_sede "
            + "inner join sede s on se.id_sede = s.id_sede "
            + "inner join especialidad e on se.id_especialidad = e.id_especialidad "
            + "inner join profesional p on sep.id_profesional = p.id_profesional "
            + "where o.estado = '1' and sep.estado = '1' and se.estado = '1' and p.estado = '1' and s.estado = '1' and e.estado = '1' "
            + "and sep.id_sede = :idSede and sep.id_especialidad = :idEspecialidad and sep.id_profesional = :idProfesional "
            + "and (date_trunc('day', o.fecha_atencion) = :fecha1 or date_trunc('day', o.fecha_atencion) = :fecha2) ",
            nativeQuery = true)
    List<Cita> findByIdSedeIdEspecialidadIdProfesionalAndFecha(@Param("idSede") Integer idSede, @Param("idEspecialidad") Integer idEspecialidad, @Param("idProfesional") Integer idProfesional,
            @Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);

    List<Cita> findByCodigoAndPacientePersonaUsuarioCorreo(String codigo, String correo);
    
    Cita findTopByOrderByIdCitaDesc();

}
