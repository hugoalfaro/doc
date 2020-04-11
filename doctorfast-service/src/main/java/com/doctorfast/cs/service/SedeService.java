/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.service.exception.ApplicationException;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface SedeService {

    List<SedeBean> findAll(SedeBean bean);

    int setEstadoByIdSede(String estado, Integer id);

    SedeBean save(SedeBean bean, Integer idClinica) throws ApplicationException;

    SedeBean findOne(Integer id);

}
