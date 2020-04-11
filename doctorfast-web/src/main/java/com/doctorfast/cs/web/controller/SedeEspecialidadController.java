/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspSubespBean;
import com.doctorfast.cs.data.bean.SedeEspSubespIdBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.data.bean.SubespecialidadBean;
import com.doctorfast.cs.service.EspecialidadService;
import com.doctorfast.cs.service.SedeService;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.util.ConstantesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author MBS GROUP
 */
@Controller
@RequestMapping(value = "/especialidades")
public class SedeEspecialidadController {

    @Autowired
    private SedeService sedeService;
    @Autowired
    private EspecialidadService especialidadService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SedeBean sedeBean = new SedeBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        sedeBean.setClinica(new ClinicaBean(user.getIdClinica()));
        model.addAttribute("sedes", sedeService.findAll(sedeBean));

        EspecialidadBean bean = new EspecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        model.addAttribute("especialidades", especialidadService.findAll(bean));

        model.addAttribute("sedeEspecialidadBean", new SedeEspecialidadBean(new SedeBean(), new EspecialidadBean()));
        model.addAttribute("sedeEspSubespBean", new SedeEspSubespBean(new SedeEspSubespIdBean(), new SedeEspecialidadBean(), new SubespecialidadBean()));
        return "sedeEspecialidad/index";
    }

}
