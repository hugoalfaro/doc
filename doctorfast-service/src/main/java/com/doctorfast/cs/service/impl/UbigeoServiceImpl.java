/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.UbigeoBean;
import com.doctorfast.cs.data.mapper.UbigeoMapper;
import com.doctorfast.cs.data.repository.UbigeoRepository;
import com.doctorfast.cs.service.UbigeoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBS GROUP
 */
@Service
public class UbigeoServiceImpl implements UbigeoService {

    @Autowired
    private UbigeoRepository ubigeoRepository;

    @Override
    public List<UbigeoBean> obtenerDepartamentos() {
        return UbigeoMapper.INSTANCE.toBean(ubigeoRepository.obtenerDepartamentos());
    }

    @Override
    public List<UbigeoBean> obtenerProvincias(Integer coDepartamento) {
        return UbigeoMapper.INSTANCE.toBean(ubigeoRepository.obtenerProvincias(coDepartamento));
    }

    @Override
    public List<UbigeoBean> obtenerDistritos(Integer coDepartamento, Integer coProvincia) {
        return UbigeoMapper.INSTANCE.toBean(ubigeoRepository.obtenerDistritos(coDepartamento, coProvincia));
    }

}
