/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.DisponibilidadBean;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface DisponibilidadService {

    List<DisponibilidadBean> findAll(DisponibilidadBean bean);

    int setEstadoByIdDisponibilidad(String estado, Integer id);

    DisponibilidadBean save(DisponibilidadBean bean);

    DisponibilidadBean findOne(Integer id);
    
}
