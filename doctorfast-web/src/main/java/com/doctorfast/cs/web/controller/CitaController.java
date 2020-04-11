/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller;

import com.doctorfast.cs.data.bean.CitaBean;
import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.bean.PromocionBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalIdBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadIdBean;
import com.doctorfast.cs.service.CitaService;
import com.doctorfast.cs.service.ClinicaService;
import com.doctorfast.cs.service.ProfesionalService;
import com.doctorfast.cs.service.PromocionService;
import com.doctorfast.cs.service.SedeEspProfesionalService;
import com.doctorfast.cs.service.SedeEspecialidadService;
import com.doctorfast.cs.service.SedeService;
import com.doctorfast.cs.service.util.FuncionesUtil;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.controller.dto.FiltroForm;
import com.doctorfast.cs.web.util.ConstantesUtil;
import com.doctorfast.cs.web.util.EmailUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
@RequestMapping(value = "/citas")
public class CitaController {

    @Autowired
    private ProfesionalService profesionalService;
    @Autowired
    private SedeEspecialidadService sedeEspecialidadService;
    @Autowired
    private ClinicaService clinicaService;
    @Autowired
    private SedeService sedeService;
    @Autowired
    private SedeEspProfesionalService sedeEspProfesionalService;
    @Autowired
    private CitaService citaService;
    @Autowired
    private PromocionService promocionService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClinicaBean clinica = clinicaService.findOne(user.getIdClinica());
        CitaBean cita = new CitaBean();
        cita.setTiempoCita(clinica.getTiempoCita());
        model.addAttribute("citaBean", cita);
        model.addAttribute("filtroForm", new FiltroForm());
        model.addAttribute("sedes", obtenerSedes(user.getIdClinica()));
        model.addAttribute("especialidades", obtenerEspecialidades(user.getIdClinica()));
        model.addAttribute("nombreClinica", user.getNombreClinica());
        return "cita/index";
    }

    @RequestMapping(value = "/calendario", method = RequestMethod.GET)
    public String calendario(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("citaBean", new CitaBean());
        model.addAttribute("filtroForm", new FiltroForm());
        model.addAttribute("clinicas", profesionalService.findClinicaByDocumentoIdentidad(user.getDocumentoIdentidad()));
        return "cita/calendario";
    }

    @RequestMapping(value = "/crearCitaDePromocion", method = RequestMethod.GET)
    public String crearCitaDePromocion(Model model, @RequestParam(value = "idSede") Integer idSede, @RequestParam(value = "idEspecialidad") Integer idEspecialidad,
            @RequestParam(value = "idPromocion") Integer idPromocion) {
        model.addAttribute("idSede", idSede);
        model.addAttribute("idEspecialidad", idEspecialidad);
        model.addAttribute("idPromocion", idPromocion);

        PromocionBean promocion = promocionService.findOne(idPromocion);
        if (promocion == null) {
            return "redirect:/index";
        }

        SedeEspecialidadBean bean = new SedeEspecialidadBean(
                new SedeEspecialidadIdBean(idEspecialidad, idSede),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        List<SedeEspecialidadBean> lista = sedeEspecialidadService.findAll(bean);

        if (FuncionesUtil.esNuloOVacio(lista)) {
            return "redirect:/index";
        }

        model.addAttribute("promocionInicio", FuncionesUtil.obtenerDateConFormato(promocion.getFechaInicio(), "dd/MM/yyyy"));
        model.addAttribute("promocionFin", FuncionesUtil.obtenerDateConFormato(promocion.getFechaFin(), "dd/MM/yyyy"));

        model.addAttribute("sede", lista.get(0).getSede().getNombre());
        model.addAttribute("especialidad", lista.get(0).getEspecialidad().getNombre());
        model.addAttribute("clinica", lista.get(0).getSede().getClinica().getNombreAbreviado());
        model.addAttribute("logo", lista.get(0).getSede().getClinica().getLogo());
        model.addAttribute("departamento", lista.get(0).getSede().getUbigeo().getNoDepartamento());
        model.addAttribute("provincia", lista.get(0).getSede().getUbigeo().getNoProvincia());
        model.addAttribute("distrito", lista.get(0).getSede().getUbigeo().getNoDistrito());

        model.addAttribute("profesionales", obtenerProfesionales(idSede, idEspecialidad));
        model.addAttribute("filtroForm", new FiltroForm());
        model.addAttribute("citaBean", new CitaBean());
        return "cita/crearCita";
    }

    @RequestMapping(value = "/crearCita", method = RequestMethod.GET)
    public String crearCita(Model model, @RequestParam(value = "idSede") Integer idSede, @RequestParam(value = "idEspecialidad") Integer idEspecialidad) {
        model.addAttribute("idSede", idSede);
        model.addAttribute("idEspecialidad", idEspecialidad);

        SedeEspecialidadBean bean = new SedeEspecialidadBean(
                new SedeEspecialidadIdBean(idEspecialidad, idSede),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        List<SedeEspecialidadBean> lista = sedeEspecialidadService.findAll(bean);

        if (FuncionesUtil.esNuloOVacio(lista)) {
            return "redirect:/consultarEspecialidad";
        }

        model.addAttribute("sede", lista.get(0).getSede().getNombre());
        model.addAttribute("especialidad", lista.get(0).getEspecialidad().getNombre());
        model.addAttribute("clinica", lista.get(0).getSede().getClinica().getNombreAbreviado());
        model.addAttribute("logo", lista.get(0).getSede().getClinica().getLogo());
        model.addAttribute("departamento", lista.get(0).getSede().getUbigeo().getNoDepartamento());
        model.addAttribute("provincia", lista.get(0).getSede().getUbigeo().getNoProvincia());
        model.addAttribute("distrito", lista.get(0).getSede().getUbigeo().getNoDistrito());

        model.addAttribute("profesionales", obtenerProfesionales(idSede, idEspecialidad));
        model.addAttribute("filtroForm", new FiltroForm());
        model.addAttribute("citaBean", new CitaBean());
        return "cita/crearCita";
    }

    @RequestMapping(value = "/consultar", method = RequestMethod.GET)
    public String consultar(Model model) {
        return "cita/consultar";
    }

    @RequestMapping(value = "/consultar", method = RequestMethod.POST)
    public String consultar(Model model, @RequestParam(value = "codigo") String codigo, @RequestParam(value = "correo") String correo, @RequestParam(value = "timezone") String timezone) {
        List<CitaBean> lista = citaService.findByCodigoAndPacientePersonaUsuarioCorreo(codigo, correo);
        if (FuncionesUtil.esNuloOVacio(lista)) {
            model.addAttribute("error", "true");
        } else {
            CitaBean cita = lista.get(0);
            enviarCorreo(correo, cita.getPaciente().getPersonaUsuario().getNombre(), cita, timezone);
            model.addAttribute("ok", "true");
        }
        return "cita/consultar";
    }

    public List<SedeBean> obtenerSedes(Integer idClinica) {
        SedeBean bean = new SedeBean();
        bean.setClinica(new ClinicaBean(idClinica));
        bean.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        return sedeService.findAll(bean);
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

    public List<ProfesionalBean> obtenerProfesionales(Integer idSede, Integer idEspecialidad) {

        SedeEspProfesionalIdBean sedeEspecialidadId = new SedeEspProfesionalIdBean(idSede, idEspecialidad);

        SedeEspecialidadBean sedeEspecialidad = new SedeEspecialidadBean(
                new SedeBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                new EspecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        ProfesionalBean profesional = new ProfesionalBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        SedeEspProfesionalBean bean = new SedeEspProfesionalBean(
                sedeEspecialidadId,
                sedeEspecialidad,
                profesional,
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        List<SedeEspProfesionalBean> lista = sedeEspProfesionalService.findAll(bean, "profesional.personaUsuario.apellidoPaterno");
        List<ProfesionalBean> resultado = new ArrayList<>();
        for (SedeEspProfesionalBean o : lista) {
            if (!resultado.contains(o.getProfesional())) {
                resultado.add(o.getProfesional());
            }
        }

        return resultado;
    }

    public String enviarCorreo(String correo, String nombre, CitaBean cita, String timezone) {
        String asunto = "Resumen de Cita";
        String mensaje = "Estimado/a " + nombre + ",<br>";
        mensaje += "La información de la cita consultada con código '" + cita.getCodigo() + "' es:<br><br>";
        mensaje += "<b>Nombre Paciente :</b> " + cita.getPaciente().getPersonaUsuario().getNombre() + "<br>";
        mensaje += "<b>Apellidos Paciente :</b> " + cita.getPaciente().getPersonaUsuario().getApellidoPaterno()+" "+cita.getPaciente().getPersonaUsuario().getApellidoMaterno() + "<br>";
        mensaje += "<b>Especialidad :</b> " + cita.getSedeEspProfesional().getSedeEspecialidad().getEspecialidad().getNombre() + "<br>";
        mensaje += "<b>Profesional :</b> " + cita.getSedeEspProfesional().getProfesional().getPersonaUsuario().getApellidoPaterno() + " "
                + cita.getSedeEspProfesional().getProfesional().getPersonaUsuario().getApellidoMaterno() + " "
                + cita.getSedeEspProfesional().getProfesional().getPersonaUsuario().getNombre() + "<br>";
        mensaje += "<b>Clinica :</b> " + cita.getSedeEspProfesional().getSedeEspecialidad().getSede().getClinica().getNombreAbreviado() + "<br>";
        mensaje += "<b>Sede :</b> " + cita.getSedeEspProfesional().getSedeEspecialidad().getSede().getNombre() + "<br>";
        mensaje += "<b>Dirección de Sede :</b> " + cita.getSedeEspProfesional().getSedeEspecialidad().getSede().getDireccion() + "<br>";
        mensaje += "<b>Fecha de Atención :</b> " + FuncionesUtil.obtenerFechaEnOtroTimezone(cita.getFechaAtencion(), timezone) + "<br>";
        mensaje += "<b>Estado de Cita :</b> " + obtenerEstadoCita(cita.getEstadoCita()) + "<br><br>";
        mensaje += "Atte. Equipo Doctorfast";
        EmailUtil emailUtil = new EmailUtil(messageSource.getMessage("parametro.enviarCorreo.host", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.port", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.cc", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.cita.alias", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.cita.usuario", new Object[]{}, LocaleContextHolder.getLocale()),
                messageSource.getMessage("parametro.enviarCorreo.cita.clave", new Object[]{}, LocaleContextHolder.getLocale()));
        return emailUtil.enviarPorSitio(correo, asunto, mensaje);
    }

    public String obtenerEstadoCita(String estado) {
        switch (estado) {
            case "1":
                return "Registrado";
            case "2":
                return "Confirmado";
            case "3":
                return "En Atención";
            case "4":
                return "Finalizado";
            default:
                return "";
        }
    }

}
