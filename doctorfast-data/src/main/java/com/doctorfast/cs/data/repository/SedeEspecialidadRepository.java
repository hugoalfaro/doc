/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.SedeEspecialidad;
import com.doctorfast.cs.data.model.SedeEspecialidadId;
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
public interface SedeEspecialidadRepository extends JpaRepository<SedeEspecialidad, SedeEspecialidadId>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query("update SedeEspecialidad o set o.estado = :estado where o.id.idSede = :idSede and o.id.idEspecialidad = :idEspecialidad")
    int setEstadoByIdSedeAndIdEspecialidad(@Param("estado") String estado, @Param("idSede") Integer idSede, @Param("idEspecialidad") Integer idEspecialidad);

}
