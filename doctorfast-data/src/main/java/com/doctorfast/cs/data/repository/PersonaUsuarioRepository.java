/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.PersonaUsuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MBS GROUP
 */
public interface PersonaUsuarioRepository extends JpaRepository<PersonaUsuario, Integer> {

    public List<PersonaUsuario> findByUsuarioAndEstado(String usuario, String estado);

    public List<PersonaUsuario> findByCorreo(String correo);

    @Modifying
    @Transactional
    @Query("update PersonaUsuario o set o.clave = :clave where o.idPersonaUsuario = :idPersonaUsuario")
    int setClaveByIdPersonaUsuario(@Param("clave") String clave, @Param("idPersonaUsuario") Integer idPersonaUsuario);

    @Modifying
    @Transactional
    @Query("update PersonaUsuario o set o.estado = :estado where o.idPersonaUsuario = :idPersonaUsuario")
    int setEstadoByIdPersonaUsuario(@Param("estado") String estado, @Param("idPersonaUsuario") Integer idPersonaUsuario);

    List<PersonaUsuario> findByEstadoNotAndDocumentoIdentidad(String estado, String documentoIdentidad);

    List<PersonaUsuario> findByEstadoNotAndDocumentoIdentidadAndIdPersonaUsuarioNot(String estado, String documentoIdentidad, Integer idPersonaUsuario);

    List<PersonaUsuario> findByEstadoNotAndDocumentoIdentidadAndPerfilIdPerfilNot(String estado, String documentoIdentidad, Integer idPerfil);

    List<PersonaUsuario> findByEstadoNotAndCorreo(String estado, String correo);

    List<PersonaUsuario> findByEstadoNotAndCorreoAndIdPersonaUsuarioNot(String estado, String correo, Integer idPersonaUsuario);

    List<PersonaUsuario> findByEstadoNotAndDocumentoIdentidadNotAndCorreoAndPerfilIdPerfil(String estado, String documentoIdentidad, String correo, Integer idPerfil);

    List<PersonaUsuario> findByEstadoNotAndCorreoAndPerfilIdPerfilNot(String estado, String correo, Integer idPerfil);

    List<PersonaUsuario> findByEstadoNotAndDocumentoIdentidadNotAndCorreoAndPerfilIdPerfilAndIdPersonaUsuarioNot(String estado, String documentoIdentidad, String correo, Integer idPerfil, Integer idPersonaUsuario);

    List<PersonaUsuario> findByEstadoNotAndCorreoAndPerfilIdPerfilNotAndIdPersonaUsuarioNot(String estado, String correo, Integer idPerfil, Integer idPersonaUsuario);

}
