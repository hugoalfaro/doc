/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadIdBean;
import com.doctorfast.cs.data.bean.UbigeoBean;
import com.doctorfast.cs.data.bean.UbigeoIdBean;
import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.Especialidad;
import com.doctorfast.cs.data.model.Sede;
import com.doctorfast.cs.data.model.SedeEspecialidad;
import com.doctorfast.cs.data.model.SedeEspecialidadId;
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
public interface SedeEspecialidadMapper {

    SedeEspecialidadMapper INSTANCE = Mappers.getMapper(SedeEspecialidadMapper.class);

    SedeEspecialidadBean toBean(SedeEspecialidad modelo);

    SedeEspecialidad toModel(SedeEspecialidadBean bean);

    List<SedeEspecialidadBean> toBean(List<SedeEspecialidad> modelo);

    List<SedeEspecialidad> toModel(List<SedeEspecialidadBean> bean);

    SedeEspecialidadIdBean toBean(SedeEspecialidadId modelo);

    SedeEspecialidadId toModel(SedeEspecialidadIdBean bean);

    SedeBean toBean(Sede modelo);

    @Mappings({
        @Mapping(target = "clinica", ignore = true),
        @Mapping(target = "ubigeo", ignore = true)
    })
    Sede toModel(SedeBean bean);

    EspecialidadBean toBean(Especialidad modelo);

    Especialidad toModel(EspecialidadBean bean);

    @Mappings({
        @Mapping(target = "profesionals", ignore = true)
    })
    ClinicaBean toBean(Clinica modelo);

    UbigeoBean toBean(Ubigeo modelo);

    UbigeoIdBean toBean(UbigeoId modelo);

}
