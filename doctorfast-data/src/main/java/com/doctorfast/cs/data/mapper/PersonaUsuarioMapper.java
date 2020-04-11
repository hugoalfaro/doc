/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.PerfilBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.model.Perfil;
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
public interface PersonaUsuarioMapper {

    PersonaUsuarioMapper INSTANCE = Mappers.getMapper(PersonaUsuarioMapper.class);

    PersonaUsuarioBean toBean(PersonaUsuario modelo);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuario toModel(PersonaUsuarioBean bean);

    List<PersonaUsuarioBean> toBean(List<PersonaUsuario> modelo);

    List<PersonaUsuario> toModel(List<PersonaUsuarioBean> bean);
    
    PerfilBean toBean(Perfil modelo);

}
