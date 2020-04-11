/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.service.ClinicaService;
import com.doctorfast.cs.service.validator.ClinicaValidator;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.util.ArchivosUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author MBS GROUP
 */
@Controller
@RequestMapping(value = "/clinica")
public class ClinicaController {

    @Autowired
    private ClinicaService clinicaService;
    @Autowired
    private ClinicaValidator clinicaValidator;
    @Autowired
    private MessageSource messageSource;

    @InitBinder("clinicaBean")
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.setValidator(clinicaValidator);
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String inicio(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClinicaBean bean = clinicaService.findOne(user.getIdClinica());
        model.addAttribute("clinicaBean", bean);
        return "clinica/index";
    }

    @RequestMapping(value = "/actualizarDatos.do", method = RequestMethod.POST)
    public String actualizarDatos(Model model, @ModelAttribute("clinicaBean") @Validated ClinicaBean bean,
            BindingResult result) {

        if (result.hasErrors()) {
            return "administrador/index";
        }

        if (!bean.getArchivo().isEmpty()) {
            String nombreArchivo = ArchivosUtil.generarNombreArchivo(bean.getArchivo(), "logo_" + bean.getIdClinica());
            String uploadPath = messageSource.getMessage("parametro.amazonaws.bucket", new Object[]{}, LocaleContextHolder.getLocale());
            ArchivosUtil.subirArchivoAWS(bean.getArchivo(), uploadPath, nombreArchivo);
            bean.setLogo(nombreArchivo);

        }

        bean = clinicaService.save(bean);
        model.addAttribute("clinicaBean", bean);
        return "clinica/index";
    }

}
