/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.service.SedeEspProfesionalService;
import com.doctorfast.cs.web.util.ConstantesUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBS GROUP
 */
@RestController
@RequestMapping(value = "/rest/sedeEspProfesional")
public class SedeEspProfesionalControllerRest {

    @Autowired
    private SedeEspProfesionalService sedeEspProfesionalService;

    @RequestMapping(value = "/obtenerEspecialidadesDeProfesional", method = RequestMethod.POST)
    public ResponseEntity obtenerEspecialidadesDeProfesional(@RequestBody SedeEspProfesionalBean bean) {        
        List<SedeEspProfesionalBean> lista = sedeEspProfesionalService.findAll(agregarEstados(bean), "sedeEspecialidad.especialidad.nombre");
        List<EspecialidadBean> resultado = new ArrayList<>();
        for (SedeEspProfesionalBean o : lista) {
            if (!resultado.contains(o.getSedeEspecialidad().getEspecialidad())) {
                resultado.add(o.getSedeEspecialidad().getEspecialidad());
            }
        }
        return ResponseEntity.ok(resultado);
    }

    @RequestMapping(value = "/obtenerProfesionalesDeSedeEspecialidad", method = RequestMethod.POST)
    public ResponseEntity obtenerProfesionalesDeSedeEspecialidad(@RequestBody SedeEspProfesionalBean bean) {        
        List<SedeEspProfesionalBean> lista = sedeEspProfesionalService.findAll(agregarEstados(bean), "profesional.personaUsuario.apellidoPaterno");
        List<ProfesionalBean> resultado = new ArrayList<>();
        for (SedeEspProfesionalBean o : lista) {
            if (!resultado.contains(o.getProfesional())) {
                resultado.add(o.getProfesional());
            }
        }
        return ResponseEntity.ok(resultado);
    }

    @RequestMapping(value = "/obtenerSedesDeEspecialidadProfesional", method = RequestMethod.POST)
    public ResponseEntity obtenerSedesDeEspecialidadProfesional(@RequestBody SedeEspProfesionalBean bean) {        
        List<SedeEspProfesionalBean> lista = sedeEspProfesionalService.findAll(agregarEstados(bean), "sedeEspecialidad.sede.nombre");
        List<SedeBean> resultado = new ArrayList<>();
        for (SedeEspProfesionalBean o : lista) {
            if (!resultado.contains(o.getSedeEspecialidad().getSede())) {
                resultado.add(o.getSedeEspecialidad().getSede());
            }
        }
        return ResponseEntity.ok(resultado);
    }
    
    public SedeEspProfesionalBean agregarEstados(SedeEspProfesionalBean bean){
        
        SedeEspecialidadBean sedeEspecialidad = new SedeEspecialidadBean(
                new SedeBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()), 
                new EspecialidadBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()), 
                ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        
        ProfesionalBean profesional = new ProfesionalBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        
        bean.setSedeEspecialidad(sedeEspecialidad);
        bean.setProfesional(profesional);
        bean.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        
        return bean;
    }

}
