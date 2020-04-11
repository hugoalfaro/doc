/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.CitaBean;
import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalIdBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.service.CitaService;
import com.doctorfast.cs.service.ClinicaService;
import com.doctorfast.cs.service.PersonaUsuarioService;
import com.doctorfast.cs.service.ProfesionalService;
import com.doctorfast.cs.service.SedeEspProfesionalService;
import com.doctorfast.cs.service.exception.ApplicationException;
import com.doctorfast.cs.service.util.FuncionesUtil;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.controller.dto.ErrorMessage;
import com.doctorfast.cs.web.controller.dto.ValidationResponse;
import com.doctorfast.cs.web.util.ConstantesUtil;
import com.doctorfast.cs.web.util.EmailUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@RequestMapping(value = "/rest/profesional")
public class ProfesionalControllerRest {

    private static final Logger LOGGER = LogManager.getLogger(ProfesionalControllerRest.class);

    @Autowired
    private ProfesionalService profesionalService;
    @Autowired
    private SedeEspProfesionalService sedeEspProfesionalService;
    @Autowired
    private ClinicaService clinicaService;
    @Autowired
    private PersonaUsuarioService personaUsuarioService;
    @Autowired
    private CitaService citaService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/obtenerProfesionales", method = RequestMethod.POST)
    public ResponseEntity obtenerProfesionales(@RequestBody ProfesionalBean bean) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bean.setClinica(new ClinicaBean(user.getIdClinica()));
        List<ProfesionalBean> lista = profesionalService.findAll(bean);
        return ResponseEntity.ok(lista);
    }

    @RequestMapping(value = "/desactivar/{idProfesional}", method = RequestMethod.GET)
    public ResponseEntity desactivar(@PathVariable Integer idProfesional) {
        SedeEspecialidadBean sedeEspecialidad = new SedeEspecialidadBean(
                new SedeBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                new EspecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        
        SedeEspProfesionalIdBean sedeEspProfesionalId = new SedeEspProfesionalIdBean();
        sedeEspProfesionalId.setIdProfesional(idProfesional);

        SedeEspProfesionalBean sedeEspProfesional = new SedeEspProfesionalBean(
                sedeEspProfesionalId,
                sedeEspecialidad,
                null,
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        
        CitaBean bean = new CitaBean(
                sedeEspProfesional,
                new Date(),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        List<CitaBean> lista = citaService.findAll(bean);
        if(!lista.isEmpty()){
            return ResponseEntity.ok("error_citas");
        }
        
        return ResponseEntity.ok(profesionalService.setEstadoByIdProfesional(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idProfesional));
    }

    @RequestMapping(value = "/agregarEditar", method = RequestMethod.POST)
    public @ResponseBody
    ValidationResponse agregarEditar(@RequestBody ProfesionalBean bean) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SedeEspProfesionalBean> listaGuardar = new ArrayList<>();
        List<SedeEspProfesionalBean> listaTemporal;
        bean.setClinica(clinicaService.findOne(user.getIdClinica()));
        String clave = "";
        String claveMd5 = "";
        if (bean.getIdProfesional() == null) {
            listaTemporal = bean.getSedeEspProfesionals();

            clave = FuncionesUtil.obtenerCadenaAleatoria(8);
            try {
                claveMd5 = FuncionesUtil.md5(clave);
                bean.getPersonaUsuario().setClave(claveMd5);
                bean = profesionalService.save(bean);
            } catch (ApplicationException | NoSuchAlgorithmException ex) {
                LOGGER.error("Error registro", ex);
                List<ErrorMessage> errorMessages = new ArrayList<>();
                errorMessages.add(new ErrorMessage("", messageSource.getMessage(ex.getMessage(), new Object[]{}, LocaleContextHolder.getLocale())));
                return new ValidationResponse("error", errorMessages);
            }
            for (SedeEspProfesionalBean o : listaTemporal) {
                o.getId().setIdProfesional(bean.getIdProfesional());
                listaGuardar.add(o);
            }
            sedeEspProfesionalService.save(listaGuardar);
            enviarCorreo(bean.getPersonaUsuario().getCorreo(), bean.getPersonaUsuario().getNombre(), bean.getPersonaUsuario().getUsuario(), clave);
            actualizarOtrosUsuarios(bean.getPersonaUsuario().getIdPersonaUsuario(), bean.getPersonaUsuario().getUsuario(), clave, claveMd5);
        } else {
            boolean cambioUsuario = false;
            listaGuardar = bean.getSedeEspProfesionals();
            try {
                if (!bean.getPersonaUsuario().getDocumentoIdentidad().equals(bean.getPersonaUsuario().getUsuario())) {
                    cambioUsuario = true;
                    clave = FuncionesUtil.obtenerCadenaAleatoria(8);
                    claveMd5 = FuncionesUtil.md5(clave);
                    bean.getPersonaUsuario().setClave(claveMd5);
                }
                bean = profesionalService.save(bean);
            } catch (ApplicationException | NoSuchAlgorithmException ex) {
                LOGGER.error("Error registro", ex);
                List<ErrorMessage> errorMessages = new ArrayList<>();
                errorMessages.add(new ErrorMessage("", messageSource.getMessage(ex.getMessage(), new Object[]{}, LocaleContextHolder.getLocale())));
                return new ValidationResponse("error", errorMessages);
            }
            listaTemporal = sedeEspProfesionalService.findAll(construirObjeto(bean.getIdProfesional()), null);
            int i, j = 0;
            boolean flag;
            while (j < listaTemporal.size()) {
                SedeEspProfesionalBean o = listaTemporal.get(j);
                i = 0;
                flag = true;
                while (i < listaGuardar.size() && flag) {
                    if (Objects.equals(o.getId().getIdEspecialidad(), listaGuardar.get(i).getId().getIdEspecialidad())
                            && Objects.equals(o.getId().getIdSede(), listaGuardar.get(i).getId().getIdSede())) {
                        flag = false;
                        listaGuardar.remove(i);
                        listaTemporal.remove(j);
                    } else {
                        i++;
                    }
                }
                if (flag) {
                    listaTemporal.get(j).setEstado(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor());
                    j++;
                }
            }

            listaGuardar.addAll(listaTemporal);
            sedeEspProfesionalService.save(listaGuardar);

            if (cambioUsuario) {
                enviarCorreoModificacion(bean.getPersonaUsuario().getCorreo(), bean.getPersonaUsuario().getNombre(), bean.getPersonaUsuario().getUsuario(), clave);
                actualizarOtrosUsuarios(bean.getPersonaUsuario().getIdPersonaUsuario(), bean.getPersonaUsuario().getUsuario(), clave, claveMd5);
            }

        }

        return new ValidationResponse("ok");
    }

    @RequestMapping(value = "/obtener/{idProfesional}", method = RequestMethod.GET)
    public ResponseEntity obtener(@PathVariable Integer idProfesional) {
        return ResponseEntity.ok(profesionalService.findOne(idProfesional));
    }

    @RequestMapping(value = "/obtenerPorDocumentoIdentidad/{documentoIdentidad}", method = RequestMethod.GET)
    public ResponseEntity obtener(@PathVariable String documentoIdentidad) {
        List<ProfesionalBean> lista = profesionalService.findClinicaByDocumentoIdentidad(documentoIdentidad);
        if (lista.isEmpty()) {
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok(lista.get(0));
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

    public SedeEspProfesionalBean construirObjeto(Integer idProfesional) {

        SedeEspProfesionalIdBean id = new SedeEspProfesionalIdBean();
        id.setIdProfesional(idProfesional);

        SedeEspecialidadBean sedeEspecialidad = new SedeEspecialidadBean(
                new SedeBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                new EspecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        SedeEspProfesionalBean bean = new SedeEspProfesionalBean(id);
        bean.setSedeEspecialidad(sedeEspecialidad);
        bean.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        return bean;
    }

    public void actualizarOtrosUsuarios(Integer idPersonaUsuario, String usuario, String clave, String claveMd5) {
        List<PersonaUsuarioBean> lista = personaUsuarioService.findByUsuarioAndEstado(usuario, ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        List<String> correos = new ArrayList<>();
        for (PersonaUsuarioBean bean : lista) {
            if (!idPersonaUsuario.equals(bean.getIdPersonaUsuario())) {
                bean.setClave(claveMd5);
                personaUsuarioService.save(bean);
                if (!correos.contains(bean.getCorreo())) {
                    enviarCorreoModificacion(bean.getCorreo(), bean.getNombre(), bean.getUsuario(), clave);
                    correos.add(bean.getCorreo());
                }
            }
        }
    }

}
