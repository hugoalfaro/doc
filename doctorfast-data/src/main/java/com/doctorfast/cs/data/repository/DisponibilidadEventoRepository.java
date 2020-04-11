/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.DisponibilidadEvento;
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
public interface DisponibilidadEventoRepository extends JpaRepository<DisponibilidadEvento, Integer>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query("update DisponibilidadEvento o set o.estado = :estado where o.idDisponibilidadEvento = :idDisponibilidadEvento")
    int setEstadoByIdDisponibilidadEvento(@Param("estado") String estado, @Param("idDisponibilidadEvento") Integer idDisponibilidadEvento);

    @Modifying
    @Transactional
    @Query("update DisponibilidadEvento o set o.estado = :estado where o.disponibilidad.idDisponibilidad = :idDisponibilidad and fechaInicio >= :fechaInicio")
    int setEstadoByIdDisponibilidadAndFechaInicioAfter(@Param("estado") String estado, @Param("idDisponibilidad") Integer idDisponibilidad, @Param("fechaInicio") Date fechaInicio);

    Long countByDisponibilidadIdDisponibilidadAndEstado(Integer idDisponibilidad, String estado);

    @Query(value = "select o.* "
            + "from disponibilidad_evento o "
            + "inner join disponibilidad d on o.id_disponibilidad = d.id_disponibilidad "
            + "inner join sede_esp_prof sep on d.id_especialidad = sep.id_especialidad and d.id_profesional = sep.id_profesional and d.id_sede = sep.id_sede "
            + "inner join sede_especialidad se on sep.id_especialidad = se.id_especialidad and sep.id_sede = se.id_sede "
            + "inner join sede s on se.id_sede = s.id_sede "
            + "inner join especialidad e on se.id_especialidad = e.id_especialidad "
            + "inner join profesional p on sep.id_profesional = p.id_profesional "
            + "where o.estado = '1' and d.estado = '1' and sep.estado = '1' and se.estado = '1' and p.estado = '1' and s.estado = '1' and e.estado = '1' "
            + "and sep.id_profesional = :idProfesional and (date_trunc('day', o.fecha_inicio) = :fecha1 or date_trunc('day', o.fecha_fin) = :fecha1 "
            + "or date_trunc('day', o.fecha_inicio) = :fecha2 or date_trunc('day', o.fecha_fin) = :fecha2) ",
            nativeQuery = true)
    List<DisponibilidadEvento> findByIdProfesionalAndFecha(@Param("idProfesional") Integer idProfesional, @Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);
    
    @Query(value = "select o.* "
            + "from disponibilidad_evento o "
            + "inner join disponibilidad d on o.id_disponibilidad = d.id_disponibilidad "
            + "inner join sede_esp_prof sep on d.id_especialidad = sep.id_especialidad and d.id_profesional = sep.id_profesional and d.id_sede = sep.id_sede "
            + "inner join sede_especialidad se on sep.id_especialidad = se.id_especialidad and sep.id_sede = se.id_sede "
            + "inner join sede s on se.id_sede = s.id_sede "
            + "inner join especialidad e on se.id_especialidad = e.id_especialidad "
            + "inner join profesional p on sep.id_profesional = p.id_profesional "
            + "where o.estado = '1' and d.estado = '1' and sep.estado = '1' and se.estado = '1' and p.estado = '1' and s.estado = '1' and e.estado = '1' "
            + "and sep.id_profesional = :idProfesional and (date_trunc('day', o.fecha_inicio) = :fecha1 or date_trunc('day', o.fecha_fin) = :fecha1 "
            + "or date_trunc('day', o.fecha_inicio) = :fecha2 or date_trunc('day', o.fecha_fin) = :fecha2) and d.id_disponibilidad != :idDisponibilidad ",
            nativeQuery = true)
    List<DisponibilidadEvento> findByIdProfesionalAndFechaAndIdDisponibilidadNot(@Param("idProfesional") Integer idProfesional, @Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("idDisponibilidad") Integer idDisponibilidad);

    @Query(value = "select o.* "
            + "from disponibilidad_evento o "
            + "inner join disponibilidad d on o.id_disponibilidad = d.id_disponibilidad "
            + "inner join sede_esp_prof sep on d.id_especialidad = sep.id_especialidad and d.id_profesional = sep.id_profesional and d.id_sede = sep.id_sede "
            + "inner join sede_especialidad se on sep.id_especialidad = se.id_especialidad and sep.id_sede = se.id_sede "
            + "inner join sede s on se.id_sede = s.id_sede "
            + "inner join especialidad e on se.id_especialidad = e.id_especialidad "
            + "inner join profesional p on sep.id_profesional = p.id_profesional "
            + "where o.estado = '1' and d.estado = '1' and sep.estado = '1' and se.estado = '1' and p.estado = '1' and s.estado = '1' and e.estado = '1' "
            + "and sep.id_sede = :idSede and sep.id_especialidad = :idEspecialidad and sep.id_profesional = :idProfesional "
            + "and (date_trunc('day', o.fecha_inicio) = :fecha1 or date_trunc('day', o.fecha_fin) = :fecha1 "
            + "or date_trunc('day', o.fecha_inicio) = :fecha2 or date_trunc('day', o.fecha_fin) = :fecha2) ",
            nativeQuery = true)
    List<DisponibilidadEvento> findByIdSedeIdEspecialidadIdProfesionalAndFecha(@Param("idSede") Integer idSede, @Param("idEspecialidad") Integer idEspecialidad, @Param("idProfesional") Integer idProfesional, @Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);

}
