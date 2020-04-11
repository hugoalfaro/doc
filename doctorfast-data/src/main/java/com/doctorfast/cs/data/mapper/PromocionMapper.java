/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.PromocionBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadIdBean;
import com.doctorfast.cs.data.model.Promocion;
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
public interface PromocionMapper {

    PromocionMapper INSTANCE = Mappers.getMapper(PromocionMapper.class);

    PromocionBean toBean(Promocion modelo);

    @Mappings({
        @Mapping(target = "sedeEspecialidad", ignore = true)
    })
    Promocion toModel(PromocionBean bean);

    List<PromocionBean> toBean(List<Promocion> modelo);

    List<Promocion> toModel(List<PromocionBean> bean);

    @Mappings({
        @Mapping(target = "sede", ignore = true),
        @Mapping(target = "especialidad", ignore = true)
    })
    SedeEspecialidadBean toBean(SedeEspecialidad modelo);

    SedeEspecialidadIdBean toBean(SedeEspecialidadId modelo);
    
}
