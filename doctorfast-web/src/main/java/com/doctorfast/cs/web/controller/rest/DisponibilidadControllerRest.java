/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.CitaBean;
import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.DisponibilidadBean;
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
import com.doctorfast.cs.service.DisponibilidadService;
import com.doctorfast.cs.service.exception.ApplicationException;
import com.doctorfast.cs.service.util.FuncionesUtil;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.controller.dto.CalendarioDto;
import com.doctorfast.cs.web.controller.dto.ErrorMessage;
import com.doctorfast.cs.web.controller.dto.FiltroForm;
import com.doctorfast.cs.web.controller.dto.ValidationResponse;
import com.doctorfast.cs.web.util.ConstantesUtil;
import com.doctorfast.cs.web.util.RepetirEventoUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping(value = "/rest/disponibilidad")
public class DisponibilidadControllerRest {

    private static final Logger LOGGER = LogManager.getLogger(DisponibilidadControllerRest.class);

    @Autowired
    private DisponibilidadService disponibilidadService;
    @Autowired
    private DisponibilidadEventoService disponibilidadEventoService;
    @Autowired
    private CitaService citaService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/obtenerDisponibilidades", method = RequestMethod.POST)
    public ResponseEntity obtenerDisponibilidades(@RequestBody FiltroForm filtro) {
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

        DisponibilidadBean disponibilidad = new DisponibilidadBean(
                sedeEspProfesional,
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        DisponibilidadEventoBean bean = new DisponibilidadEventoBean(
                disponibilidad,
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        bean.setFechaInicio(filtro.getFechaDesde());
        bean.setFechaFin(filtro.getFechaHasta());

        List<DisponibilidadEventoBean> lista = disponibilidadEventoService.findAll(bean);

        List<CalendarioDto> resultado = new ArrayList<>();
        for (DisponibilidadEventoBean evento : lista) {
            resultado.add(obtenerCalendarioBean(messageSource.getMessage("parametro.color.texto", new Object[]{}, LocaleContextHolder.getLocale()), filtro.getRendering(), "D", evento.getDisponibilidad(), evento));
        }
        return ResponseEntity.ok(resultado);
    }

    @RequestMapping(value = "/desactivarTodos/{idDisponibilidad}", method = RequestMethod.GET)
    public ResponseEntity desactivarTodos(@PathVariable Integer idDisponibilidad) {
        try {
            validarCita(disponibilidadEventoService.findAll(new DisponibilidadEventoBean(new DisponibilidadBean(idDisponibilidad), ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor())));
        } catch (ApplicationException ex) {
            return ResponseEntity.ok("error_citas");
        }
        return ResponseEntity.ok(disponibilidadService.setEstadoByIdDisponibilidad(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idDisponibilidad));
    }

    @RequestMapping(value = "/desactivarDisponibilidadEvento/{idDisponibilidad}/{idDisponibilidadEvento}", method = RequestMethod.GET)
    public ResponseEntity desactivarDisponibilidadEvento(@PathVariable Integer idDisponibilidad, @PathVariable Integer idDisponibilidadEvento) {
        try {
            validarCita(disponibilidadEventoService.findOne(idDisponibilidadEvento));
        } catch (ApplicationException ex) {
            return ResponseEntity.ok("error_citas");
        }
        disponibilidadEventoService.setEstadoByIdDisponibilidadEvento(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idDisponibilidadEvento);
        verificarYOActualizarDisponibilidad(idDisponibilidad);
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/desactivarSiguientes/{idDisponibilidad}/{fechaInicio}", method = RequestMethod.GET)
    public ResponseEntity desactivarSiguientes(@PathVariable Integer idDisponibilidad, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaInicio) {
        try {
            validarCita(disponibilidadEventoService.findAll(new DisponibilidadEventoBean(fechaInicio, new DisponibilidadBean(idDisponibilidad), ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor())));
        } catch (ApplicationException ex) {
            return ResponseEntity.ok("error_citas");
        }
        disponibilidadEventoService.setEstadoByIdDisponibilidadAndFechaInicioAfter(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idDisponibilidad, fechaInicio);
        verificarYOActualizarDisponibilidad(idDisponibilidad);
        return ResponseEntity.ok("");
    }

    public void verificarYOActualizarDisponibilidad(Integer idDisponibilidad) {
        Long cantidadEventos = disponibilidadEventoService.contarPorIdDisponibilidadEstado(idDisponibilidad, ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        if (cantidadEventos == 0) {
            disponibilidadService.setEstadoByIdDisponibilidad(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idDisponibilidad);
        }
    }

    @RequestMapping(value = "/agregarEditar", method = RequestMethod.POST)
    public @ResponseBody
    ValidationResponse agregarEditar(@RequestBody DisponibilidadBean bean) {
        List<DisponibilidadEventoBean> listaGuardar;
        boolean flagNuevo = bean.getIdDisponibilidad() == null;
        boolean flagRepite = bean.getRepeticionTipo() != null;

        try {
            if (flagNuevo) {
                String timezone = bean.getTimezone();
                bean = disponibilidadService.save(bean);
                if (flagRepite) {
                    bean.setTimezone(timezone);
                    listaGuardar = obtenerEventosRepetidos(bean, ConstantesUtil.EVENTO_CASO.CREAR.getValor());
                    DisponibilidadControllerRest.this.validar(listaGuardar, FuncionesUtil.removerTiempo(bean.getFechaInicio()), flagNuevo);
                    disponibilidadEventoService.save(listaGuardar);
                } else {
                    DisponibilidadEventoBean o = construirEvento(bean, bean.getFechaInicio(), bean.getFechaFin(), ConstantesUtil.EVENTO_CASO.CREAR.getValor());
                    validar(o, FuncionesUtil.removerTiempo(bean.getFechaInicio()), flagNuevo);
                    disponibilidadEventoService.save(o);
                }
            } else {

                List<DisponibilidadEventoBean> listaEliminar = null;
                listaGuardar = new ArrayList<>();
                Boolean guardarDisponibilidad = true;
                DisponibilidadEventoBean filtro = new DisponibilidadEventoBean();

                if (bean.getSinRepeticion()) {
                    if (flagRepite) {
                        listaGuardar = obtenerEventosRepetidos(bean, ConstantesUtil.EVENTO_CASO.MIXTO.getValor());
                        filtro.setDisponibilidad(new DisponibilidadBean(bean.getIdDisponibilidad()));
                        filtro.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
                        listaEliminar = disponibilidadEventoService.findAll(filtro);
                        int aux = listaEliminar.size();
                        for (int i = 0; i < listaGuardar.size(); i++) {
                            if (i < aux) {
                                listaGuardar.get(i).setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
                                listaGuardar.get(i).setIdDisponibilidadEvento(listaEliminar.get(0).getIdDisponibilidadEvento());
                                listaEliminar.remove(0);
                            }
                        }
                    } else {
                        listaGuardar.add(construirEvento(bean, bean.getFechaInicio(), bean.getFechaFin(), ConstantesUtil.EVENTO_CASO.EDITAR.getValor()));
                    }
                } else if (bean.getEditarSiguientes()) {
                    listaGuardar = obtenerEventosRepetidos(bean, ConstantesUtil.EVENTO_CASO.MIXTO.getValor());
                    filtro.setDisponibilidad(new DisponibilidadBean(bean.getIdDisponibilidad()));
                    filtro.setFechaInicio(FuncionesUtil.removerTiempo(bean.getFechaInicio()));
                    filtro.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
                    listaEliminar = disponibilidadEventoService.findAll(filtro);
                    int aux = listaEliminar.size();
                    for (int i = 0; i < listaGuardar.size(); i++) {
                        if (i < aux) {
                            listaGuardar.get(i).setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
                            listaGuardar.get(i).setIdDisponibilidadEvento(listaEliminar.get(0).getIdDisponibilidadEvento());
                            listaEliminar.remove(0);
                        }
                    }
                } else {
                    guardarDisponibilidad = false;
                    listaGuardar.add(construirEvento(bean, bean.getFechaInicio(), bean.getFechaFin(), ConstantesUtil.EVENTO_CASO.EDITAR.getValor()));
                }

                DisponibilidadControllerRest.this.validar(listaGuardar, FuncionesUtil.removerTiempo(bean.getFechaInicio()), flagNuevo);
                disponibilidadEventoService.save(listaGuardar);

                if (listaEliminar != null && !listaEliminar.isEmpty()) {
                    disponibilidadEventoService.delete(listaEliminar);
                }
                if (guardarDisponibilidad) {
                    disponibilidadService.save(bean);
                }

            }
        } catch (ApplicationException ex) {
            LOGGER.error("Error registro", ex);
            List<ErrorMessage> errorMessages = new ArrayList<>();
            errorMessages.add(new ErrorMessage("", "El profesional ya se encuentra registrado dentro de la disponibilidad."));
            return new ValidationResponse("error", errorMessages);
        }

        return new ValidationResponse("ok");
    }

    @RequestMapping(value = "/obtenerDisponibilidadesPublico", method = RequestMethod.POST)
    public ResponseEntity obtenerDisponibilidadesPublico(@RequestBody FiltroForm filtro) {

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

        DisponibilidadBean disponibilidad = new DisponibilidadBean(
                sedeEspProfesional,
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        DisponibilidadEventoBean bean = new DisponibilidadEventoBean(
                disponibilidad,
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        bean.setFechaInicio(filtro.getFechaDesde());
        bean.setFechaFin(filtro.getFechaHasta());

        List<DisponibilidadEventoBean> lista = disponibilidadEventoService.findAll(bean);

        List<CalendarioDto> resultado = new ArrayList<>();
        String colorTexto = messageSource.getMessage("parametro.color.texto", new Object[]{}, LocaleContextHolder.getLocale());

        CitaBean cita = new CitaBean(
                sedeEspProfesional,
                "",
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        cita.setFechaAtencion(filtro.getFechaDesde());
        cita.setFechaAtencionFin(filtro.getFechaHasta());

        List<CitaBean> citas = citaService.findAll(cita);

        for (CitaBean c : citas) {
            c.setFechaAtencionFin(FuncionesUtil.sumarHorasMinutosFecha(c.getFechaAtencion(), 0, c.getTiempoCita()));
        }

        boolean ningunaCita;
        List<Date> listaInicio;
        List<Date> listaFin;
        for (DisponibilidadEventoBean d : lista) {
            ningunaCita = true;
            listaInicio = new ArrayList<>();
            listaFin = new ArrayList<>();
            for (CitaBean c : citas) {
                if (FuncionesUtil.existeCruce(d.getFechaInicio(), d.getFechaFin(), c.getFechaAtencion(), c.getFechaAtencionFin())) {
                    if (ningunaCita) {
                        if (d.getFechaInicio().before(c.getFechaAtencion())) {
                            listaInicio.add(d.getFechaInicio());
                            listaFin.add(c.getFechaAtencion());
                        }
                        listaInicio.add(c.getFechaAtencionFin());
                        ningunaCita = false;
                    } else {
                        listaFin.add(c.getFechaAtencion());
                        if (c.getFechaAtencionFin().before(d.getFechaFin())) {
                            listaInicio.add(c.getFechaAtencionFin());
                        }
                    }
                }
            }
            if (listaFin.size() < listaInicio.size()) {
                listaFin.add(d.getFechaFin());
            }

            if (ningunaCita) {
                resultado.add(obtenerCalendarioBean(colorTexto, filtro.getRendering(), "D", d.getDisponibilidad(), d));
            } else {
                for (int i = 0; i < listaInicio.size(); i++) {
                    if (listaInicio.get(i).compareTo(listaFin.get(i)) != 0) {
                        d.setFechaInicio(listaInicio.get(i));
                        d.setFechaFin(listaFin.get(i));
                        resultado.add(obtenerCalendarioBean(colorTexto, filtro.getRendering(), "D", d.getDisponibilidad(), d));
                    }
                }
            }
        }
        return ResponseEntity.ok(resultado);
    }

    public CalendarioDto obtenerCalendarioBean(String colorTexto, String rendering, String tipo, DisponibilidadBean disponibilidad, DisponibilidadEventoBean evento) {
        CalendarioDto calendario = new CalendarioDto();
        PersonaUsuarioBean p = disponibilidad.getSedeEspProfesional().getProfesional().getPersonaUsuario();
        disponibilidad.setSinRepeticion(disponibilidad.getRepeticionTipo() == null);
        calendario.setTitle(p.getApellidoPaterno() + " " + p.getApellidoMaterno() + " " + p.getNombre());
//        calendario.setAllDay(true);
        calendario.setStart(evento.getFechaInicio());
        calendario.setEnd(evento.getFechaFin());
        calendario.setColor(disponibilidad.getSedeEspProfesional().getProfesional().getColorDisponibilidad());
        calendario.setTextColor(colorTexto);
        calendario.setRendering(rendering);
        calendario.setTipo(tipo);
        calendario.setDisponibilidad(disponibilidad);
        calendario.setIdDisponibilidadEvento(evento.getIdDisponibilidadEvento());
        return calendario;
    }

    public List<DisponibilidadEventoBean> obtenerEventosRepetidos(DisponibilidadBean d, int caso) {
        Integer diasRepeticion[] = FuncionesUtil.convertirStringAArrayDays(d.getRepeticionDias());
        Calendar start = Calendar.getInstance();
        start.setTime(d.getFechaInicio());
        Calendar start2 = Calendar.getInstance();
        start2.setTime(d.getFechaFin());

        if (d.getRepeticionTipo() != 1 && diasRepeticion.length == 0) {
            return null;
        }

        RepetirEventoUtil o = new RepetirEventoUtil(start, start2, diasRepeticion, d, caso, d.getTimezone());
        return o.obtenerEventosRepetidos();
    }

    public DisponibilidadEventoBean construirEvento(DisponibilidadBean bean, Date fechaInicio, Date fechaFin, int caso) {
        DisponibilidadEventoBean d = new DisponibilidadEventoBean();
        if (caso == ConstantesUtil.EVENTO_CASO.EDITAR.getValor()) {
            d.setIdDisponibilidadEvento(bean.getIdDisponibilidadEvento());
            d.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        }
        d.setFechaInicio(fechaInicio);
        d.setFechaFin(fechaFin);
        d.setDisponibilidad(bean);
        return d;
    }

    public void validar(List<DisponibilidadEventoBean> lista, Date fechaInicial, boolean flagNuevo) throws ApplicationException {
        String resultado = "";
        for (DisponibilidadEventoBean bean : lista) {
            resultado += validarDisponibilidad(bean, fechaInicial, flagNuevo);
        }
        if (!resultado.equals("")) {
            throw new ApplicationException(resultado);
        }
    }

    public void validar(DisponibilidadEventoBean bean, Date fechaInicial, boolean flagNuevo) throws ApplicationException {
        String resultado = validarDisponibilidad(bean, fechaInicial, flagNuevo);
        if (!resultado.equals("")) {
            throw new ApplicationException(resultado);
        }
    }

    public String validarDisponibilidad(DisponibilidadEventoBean bean, Date fechaInicial, boolean flagNuevo) {
        String resultado = "";
        List<DisponibilidadEventoBean> eventos;

        if (flagNuevo || bean.getFechaInicio().before(fechaInicial)) {
            eventos = disponibilidadEventoService.findByIdProfesionalAndFecha(
                    bean.getDisponibilidad().getSedeEspProfesional().getId().getIdProfesional(),
                    FuncionesUtil.removerTiempo(bean.getFechaInicio()),
                    FuncionesUtil.removerTiempo(bean.getFechaFin()));
        } else {
            eventos = disponibilidadEventoService.findByIdProfesionalAndFechaAndIdDisponibilidadNot(
                    bean.getDisponibilidad().getSedeEspProfesional().getId().getIdProfesional(),
                    FuncionesUtil.removerTiempo(bean.getFechaInicio()),
                    FuncionesUtil.removerTiempo(bean.getFechaFin()),
                    bean.getDisponibilidad().getIdDisponibilidad());
        }

        for (DisponibilidadEventoBean evento : eventos) {
            if (FuncionesUtil.existeCruce(evento.getFechaInicio(), evento.getFechaFin(), bean.getFechaInicio(), bean.getFechaFin())) {
                resultado += FuncionesUtil.mensajeCruce(evento.getFechaInicio(), evento.getFechaFin()) + "<br>";
            }
        }

        return resultado;
    }

    public void validarCita(List<DisponibilidadEventoBean> lista) throws ApplicationException {
        for (DisponibilidadEventoBean bean : lista) {
            validarCita(bean);
        }
    }

    public void validarCita(DisponibilidadEventoBean bean) throws ApplicationException {
        int i = 0;
        List<CitaBean> eventos = citaService.findByIdSedeIdEspecialidadIdProfesionalAndFecha(
                bean.getDisponibilidad().getSedeEspProfesional().getId().getIdSede(),
                bean.getDisponibilidad().getSedeEspProfesional().getId().getIdEspecialidad(),
                bean.getDisponibilidad().getSedeEspProfesional().getId().getIdProfesional(),
                FuncionesUtil.removerTiempo(bean.getFechaInicio()),
                FuncionesUtil.removerTiempo(bean.getFechaFin()));

        while (i < eventos.size()) {
            eventos.get(i).setFechaAtencionFin(FuncionesUtil.sumarHorasMinutosFecha(eventos.get(i).getFechaAtencion(), 0, eventos.get(i).getTiempoCita()));
            if (FuncionesUtil.existeCruce(bean.getFechaInicio(), bean.getFechaFin(), eventos.get(i).getFechaAtencion(), eventos.get(i).getFechaAtencionFin())) {
                throw new ApplicationException("Error en validacion.");
            } else {
                i++;
            }
        }
    }
}
