/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.AsistenteBean;
import com.doctorfast.cs.service.exception.ApplicationException;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface AsistenteService {

    List<AsistenteBean> findAll(AsistenteBean bean);

    int setEstadoByIdAsistente(String estado, Integer id);

    AsistenteBean save(AsistenteBean bean) throws ApplicationException;

    AsistenteBean findOne(Integer id);

    AsistenteBean findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario);
    
}
