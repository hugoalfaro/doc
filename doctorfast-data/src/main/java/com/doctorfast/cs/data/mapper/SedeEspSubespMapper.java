/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.SedeEspSubespBean;
import com.doctorfast.cs.data.bean.SedeEspSubespIdBean;
import com.doctorfast.cs.data.bean.SubespecialidadBean;
import com.doctorfast.cs.data.model.SedeEspSubesp;
import com.doctorfast.cs.data.model.SedeEspSubespId;
import com.doctorfast.cs.data.model.Subespecialidad;
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
public interface SedeEspSubespMapper {

    SedeEspSubespMapper INSTANCE = Mappers.getMapper(SedeEspSubespMapper.class);

    @Mappings({
        @Mapping(target = "sedeEspecialidad", ignore = true)
    })
    SedeEspSubespBean toBean(SedeEspSubesp modelo);

    @Mappings({
        @Mapping(target = "sedeEspecialidad", ignore = true)
    })
    SedeEspSubesp toModel(SedeEspSubespBean bean);

    List<SedeEspSubespBean> toBean(List<SedeEspSubesp> modelo);

    List<SedeEspSubesp> toModel(List<SedeEspSubespBean> bean);

    SedeEspSubespIdBean toBean(SedeEspSubespId modelo);

    SedeEspSubespId toModel(SedeEspSubespIdBean bean);

    @Mappings({
        @Mapping(target = "especialidad", ignore = true)
    })
    SubespecialidadBean toBean(Subespecialidad modelo);

    @Mappings({
        @Mapping(target = "especialidad", ignore = true)
    })
    Subespecialidad toModel(SubespecialidadBean bean);

}
