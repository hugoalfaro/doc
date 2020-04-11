/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.AdministradorBean;
import com.doctorfast.cs.data.mapper.AdministradorMapper;
import com.doctorfast.cs.data.repository.AdministradorRepository;
import com.doctorfast.cs.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBS GROUP
 */
@Service
public class AdministradorServiceImpl implements AdministradorService {
    
    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public AdministradorBean findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario) {
        return AdministradorMapper.INSTANCE.toBean(administradorRepository.findOne(idPersonaUsuario));
    }
    
}
