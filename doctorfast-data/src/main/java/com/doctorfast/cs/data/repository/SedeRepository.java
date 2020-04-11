/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Sede;
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
public interface SedeRepository extends JpaRepository<Sede, Integer>, JpaSpecificationExecutor {

    @Modifying
    @Transactional
    @Query("update Sede o set o.estado = :estado where o.idSede = :idSede")
    int setEstadoByIdSede(@Param("estado") String estado, @Param("idSede") Integer idSede);
    
    @Query("select max(o.codigo)+1 from Sede o where o.clinica.idClinica = :idClinica")
    Integer obtenerCodigoSiguiente(@Param("idClinica") Integer idClinica);
    
    Sede findByNombreAndClinicaIdClinicaAndUbigeoIdCoDepartamentoAndUbigeoIdCoProvinciaAndUbigeoIdCoDistrito(String nombre, Integer idClinica, Integer coDepartamento, Integer coProvincia, Integer coDistrito);
    
    Sede findByNombreAndClinicaIdClinicaAndUbigeoIdCoDepartamentoAndUbigeoIdCoProvinciaAndUbigeoIdCoDistritoAndIdSedeNot(String nombre, Integer idClinica, Integer coDepartamento, Integer coProvincia, Integer coDistrito, Integer idSede);

}
