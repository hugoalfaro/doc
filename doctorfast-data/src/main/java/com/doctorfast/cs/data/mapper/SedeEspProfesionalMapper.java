/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalIdBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadIdBean;
import com.doctorfast.cs.data.model.Especialidad;
import com.doctorfast.cs.data.model.PersonaUsuario;
import com.doctorfast.cs.data.model.Profesional;
import com.doctorfast.cs.data.model.Sede;
import com.doctorfast.cs.data.model.SedeEspProfesional;
import com.doctorfast.cs.data.model.SedeEspProfesionalId;
import com.doctorfast.cs.data.model.SedeEspecialidad;
import com.doctorfast.cs.data.model.SedeEspecialidadId;
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
public interface SedeEspProfesionalMapper {

    SedeEspProfesionalMapper INSTANCE = Mappers.getMapper(SedeEspProfesionalMapper.class);

    SedeEspProfesionalBean toBean(SedeEspProfesional modelo);

    @Mappings({
        @Mapping(target = "sedeEspecialidad", ignore = true),
        @Mapping(target = "profesional", ignore = true)
    })
    SedeEspProfesional toModel(SedeEspProfesionalBean bean);

    SedeEspProfesionalIdBean toBean(SedeEspProfesionalId modelo);

    SedeEspProfesionalId toModel(SedeEspProfesionalIdBean bean);

    List<SedeEspProfesionalBean> toBean(List<SedeEspProfesional> modelo);

    List<SedeEspProfesional> toModel(List<SedeEspProfesionalBean> bean);

    SedeEspecialidadBean toBean(SedeEspecialidad modelo);

    SedeEspecialidadIdBean toBean(SedeEspecialidadId modelo);

    @Mappings({
        @Mapping(target = "clinica", ignore = true),
        @Mapping(target = "ubigeo", ignore = true)
    })
    SedeBean toBean(Sede modelo);

    EspecialidadBean toBean(Especialidad modelo);

    @Mappings({
        @Mapping(target = "clinica", ignore = true),
        @Mapping(target = "sedeEspProfesionals", ignore = true)
    })
    ProfesionalBean toBean(Profesional modelo);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuarioBean toBean(PersonaUsuario modelo);

}
