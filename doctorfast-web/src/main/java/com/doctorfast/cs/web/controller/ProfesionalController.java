/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.service.ClinicaService;
import com.doctorfast.cs.service.SedeEspecialidadService;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.util.ConstantesUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
@RequestMapping(value = "/profesionales")
public class ProfesionalController {

    @Autowired
    private SedeEspecialidadService sedeEspecialidadService;
    @Autowired
    private ClinicaService clinicaService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClinicaBean clinica = clinicaService.findOne(user.getIdClinica());
        ProfesionalBean profesional = new ProfesionalBean(new SedeEspProfesionalBean());
        profesional.setTiempoCita(clinica.getTiempoCita());
        profesional.setColorDisponibilidad(messageSource.getMessage("parametro.color.disponibilidad", new Object[]{}, LocaleContextHolder.getLocale()));
        profesional.setColorCita(messageSource.getMessage("parametro.color.cita", new Object[]{}, LocaleContextHolder.getLocale()));
        model.addAttribute("profesionalBean", profesional);
        model.addAttribute("especialidades", obtenerEspecialidades(user.getIdClinica()));
        return "profesional/index";
    }

    public List<EspecialidadBean> obtenerEspecialidades(Integer idClinica) {
        SedeBean sede = new SedeBean(
                new ClinicaBean(idClinica),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        SedeEspecialidadBean sedeEspecialidad = new SedeEspecialidadBean(
                sede,
                new EspecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        List<SedeEspecialidadBean> lista = sedeEspecialidadService.findAll(sedeEspecialidad);
        List<EspecialidadBean> especialidades = new ArrayList<>();
        for (SedeEspecialidadBean o : lista) {
            if (!especialidades.contains(o.getEspecialidad())) {
                especialidades.add(o.getEspecialidad());
            }
        }
        return especialidades;
    }

}
