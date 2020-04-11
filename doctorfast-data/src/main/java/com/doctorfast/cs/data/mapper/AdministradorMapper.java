/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.AdministradorBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.model.Administrador;
import com.doctorfast.cs.data.model.PersonaUsuario;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author MBS GROUP
 */
@Mapper
public interface AdministradorMapper {

    AdministradorMapper INSTANCE = Mappers.getMapper(AdministradorMapper.class);

    AdministradorBean toBean(Administrador modelo);

    Administrador toModel(AdministradorBean bean);

    List<AdministradorBean> toBean(List<Administrador> modelo);

    List<Administrador> toModel(List<AdministradorBean> bean);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuarioBean toBean(PersonaUsuario modelo);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuario toModel(PersonaUsuarioBean bean);
    
}
