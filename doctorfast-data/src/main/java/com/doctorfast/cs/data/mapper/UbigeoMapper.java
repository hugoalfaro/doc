/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.mapper;

import com.doctorfast.cs.data.bean.UbigeoBean;
import com.doctorfast.cs.data.bean.UbigeoIdBean;
import com.doctorfast.cs.data.model.Ubigeo;
import com.doctorfast.cs.data.model.UbigeoId;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author MBS GROUP
 */
@Mapper
public interface UbigeoMapper {

    UbigeoMapper INSTANCE = Mappers.getMapper(UbigeoMapper.class);

    UbigeoBean toBean(Ubigeo modelo);

    Ubigeo toModel(UbigeoBean bean);

    List<UbigeoBean> toBean(List<Ubigeo> modelo);

    List<Ubigeo> toModel(List<UbigeoBean> bean);

    UbigeoIdBean toUbigeoIdBean(UbigeoId modelo);

    UbigeoId toUbigeoIdModel(UbigeoIdBean bean);

}
