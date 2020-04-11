/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.ClinicaBean;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface ClinicaService {

    List<ClinicaBean> findAll(ClinicaBean bean, String order);

    ClinicaBean findByAdministradorsIdAdministrador(Integer idAdministrador);

    ClinicaBean save(ClinicaBean bean);
    
    ClinicaBean findOne(Integer idClinica);
    
}
