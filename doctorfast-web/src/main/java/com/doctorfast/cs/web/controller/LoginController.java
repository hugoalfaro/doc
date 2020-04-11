/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller;

import com.doctorfast.cs.data.bean.AdministradorBean;
import com.doctorfast.cs.data.bean.AsistenteBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.service.AdministradorService;
import com.doctorfast.cs.service.AsistenteService;
import com.doctorfast.cs.service.PersonaUsuarioService;
import com.doctorfast.cs.service.ProfesionalService;
import com.doctorfast.cs.service.util.FuncionesUtil;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.util.ConstantesUtil;
import com.doctorfast.cs.web.util.EmailUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MBS GROUP
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private PersonaUsuarioService personaUsuarioService;
    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private ProfesionalService profesionalService;
    @Autowired
    private AsistenteService asistenteService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "administrador")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/administrador?logout";
    }

    @RequestMapping(value = "inicio")
    public String inicio() {
        return "inicio";
    }

    @RequestMapping(value = "notFound.do")
    public String notFound() {
        return "404";
    }

    @RequestMapping(value = "forbidden")
    public String forbidden() {
        return "403";
    }

    @RequestMapping(value = "seguridad")
    public String seguridad(Model model) {
        model.addAttribute("personaUsuarioBean", new PersonaUsuarioBean());
        return "seguridad";
    }

    @RequestMapping(value = "/seguridad", method = RequestMethod.POST)
    public String seguridad(Model model, @ModelAttribute("personaUsuarioBean") PersonaUsuarioBean bean) {
        String rpta = "false";
        if (bean.getClave().equals(bean.getRepetirClave())) {
            CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            try {
                personaUsuarioService.setClaveByIdPersonaUsuario(FuncionesUtil.md5(bean.getClave()), user.getIdPersonaUsuario());
                rpta = "true";
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("Error registro", ex);
            }
        }
        model.addAttribute("personaUsuarioBean", new PersonaUsuarioBean());
        model.addAttribute("ok", rpta);
        return "seguridad";
    }

    @RequestMapping(value = "recuperarContrasena", method = RequestMethod.GET)
    public String recuperarContrasena(Model model, @RequestParam(value = "usuario") String usuario) {
        usuario = usuario.trim();
        List<PersonaUsuarioBean> lista = personaUsuarioService.findByUsuarioAndEstado(usuario, ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        if (!lista.isEmpty()) {
            try {
                boolean flag = false;
                String clave = FuncionesUtil.obtenerCadenaAleatoria(8);
                String claveMd5 = FuncionesUtil.md5(clave);
                List<String> correos = new ArrayList<>();
                for (PersonaUsuarioBean bean : lista) {
                    if (buscarUsuario(bean)) {
                        bean.setClave(claveMd5);
                        personaUsuarioService.save(bean);
                        if (!correos.contains(bean.getCorreo())) {
                            enviarCorreo(bean.getCorreo(), bean.getNombre(), bean.getUsuario(), clave);
                            correos.add(bean.getCorreo());
                        }
                        flag = true;
                    }
                }
                if (flag) {
                    model.addAttribute("recuperacion_ok", "true");
                    return "login";
                }
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("Error registro", ex);
                model.addAttribute("recuperacion_error", "true");
                return "login";
            }
        }
        model.addAttribute("recuperacion_error", "true");
        return "login";
    }

    @RequestMapping(value = "recuperarUsuario", method = RequestMethod.GET)
    public String recuperarUsuario(Model model, @RequestParam(value = "correo") String correo) {
        correo = correo.trim();
        List<PersonaUsuarioBean> lista = personaUsuarioService.findByCorreo(correo);
        if (!lista.isEmpty()) {
            try {
                boolean flag = false;
                String clave = FuncionesUtil.obtenerCadenaAleatoria(8);
                String claveMd5 = FuncionesUtil.md5(clave);
                List<String> correos = new ArrayList<>();
                for (PersonaUsuarioBean bean : lista) {
                    if (buscarUsuario(bean)) {
                        bean.setClave(claveMd5);
                        personaUsuarioService.save(bean);
                        if (!correos.contains(bean.getCorreo())) {
                            enviarCorreo(bean.getCorreo(), bean.getNombre(), bean.getUsuario(), clave);
                            correos.add(bean.getCorreo());
                        }
                        flag = true;
                    }
                }
                if (flag) {
                    model.addAttribute("recuperacion_ok", "true");
                    return "login";
                }
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("Error registro", ex);
                model.addAttribute("recuperacion_error", "true");
                return "login";
            }
        }
        model.addAttribute("recuperacion_error", "true");
        return "login";
    }

    public boolean buscarUsuario(PersonaUsuarioBean bean) {
        if (bean.getPerfil().getNoPerfil().equalsIgnoreCase("administrador")) {
            AdministradorBean administrador = administradorService.findByPersonaUsuarioIdPersonaUsuario(bean.getIdPersonaUsuario());
            if (administrador != null && !administrador.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor())) {
                return true;
            }

        }
        if (bean.getPerfil().getNoPerfil().equalsIgnoreCase("asistente")) {
            AsistenteBean asistente = asistenteService.findByPersonaUsuarioIdPersonaUsuario(bean.getIdPersonaUsuario());
            if (asistente != null && !asistente.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor())) {
                return true;
            }
        }
        if (bean.getPerfil().getNoPerfil().equalsIgnoreCase("profesional")) {
            ProfesionalBean profesional = profesionalService.findByPersonaUsuarioIdPersonaUsuario(bean.getIdPersonaUsuario());
            if (profesional != null && !profesional.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor())) {
                return true;
            }
        }
        return false;
    }

    public String enviarCorreo(String correo, String nombre, String usuario, String clave) {
        String asunto = "Recuperación de contraseña";
        String mensaje = "Estimado/a " + nombre + ",<br><br>";
        mensaje += "Se ha atendido su solicitud de recuperar el acceso a su cuenta en Doctorfast, los nuevos accesos son:<br>";
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
