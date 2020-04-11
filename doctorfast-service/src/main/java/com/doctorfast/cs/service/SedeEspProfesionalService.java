/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface SedeEspProfesionalService {
    
    List<SedeEspProfesionalBean> findAll(SedeEspProfesionalBean bean, String order);

    List<SedeEspProfesionalBean> save(List<SedeEspProfesionalBean> bean);

    void delete(List<SedeEspProfesionalBean> bean);
    
    List<SedeEspProfesionalBean> findByIdProfesionalAndIdClinica(Integer idProfesional, Integer idClinica);

}
