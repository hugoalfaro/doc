/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.AsistenteBean;
import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.model.Asistente;
import com.doctorfast.cs.data.model.Clinica;
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
public interface AsistenteMapper {

    AsistenteMapper INSTANCE = Mappers.getMapper(AsistenteMapper.class);

    @Mappings({
        @Mapping(target = "clinica", ignore = true)
    })
    AsistenteBean toBean(Asistente modelo);

    Asistente toModel(AsistenteBean bean);

    List<AsistenteBean> toBean(List<Asistente> modelo);

    List<Asistente> toModel(List<AsistenteBean> bean);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuarioBean toBean(PersonaUsuario modelo);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuario toModel(PersonaUsuarioBean bean);

    @Mappings({
        @Mapping(target = "profesionals", ignore = true)
    })
    Clinica toModel(ClinicaBean bean);
    
}
