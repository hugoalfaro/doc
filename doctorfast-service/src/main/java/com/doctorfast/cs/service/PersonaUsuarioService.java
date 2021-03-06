/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface PersonaUsuarioService {

    PersonaUsuarioBean save(PersonaUsuarioBean bean);

    List<PersonaUsuarioBean> findByUsuarioAndEstado(String usuario, String estado);

    List<PersonaUsuarioBean> findByCorreo(String correo);
    
    int setClaveByIdPersonaUsuario(String clave, Integer id);

}
