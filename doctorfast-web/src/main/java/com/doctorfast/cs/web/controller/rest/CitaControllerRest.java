/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.CitaBean;
import com.doctorfast.cs.data.bean.DisponibilidadEventoBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.PersonaUsuarioBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalIdBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.service.CitaService;
import com.doctorfast.cs.service.DisponibilidadEventoService;
import com.doctorfast.cs.service.ProfesionalService;
import com.doctorfast.cs.service.exception.ApplicationException;
import com.doctorfast.cs.service.util.FuncionesUtil;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.controller.dto.CalendarioDto;
import com.doctorfast.cs.web.controller.dto.ErrorMessage;
import com.doctorfast.cs.web.controller.dto.FiltroForm;
import com.doctorfast.cs.web.controller.dto.ValidationResponse;
import com.doctorfast.cs.web.util.ConstantesUtil;
import com.doctorfast.cs.web.util.EmailUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
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
@RequestMapping(value = "/rest/cita")
public class CitaControllerRest {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(CitaControllerRest.class);

    @Autowired
    private CitaService citaService;
    @Autowired
    private DisponibilidadEventoService disponibilidadEventoService;
    @Autowired
    private ProfesionalService profesionalService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/obtenerCitas", method = RequestMethod.POST)
    public ResponseEntity obtenerCitas(@RequestBody FiltroForm filtro) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SedeEspecialidadBean sedeEspecialidad = new SedeEspecialidadBean(
                new SedeBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                new EspecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        ProfesionalBean profesional = new ProfesionalBean(
                new ClinicaBean(user.getIdClinica()),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        profesional.setCop(filtro.getCop());

        if (filtro.getIdClinica() != null && filtro.getIdClinica() == 0) {
            if (user.getIdsProfesionales().size() < 2) {
                filtro.setIdProfesional(user.getIdsProfesionales().get(0));
            } else {
                profesional.setIdProfesionals(user.getIdsProfesionales());
            }
        }

        SedeEspProfesionalBean sedeEspProfesional = new SedeEspProfesionalBean(
                new SedeEspProfesionalIdBean(filtro.getIdSede(), filtro.getIdEspecialidad(), filtro.getIdProfesional()),
                sedeEspecialidad,
                profesional,
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        CitaBean bean = new CitaBean(
                sedeEspProfesional,
                filtro.getEstado(),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        bean.setFechaAtencion(filtro.getFechaDesde());
        bean.setFechaAtencionFin(filtro.getFechaHasta());

        List<CitaBean> lista = citaService.findAll(bean);

        List<CalendarioDto> resultado = new ArrayList<>();
        for (CitaBean o : lista) {
            resultado.add(obtenerCalendarioBean(messageSource.getMessage("parametro.color.texto", new Object[]{}, LocaleContextHolder.getLocale()), "C", o));
        }
        return ResponseEntity.ok(resultado);
    }

    @RequestMapping(value = "/desactivar/{idCita}", method = RequestMethod.GET)
    public ResponseEntity desactivar(@PathVariable Integer idCita) {
        return ResponseEntity.ok(citaService.setEstadoByIdCita(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idCita));
    }

    @RequestMapping(value = "/agregarEditar", method = RequestMethod.POST)
    public @ResponseBody
    ValidationResponse agregarEditar(@RequestBody CitaBean bean) {
        try {
            validarDisponibilidad(bean);
            validarCita(bean);
            citaService.save(bean);
        } catch (ApplicationException ex) {
            LOGGER.error("Error registro", ex);
            List<ErrorMessage> errorMessages = new ArrayList<>();
            errorMessages.add(new ErrorMessage("", ex.getMessage()));
            return new ValidationResponse("error", errorMessages);
        }
        return new ValidationResponse("ok");
    }

    @RequestMapping(value = "/agregarPublico", method = RequestMethod.POST)
    public @ResponseBody
    ValidationResponse agregarPublico(@RequestBody CitaBean bean) {
        try {
            String timezone = bean.getTimezone();
            ProfesionalBean profesional = profesionalService.findOne(bean.getSedeEspProfesional().getId().getIdProfesional());
            bean.setTiempoCita(profesional.getTiempoCita());
            validarDisponibilidad(bean);
            validarCita(bean);
            bean.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
            bean.setEstadoCita(ConstantesUtil.ESTADO_CITA.REGISTRADO.getValor());
            bean = citaService.save(bean);

            CitaBean cita = citaService.findOne(bean.getIdCita());
            enviarCorreo(bean.getPaciente().getPersonaUsuario().getCorreo(), bean.getPaciente().getPersonaUsuario().getNombre(), cita, timezone);
            
            return new ValidationResponse(bean.getCodigo());
        } catch (ApplicationException ex) {
            LOGGER.error("Error registro", ex);
            List<ErrorMessage> errorMessages = new ArrayList<>();
            errorMessages.add(new ErrorMessage("", ex.getMessage()));
            return new ValidationResponse("error", errorMessages);
        }
    }

    @RequestMapping(value = "/editar", method = RequestMethod.POST)
    public @ResponseBody
    ValidationResponse editar(@RequestBody CitaBean bean) {
        citaService.setEstadoCitaAndComentarioByIdCita(bean.getEstadoCita(), bean.getComentario(), bean.getIdCita());
        return new ValidationResponse("ok");
    }

    @RequestMapping(value = "/obtenerCitasPublico", method = RequestMethod.POST)
    public ResponseEntity obtenerCitasPublico(@RequestBody FiltroForm filtro) {

        SedeEspecialidadBean sedeEspecialidad = new SedeEspecialidadBean(
                new SedeBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                new EspecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        ProfesionalBean profesional = new ProfesionalBean(
                new ClinicaBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        SedeEspProfesionalBean sedeEspProfesional = new SedeEspProfesionalBean(
                new SedeEspProfesionalIdBean(filtro.getIdSede(), filtro.getIdEspecialidad(), filtro.getIdProfesional()),
                sedeEspecialidad,
                profesional,
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        CitaBean bean = new CitaBean(
                sedeEspProfesional,
                "",
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        bean.setFechaAtencion(filtro.getFechaDesde());
        bean.setFechaAtencionFin(filtro.getFechaHasta());

        List<CitaBean> lista = citaService.findAll(bean);

        List<CalendarioDto> resultado = new ArrayList<>();
        for (CitaBean o : lista) {
            resultado.add(obtenerCalendarioBean(messageSource.getMessage("parametro.color.texto", new Object[]{}, LocaleContextHolder.getLocale()), "C", o));
        }
        return ResponseEntity.ok(resultado);
    }

    public CalendarioDto obtenerCalendarioBean(String colorTexto, String tipo, CitaBean cita) {
        CalendarioDto calendario = new CalendarioDto();
        PersonaUsuarioBean profesional = cita.getSedeEspProfesional().getProfesional().getPersonaUsuario();
        PersonaUsuarioBean paciente = cita.getPaciente().getPersonaUsuario();
        calendario.setTitle(profesional.getApellidoPaterno() + " " + profesional.getApellidoMaterno() + " " + profesional.getNombre() + " - "
                + paciente.getApellidoPaterno() + " " + paciente.getApellidoMaterno() + " " + paciente.getNombre());
        calendario.setStart(cita.getFechaAtencion());
        calendario.setEnd(FuncionesUtil.sumarHorasMinutosFecha(cita.getFechaAtencion(), 0, (FuncionesUtil.esNuloOVacio(cita.getTiempoCita())) ? 30 : cita.getTiempoCita()));
        calendario.setColor(cita.getSedeEspProfesional().getProfesional().getColorCita());
        calendario.setTextColor(colorTexto);
        calendario.setTipo(tipo);
        calendario.setCita(cita);
        return calendario;
    }

    public void validarDisponibilidad(CitaBean bean) throws ApplicationException {
        boolean flag = true;
        int i = 0;
        bean.setFechaAtencionFin(FuncionesUtil.sumarHorasMinutosFecha(bean.getFechaAtencion(), 0, bean.getTiempoCita()));
        List<DisponibilidadEventoBean> eventos = disponibilidadEventoService.findByIdSedeIdEspecialidadIdProfesionalAndFecha(
                bean.getSedeEspProfesional().getId().getIdSede(),
                bean.getSedeEspProfesional().getId().getIdEspecialidad(),
                bean.getSedeEspProfesional().getId().getIdProfesional(),
                FuncionesUtil.removerTiempo(bean.getFechaAtencion()),
                FuncionesUtil.removerTiempo(bean.getFechaAtencionFin()));

        while (flag && i < eventos.size()) {
            if (FuncionesUtil.estaDentro(eventos.get(i).getFechaInicio(), eventos.get(i).getFechaFin(), bean.getFechaAtencion(), bean.getFechaAtencionFin())) {
                flag = false;
            } else {
                i++;
            }
        }
        if (flag) {
            throw new ApplicationException("El profesional no tiene disponibilidad en el horario seleccionado.");
        }
    }

    public void validarCita(CitaBean bean) throws ApplicationException {
        String resultado = "";
        List<CitaBean> eventos;

        bean.setFechaAtencionFin(FuncionesUtil.sumarHorasMinutosFecha(bean.getFechaAtencion(), 0, bean.getTiempoCita()));
        if (bean.getIdCita() == null) {
            eventos = citaService.findByIdProfesionalAndFecha(
                    bean.getSedeEspProfesional().getId().getIdProfesional(),
                    FuncionesUtil.removerTiempo(bean.getFechaAtencion()),
                    FuncionesUtil.removerTiempo(bean.getFechaAtencionFin()));
        } else {
            eventos = citaService.findByIdProfesionalAndFechaAndIdCitaNot(
                    bean.getSedeEspProfesional().getId().getIdProfesional(),
                    FuncionesUtil.removerTiempo(bean.getFechaAtencion()),
                    FuncionesUtil.removerTiempo(bean.getFechaAtencionFin()),
                    bean.getIdCita());
        }

        for (CitaBean evento : eventos) {
            evento.setFechaAtencionFin(FuncionesUtil.sumarHorasMinutosFecha(evento.getFechaAtencion(), 0, evento.getTiempoCita()));
            if (FuncionesUtil.existeCruce(evento.getFechaAtencion(), evento.getFechaAtencionFin(), bean.getFechaAtencion(), bean.getFechaAtencionFin())) {
                resultado += FuncionesUtil.mensajeCruce(evento.getFechaAtencion(), evento.getFechaAtencionFin()) + "<br>";
            }
        }
        if (!resultado.equals("")) {
            throw new ApplicationException("El profesional ya tiene una cita en el horario seleccionado.");
        }
    }

    public String enviarCorreo(String correo, String nombre, CitaBean cita, String timezone) {
        String asunto = "Creación de Cita";
        String mensaje = "Estimado/a " + nombre + ",<br>";
        mensaje += "Se ha creado la cita con código '" + cita.getCodigo() + "':<br><br>";
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
