/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.SedeEspProfesional;
import com.doctorfast.cs.data.model.SedeEspProfesionalId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author MBS GROUP
 */
public interface SedeEspProfesionalRepository extends JpaRepository<SedeEspProfesional, SedeEspProfesionalId>, JpaSpecificationExecutor {
    
    public List<SedeEspProfesional> findByIdIdProfesionalAndSedeEspecialidadSedeClinicaIdClinica(Integer idProfesional, Integer idClinica);
    
}
