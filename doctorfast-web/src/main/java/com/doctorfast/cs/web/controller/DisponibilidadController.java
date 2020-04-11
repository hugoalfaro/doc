/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.DisponibilidadBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.service.ProfesionalService;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.controller.dto.FiltroForm;
import com.doctorfast.cs.web.util.ConstantesUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MBS GROUP
 */
@Controller
@RequestMapping(value = "/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private ProfesionalService profesionalService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "idProfesional", required = false) String idProfesional) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("filtroForm", new FiltroForm());
        model.addAttribute("disponibilidadBean", new DisponibilidadBean());
        model.addAttribute("profesionales", obtenerProfesionales(user.getIdClinica()));
        model.addAttribute("idProfesional", idProfesional);
        return "disponibilidad/index";
    }

    public List<ProfesionalBean> obtenerProfesionales(Integer idClinica) {
        ProfesionalBean bean = new ProfesionalBean();
        bean.setClinica(new ClinicaBean(idClinica));
        bean.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        return profesionalService.findAll(bean);
    }

}
