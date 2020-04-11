/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.DisponibilidadBean;
import com.doctorfast.cs.data.bean.DisponibilidadEventoBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalIdBean;
import com.doctorfast.cs.data.model.Disponibilidad;
import com.doctorfast.cs.data.model.DisponibilidadEvento;
import com.doctorfast.cs.data.model.PersonaUsuario;
import com.doctorfast.cs.data.model.Profesional;
import com.doctorfast.cs.data.model.SedeEspProfesional;
import com.doctorfast.cs.data.model.SedeEspProfesionalId;
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
public interface DisponibilidadEventoMapper {

    DisponibilidadEventoMapper INSTANCE = Mappers.getMapper(DisponibilidadEventoMapper.class);

    DisponibilidadEventoBean toBean(DisponibilidadEvento modelo);

    DisponibilidadEvento toModel(DisponibilidadEventoBean bean);

    List<DisponibilidadEventoBean> toBean(List<DisponibilidadEvento> modelo);

    List<DisponibilidadEvento> toModel(List<DisponibilidadEventoBean> bean);

    @Mappings({
        @Mapping(target = "disponibilidadEventos", ignore = true)
    })
    DisponibilidadBean toBean(Disponibilidad modelo);

    @Mappings({
        @Mapping(target = "disponibilidadEventos", ignore = true)
    })
    Disponibilidad toModel(DisponibilidadBean bean);

    @Mappings({
        @Mapping(target = "sedeEspecialidad", ignore = true)
    })
    SedeEspProfesionalBean toBean(SedeEspProfesional modelo);

    @Mappings({
        @Mapping(target = "sedeEspecialidad", ignore = true),
        @Mapping(target = "profesional", ignore = true)
    })
    SedeEspProfesional toModel(SedeEspProfesionalBean bean);

    SedeEspProfesionalIdBean toBean(SedeEspProfesionalId modelo);

    SedeEspProfesionalId toModel(SedeEspProfesionalIdBean bean);

    @Mappings({
        @Mapping(target = "sedeEspProfesionals", ignore = true),
        @Mapping(target = "clinica", ignore = true)
    })
    ProfesionalBean toBean(Profesional modelo);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuarioBean toBean(PersonaUsuario modelo);
    
}
