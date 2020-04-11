/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.mapper.PersonaUsuarioMapper;
import com.doctorfast.cs.data.model.PersonaUsuario;
import com.doctorfast.cs.data.repository.PersonaUsuarioRepository;
import com.doctorfast.cs.service.PersonaUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBS GROUP
 */
@Service
public class PersonaUsuarioServiceImpl implements PersonaUsuarioService {

    @Autowired
    private PersonaUsuarioRepository personaUsuarioRepository;

    @Override
    public PersonaUsuarioBean save(PersonaUsuarioBean bean) {
        PersonaUsuario model = PersonaUsuarioMapper.INSTANCE.toModel(bean);
        model = personaUsuarioRepository.save(model);
        return PersonaUsuarioMapper.INSTANCE.toBean(model);
    }

    @Override
    public List<PersonaUsuarioBean> findByUsuarioAndEstado(String usuario, String estado) {
        return PersonaUsuarioMapper.INSTANCE.toBean(personaUsuarioRepository.findByUsuarioAndEstado(usuario, estado));
    }

    @Override
    public List<PersonaUsuarioBean> findByCorreo(String correo) {
        return PersonaUsuarioMapper.INSTANCE.toBean(personaUsuarioRepository.findByCorreo(correo));
    }

    @Override
    public int setClaveByIdPersonaUsuario(String clave, Integer id) {
        return personaUsuarioRepository.setClaveByIdPersonaUsuario(clave, id);
    }

}
