/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Asistente;
import com.doctorfast.cs.data.model.Clinica;
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
public interface AsistenteRepository extends JpaRepository<Asistente, Integer>, JpaSpecificationExecutor {

    @Query("select o.clinica from Asistente o "
            + "where o.personaUsuario.idPersonaUsuario = :idPersonaUsuario ")
    public Clinica findClinicaByIdPersonaUsuario(@Param(value = "idPersonaUsuario") Integer idPersonaUsuario);
    
    @Modifying
    @Transactional
    @Query("update Asistente o set o.estado = :estado where o.idAsistente = :idAsistente")
    int setEstadoByIdAsistente(@Param("estado") String estado, @Param("idAsistente") Integer idAsistente);

    @Query("select o.idAsistente from Asistente o "
            + "where o.personaUsuario.documentoIdentidad = :documentoIdentidad and o.estado = '1' ")
    public List<Integer> findIdAsistenteByDocumentoIdentidad(@Param(value = "documentoIdentidad") String documentoIdentidad);
    
    Asistente findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario);

}
