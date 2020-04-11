/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.model.Especialidad;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author MBS GROUP
 */
@Mapper
public interface EspecialidadMapper {

    EspecialidadMapper INSTANCE = Mappers.getMapper(EspecialidadMapper.class);

    EspecialidadBean toBean(Especialidad modelo);

    Especialidad toModel(EspecialidadBean bean);

    List<EspecialidadBean> toBean(List<Especialidad> modelo);

    List<Especialidad> toModel(List<EspecialidadBean> bean);

}
