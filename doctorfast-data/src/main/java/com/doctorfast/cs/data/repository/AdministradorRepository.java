/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Administrador;
import com.doctorfast.cs.data.model.Clinica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author MBS GROUP
 */
public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {

    @Query("select o.clinica from Administrador o "
            + "where o.personaUsuario.idPersonaUsuario = :idPersonaUsuario ")
    public Clinica findClinicaByIdPersonaUsuario(@Param(value = "idPersonaUsuario") Integer idPersonaUsuario);
    
    Administrador findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario);

    List<Administrador> findByEstadoNotAndPersonaUsuarioCorreo(String estado, String correo);

    List<Administrador> findByEstadoNotAndPersonaUsuarioIdPersonaUsuarioNotAndPersonaUsuarioCorreo(String estado, Integer idPersonaUsuario, String correo);

    List<Administrador> findByEstadoNotAndPersonaUsuarioDocumentoIdentidad(String estado, String documentoIdentidad);

}
