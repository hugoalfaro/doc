/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.AdministradorBean;

/**
 *
 * @author MBS GROUP
 */
public interface AdministradorService {

    AdministradorBean findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario);
    
}
