/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.SubespecialidadBean;
import com.doctorfast.cs.service.SubespecialidadService;
import com.doctorfast.cs.web.util.ConstantesUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBS GROUP
 */
@RestController
@RequestMapping(value = "/rest/subespecialidad")
public class SubespecialidadControllerRest {

    @Autowired
    private SubespecialidadService subespecialidadService;

    @RequestMapping(value = "/obtenerSubespecialidades/{idEspecialidad}", method = RequestMethod.GET)
    public ResponseEntity obtenerSubespecialidades(@PathVariable Integer idEspecialidad) {
        SubespecialidadBean bean = new SubespecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        bean.setEspecialidad(new EspecialidadBean(idEspecialidad));

        List<SubespecialidadBean> lista = subespecialidadService.findAll(bean);
        return ResponseEntity.ok(lista);
    }

}
