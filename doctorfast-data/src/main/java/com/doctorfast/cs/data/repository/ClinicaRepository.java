/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Clinica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author MBS GROUP
 */
public interface ClinicaRepository extends JpaRepository<Clinica, Integer>, JpaSpecificationExecutor {

    Clinica findByAdministradorsIdAdministrador(Integer idAdministrador);
    
    List<Clinica> findByEstadoOrderByOrdenAsc(String estado);

}
