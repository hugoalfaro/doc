/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.SedeEspSubesp;
import com.doctorfast.cs.data.model.SedeEspSubespId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author MBS GROUP
 */
public interface SedeEspSubespRepository extends JpaRepository<SedeEspSubesp, SedeEspSubespId>, JpaSpecificationExecutor {

    List<SedeEspSubesp> findByIdIdSedeAndIdIdEspecialidad(Integer idSede, Integer idEspecialidad);

}
