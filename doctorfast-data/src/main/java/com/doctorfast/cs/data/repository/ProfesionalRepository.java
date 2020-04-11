/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Profesional;
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
public interface ProfesionalRepository extends JpaRepository<Profesional, Integer>, JpaSpecificationExecutor {
    
    @Modifying
    @Transactional
    @Query("update Profesional o set o.estado = :estado where o.idProfesional = :idProfesional")
    int setEstadoByIdProfesional(@Param("estado") String estado, @Param("idProfesional") Integer idProfesional);

    @Query("select o.idProfesional from Profesional o "
            + "where o.personaUsuario.documentoIdentidad = :documentoIdentidad and o.estado = '1' ")
    public List<Integer> findIdProfesionalByDocumentoIdentidad(@Param(value = "documentoIdentidad") String documentoIdentidad);
    
    @Query("select o from Profesional o " +
            "join o.clinica c " +
            "join o.personaUsuario p " +    
            "where o.estado = '1' and c.estado = '1' and p.documentoIdentidad = :documentoIdentidad ")
    List<Profesional> findClinicaByDocumentoIdentidad(@Param(value = "documentoIdentidad") String documentoIdentidad);
    
    Profesional findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario);

    List<Profesional> findByEstadoNotAndPersonaUsuarioDocumentoIdentidadAndClinicaIdClinica(String estado, String documentoIdentidad, Integer idClinica);

    List<Profesional> findByEstadoNotAndPersonaUsuarioDocumentoIdentidadAndClinicaIdClinicaAndPersonaUsuarioIdPersonaUsuarioNot(String estado, String documentoIdentidad, Integer idClinica, Integer idPersonaUsuario);
    
}
