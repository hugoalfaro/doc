/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.CitaBean;
import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.PacienteBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalIdBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadIdBean;
import com.doctorfast.cs.data.bean.SubespecialidadBean;
import com.doctorfast.cs.data.bean.UbigeoBean;
import com.doctorfast.cs.data.bean.UbigeoIdBean;
import com.doctorfast.cs.data.model.Cita;
import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.Especialidad;
import com.doctorfast.cs.data.model.Paciente;
import com.doctorfast.cs.data.model.PersonaUsuario;
import com.doctorfast.cs.data.model.Profesional;
import com.doctorfast.cs.data.model.Sede;
import com.doctorfast.cs.data.model.SedeEspProfesional;
import com.doctorfast.cs.data.model.SedeEspProfesionalId;
import com.doctorfast.cs.data.model.SedeEspecialidad;
import com.doctorfast.cs.data.model.SedeEspecialidadId;
import com.doctorfast.cs.data.model.Subespecialidad;
import com.doctorfast.cs.data.model.Ubigeo;
import com.doctorfast.cs.data.model.UbigeoId;
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
public interface CitaMapper {

    CitaMapper INSTANCE = Mappers.getMapper(CitaMapper.class);

    CitaBean toBean(Cita modelo);

    Cita toModel(CitaBean bean);

    List<CitaBean> toBean(List<Cita> modelo);

    List<Cita> toModel(List<CitaBean> bean);

    SedeEspProfesionalBean toBean(SedeEspProfesional modelo);

    @Mappings({
        @Mapping(target = "sedeEspecialidad", ignore = true),
        @Mapping(target = "profesional", ignore = true)
    })
    SedeEspProfesional toModel(SedeEspProfesionalBean bean);

    SedeEspProfesionalIdBean toBean(SedeEspProfesionalId modelo);

    SedeEspProfesionalId toModel(SedeEspProfesionalIdBean bean);

    PacienteBean toBean(Paciente modelo);

    Paciente toModel(PacienteBean bean);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuarioBean toBean(PersonaUsuario modelo);

    @Mappings({
        @Mapping(target = "perfil", ignore = true)
    })
    PersonaUsuario toModel(PersonaUsuarioBean bean);

    @Mappings({
        @Mapping(target = "especialidad", ignore = true)
    })
    SubespecialidadBean toBean(Subespecialidad modelo);

    @Mappings({
        @Mapping(target = "especialidad", ignore = true)
    })
    Subespecialidad toModel(SubespecialidadBean bean);

    @Mappings({
        @Mapping(target = "sedeEspProfesionals", ignore = true),
        @Mapping(target = "clinica", ignore = true)
    })
    ProfesionalBean toBean(Profesional modelo);

    SedeEspecialidadBean toBean(SedeEspecialidad modelo);

    SedeEspecialidadIdBean toBean(SedeEspecialidadId modelo);

    SedeBean toBean(Sede modelo);

    UbigeoBean toBean(Ubigeo modelo);

    UbigeoIdBean toBean(UbigeoId modelo);

    EspecialidadBean toBean(Especialidad modelo);

    @Mappings({
        @Mapping(target = "profesionals", ignore = true)
    })
    ClinicaBean toBean(Clinica modelo);

    @Mappings({
        @Mapping(target = "profesionals", ignore = true)
    })
    Clinica toModel(ClinicaBean bean);

}
