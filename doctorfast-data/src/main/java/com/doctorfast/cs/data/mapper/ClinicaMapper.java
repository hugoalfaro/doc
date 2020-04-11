/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.model.Clinica;
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
public interface ClinicaMapper {

    ClinicaMapper INSTANCE = Mappers.getMapper(ClinicaMapper.class);

    @Mappings({
        @Mapping(target = "profesionals", ignore = true)
    })
    ClinicaBean toBean(Clinica modelo);

    @Mappings({
        @Mapping(target = "profesionals", ignore = true)
    })
    Clinica toModel(ClinicaBean bean);

    List<ClinicaBean> toBean(List<Clinica> modelo);

    List<Clinica> toModel(List<ClinicaBean> bean);

}
