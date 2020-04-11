/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.UbigeoBean;
import com.doctorfast.cs.data.bean.UbigeoIdBean;
import com.doctorfast.cs.data.model.Sede;
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
public interface SedeMapper {

    SedeMapper INSTANCE = Mappers.getMapper(SedeMapper.class);

    @Mappings({
        @Mapping(target = "clinica", ignore = true)
    })
    SedeBean toBean(Sede modelo);

    @Mappings({
        @Mapping(target = "clinica", ignore = true)
    })
    Sede toModel(SedeBean bean);

    List<SedeBean> toBean(List<Sede> modelo);

    List<Sede> toModel(List<SedeBean> bean);

    UbigeoBean toUbigeoBean(Ubigeo modelo);

    Ubigeo toUbigeoModel(UbigeoBean bean);

    UbigeoIdBean toUbigeoIdBean(UbigeoId modelo);

    UbigeoId toUbigeoIdModel(UbigeoIdBean bean);

}
