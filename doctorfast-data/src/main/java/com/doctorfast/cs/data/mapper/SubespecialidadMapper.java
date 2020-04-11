/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.SubespecialidadBean;
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
public interface SubespecialidadMapper {

    SubespecialidadMapper INSTANCE = Mappers.getMapper(SubespecialidadMapper.class);

    @Mappings({
        @Mapping(target = "especialidad", ignore = true)
    })
    SubespecialidadBean toBean(Subespecialidad modelo);

    @Mappings({
        @Mapping(target = "especialidad", ignore = true)
    })
    Subespecialidad toModel(SubespecialidadBean bean);

    List<SubespecialidadBean> toBean(List<Subespecialidad> modelo);

    List<Subespecialidad> toModel(List<SubespecialidadBean> bean);

}
