/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author MBS GROUP
 */
public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer>, JpaSpecificationExecutor {

}
