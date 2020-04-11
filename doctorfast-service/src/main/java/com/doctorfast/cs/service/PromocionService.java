/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.PromocionBean;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface PromocionService {

    List<PromocionBean> findByEstadoAndFechaFinAfterOrderByOrdenAsc(String estado, Date fecha);

    PromocionBean findOne(Integer id);

}
