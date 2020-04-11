/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.AsistenteBean;
import com.doctorfast.cs.service.ClinicaService;
import com.doctorfast.cs.service.AsistenteService;
import com.doctorfast.cs.service.exception.ApplicationException;
import com.doctorfast.cs.service.util.FuncionesUtil;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.controller.dto.ErrorMessage;
import com.doctorfast.cs.web.controller.dto.ValidationResponse;
import com.doctorfast.cs.web.util.ConstantesUtil;
import com.doctorfast.cs.web.util.EmailUtil;
import java.security.NoSuchAlgorithmException;
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
@RequestMapping(value = "/rest/asistente")
public class AsistenteControllerRest {

    private static final Logger LOGGER = LogManager.getLogger(AsistenteControllerRest.class);

    @Autowired
    private AsistenteService asistenteService;
    @Autowired
    private ClinicaService clinicaService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/obtenerAsistentes", method = RequestMethod.POST)
    public ResponseEntity obtenerAsistentes(@RequestBody AsistenteBean bean) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bean.setClinica(new ClinicaBean(user.getIdClinica()));
        List<AsistenteBean> lista = asistenteService.findAll(bean);
        return ResponseEntity.ok(lista);
    }

    @RequestMapping(value = "/desactivar/{idAsistente}", method = RequestMethod.GET)
    public ResponseEntity desactivar(@PathVariable Integer idAsistente) {
        return ResponseEntity.ok(asistenteService.setEstadoByIdAsistente(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idAsistente));
    }

    @RequestMapping(value = "/agregarEditar", method = RequestMethod.POST)
    public @ResponseBody
    ValidationResponse agregarEditar(@RequestBody AsistenteBean bean) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bean.setClinica(clinicaService.findOne(user.getIdClinica()));

        String clave = null;
        boolean esNuevo = bean.getIdAsistente() == null;

        try {
            if (esNuevo || !bean.getPersonaUsuario().getDocumentoIdentidad().equals(bean.getPersonaUsuario().getUsuario())) {
                clave = FuncionesUtil.obtenerCadenaAleatoria(8);
                bean.getPersonaUsuario().setClave(FuncionesUtil.md5(clave));
            }
            bean = asistenteService.save(bean);
        } catch (ApplicationException | NoSuchAlgorithmException ex) {
            LOGGER.error("Error registro", ex);
            List<ErrorMessage> errorMessages = new ArrayList<>();
            errorMessages.add(new ErrorMessage("", messageSource.getMessage(ex.getMessage(), new Object[]{}, LocaleContextHolder.getLocale())));
            return new ValidationResponse("error", errorMessages);
        }

        if (clave != null) {
            if (esNuevo) {
                enviarCorreo(bean.getPersonaUsuario().getCorreo(), bean.getPersonaUsuario().getNombre(), bean.getPersonaUsuario().getUsuario(), clave);
            } else {
                enviarCorreoModificacion(bean.getPersonaUsuario().getCorreo(), bean.getPersonaUsuario().getNombre(), bean.getPersonaUsuario().getUsuario(), clave);
            }
        }

        return new ValidationResponse("ok");
    }

    @RequestMapping(value = "/obtener/{idAsistente}", method = RequestMethod.GET)
    public ResponseEntity obtener(@PathVariable Integer idAsistente) {
        return ResponseEntity.ok(asistenteService.findOne(idAsistente));
    }

    public String enviarCorreo(String correo, String nombre, String usuario, String clave) {
        String asunto = "Creación de cuenta";
        String mensaje = "Estimado/a " + nombre + ",<br><br>";
        mensaje += "Se ha creado su cuenta en Doctorfast, los accesos son:<br>";
        mensaje += "Usuario: " + usuario + " / Clave: " + clave + "<br><br>";
        mensaje += "Atte. Equipo Doctorfast";
        EmailUtil emailUtil = new EmailUtil(messageSource.getMessage("parametro.enviarCorreo.host", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.port", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.cc", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.alias", new Object[]{}, LocaleContextHolder.getLocale()), 
                messageSource.getMessage("parametro.enviarCorreo.usuario", new Object[]{}, LocaleContextHolder.getLocale()), 
                messageSource.getMessage("parametro.enviarCorreo.clave", new Object[]{}, LocaleContextHolder.getLocale()));
        return emailUtil.enviarPorSitio(correo, asunto, mensaje);
    }

    public String enviarCorreoModificacion(String correo, String nombre, String usuario, String clave) {
        String asunto = "Modificación de cuenta";
        String mensaje = "Estimado/a " + nombre + ",<br><br>";
        mensaje += "Se ha modificado su cuenta en Doctorfast, los accesos son:<br>";
        mensaje += "Usuario: " + usuario + " / Clave: " + clave + "<br><br>";
        mensaje += "Atte. Equipo Doctorfast";
        EmailUtil emailUtil = new EmailUtil(messageSource.getMessage("parametro.enviarCorreo.host", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.port", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.cc", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.alias", new Object[]{}, LocaleContextHolder.getLocale()), 
                messageSource.getMessage("parametro.enviarCorreo.usuario", new Object[]{}, LocaleContextHolder.getLocale()), 
                messageSource.getMessage("parametro.enviarCorreo.clave", new Object[]{}, LocaleContextHolder.getLocale()));
        return emailUtil.enviarPorSitio(correo, asunto, mensaje);
    }

}
