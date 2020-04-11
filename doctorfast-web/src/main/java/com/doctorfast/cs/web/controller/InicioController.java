/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.service.ClinicaService;
import com.doctorfast.cs.service.PromocionService;
import com.doctorfast.cs.service.SedeEspecialidadService;
import com.doctorfast.cs.service.UbigeoService;
import com.doctorfast.cs.service.util.FuncionesUtil;
import com.doctorfast.cs.web.util.ConstantesUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InicioController {

    @Autowired
    private ClinicaService clinicaService;
    @Autowired
    private SedeEspecialidadService sedeEspecialidadService;
    @Autowired
    private UbigeoService ubigeoService;
    @Autowired
    private PromocionService promocionService;

    @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("sedeEspecialidadBean", new SedeEspecialidadBean());
        model.addAttribute("clinicas", obtenerClinicas("nombreAbreviado"));
        model.addAttribute("especialidades", obtenerEspecialidades());
        model.addAttribute("departamentos", ubigeoService.obtenerDepartamentos());
        model.addAttribute("promociones", promocionService.findByEstadoAndFechaFinAfterOrderByOrdenAsc(
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor(),
                FuncionesUtil.sumarHorasMinutosFecha(new Date(), -24, 0)));
        model.addAttribute("partners", obtenerClinicas("orden"));
        return "publico/index";
    }

    @RequestMapping(value = "consultarEspecialidad", method = RequestMethod.GET)
    public String consultarEspecialidad(Model model, @RequestParam(value = "sede.ubigeo.id.coDepartamento", required = false) Integer coDepartamento,
            @RequestParam(value = "sede.ubigeo.id.coProvincia", required = false) Integer coProvincia, @RequestParam(value = "sede.ubigeo.id.coDistrito", required = false) Integer coDistrito) {
        model.addAttribute("sedeEspecialidadBean", new SedeEspecialidadBean());
        model.addAttribute("clinicas", obtenerClinicas("nombreAbreviado"));
        model.addAttribute("especialidades", obtenerEspecialidades());
        model.addAttribute("departamentos", ubigeoService.obtenerDepartamentos());
        if (!FuncionesUtil.esNuloOVacio(coDepartamento)) {
            model.addAttribute("provincias", ubigeoService.obtenerProvincias(coDepartamento));
            if (!FuncionesUtil.esNuloOVacio(coProvincia)) {
                model.addAttribute("distritos", ubigeoService.obtenerDistritos(coDepartamento, coProvincia));
            }
        }
        return "publico/consultarEspecialidad";
    }

    public List<EspecialidadBean> obtenerEspecialidades() {

        SedeEspecialidadBean sedeEspecialidad = new SedeEspecialidadBean(
                new SedeBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()),
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

    public List<ClinicaBean> obtenerClinicas(String order) {
        ClinicaBean bean = new ClinicaBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        return clinicaService.findAll(bean, order);
    }

}
