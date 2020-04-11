/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.PromocionBean;
import com.doctorfast.cs.data.mapper.PromocionMapper;
import com.doctorfast.cs.data.repository.PromocionRepository;
import com.doctorfast.cs.service.PromocionService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBS GROUP
 */
@Service
public class PromocionServiceImpl implements PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Override
    public List<PromocionBean> findByEstadoAndFechaFinAfterOrderByOrdenAsc(String estado, Date fecha) {
        return PromocionMapper.INSTANCE.toBean(promocionRepository.findByEstadoAndFechaFinAfterOrderByOrdenAsc(estado, fecha));
    }

    @Override
    public PromocionBean findOne(Integer id) {
        return PromocionMapper.INSTANCE.toBean(promocionRepository.findOne(id));
    }
    
}
