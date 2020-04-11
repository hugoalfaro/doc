/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.SedeEspSubespBean;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface SedeEspSubespService {

    void delete(Integer idSede, Integer idEspecialidad, Integer idSubespecialidad);

    List<SedeEspSubespBean> findByIdIdSedeAndIdIdEspecialidad(Integer idSede, Integer idEspecialidad);

    SedeEspSubespBean save(SedeEspSubespBean bean);

}
