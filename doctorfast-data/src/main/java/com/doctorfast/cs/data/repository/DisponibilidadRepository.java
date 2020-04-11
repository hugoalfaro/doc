/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Disponibilidad;
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
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer>, JpaSpecificationExecutor  {
    
    @Modifying
    @Transactional
    @Query("update Disponibilidad o set o.estado = :estado where o.idDisponibilidad = :idDisponibilidad")
    int setEstadoByIdDisponibilidad(@Param("estado") String estado, @Param("idDisponibilidad") Integer idDisponibilidad);
    
}
