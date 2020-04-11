/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.service.exception.ApplicationException;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface ProfesionalService {

    List<ProfesionalBean> findAll(ProfesionalBean bean);

    int setEstadoByIdProfesional(String estado, Integer id);

    ProfesionalBean save(ProfesionalBean bean) throws ApplicationException;

    ProfesionalBean findOne(Integer id);
    
    List<ProfesionalBean> findClinicaByDocumentoIdentidad(String documentoIdentidad);

    ProfesionalBean findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario);

}
