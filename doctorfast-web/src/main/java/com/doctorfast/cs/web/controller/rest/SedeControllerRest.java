/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.service.SedeService;
import com.doctorfast.cs.service.exception.ApplicationException;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.controller.dto.ErrorMessage;
import com.doctorfast.cs.web.controller.dto.ValidationResponse;
import com.doctorfast.cs.web.util.ConstantesUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBS GROUP
 */
@RestController
@RequestMapping(value = "/rest/sede")
public class SedeControllerRest {

    private static final Logger LOGGER = LogManager.getLogger(SedeControllerRest.class);

    @Autowired
    private SedeService sedeService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/obtenerSedes", method = RequestMethod.POST)
    public ResponseEntity obtenerSedes(@RequestBody SedeBean bean) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bean.setClinica(new ClinicaBean(user.getIdClinica()));
        List<SedeBean> lista = sedeService.findAll(bean);
        return ResponseEntity.ok(lista);
    }

    @RequestMapping(value = "/desactivar/{idSede}", method = RequestMethod.GET)
    public ResponseEntity desactivar(@PathVariable Integer idSede) {
        return ResponseEntity.ok(sedeService.setEstadoByIdSede(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idSede));
    }

    @RequestMapping(value = "/agregarEditar", method = RequestMethod.POST)
    public @ResponseBody
    ValidationResponse agregarEditar(@RequestBody SedeBean bean) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            bean.setNombre(bean.getNombre().trim());
            sedeService.save(bean, user.getIdClinica());
        } catch (ApplicationException ex) {
            LOGGER.error("Error registro", ex);
            List<ErrorMessage> errorMessages = new ArrayList<>();
            errorMessages.add(new ErrorMessage("", messageSource.getMessage(ex.getMessage(), new Object[]{}, LocaleContextHolder.getLocale())));
            return new ValidationResponse("error", errorMessages);
        }
        return new ValidationResponse("ok");
    }

    @RequestMapping(value = "/obtener/{idSede}", method = RequestMethod.GET)
    public ResponseEntity obtener(@PathVariable Integer idSede) {
        return ResponseEntity.ok(sedeService.findOne(idSede));
    }

    @RequestMapping(value = "/obtenerSedesDeClinica/{idClinica}", method = RequestMethod.GET)
    public ResponseEntity obtenerSedesDeClinica(@PathVariable Integer idClinica) {        
        SedeBean bean = new SedeBean(new ClinicaBean(idClinica), ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        List<SedeBean> lista = sedeService.findAll(bean);
        return ResponseEntity.ok(lista);
    }

}
