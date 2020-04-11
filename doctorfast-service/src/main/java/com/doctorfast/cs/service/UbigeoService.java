/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service;

import com.doctorfast.cs.data.bean.UbigeoBean;
import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public interface UbigeoService {

    List<UbigeoBean> obtenerDepartamentos();

    List<UbigeoBean> obtenerProvincias(Integer coDepartamento);

    List<UbigeoBean> obtenerDistritos(Integer coDepartamento, Integer coProvincia);

}
