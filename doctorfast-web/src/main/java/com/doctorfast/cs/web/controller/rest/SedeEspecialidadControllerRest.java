/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.data.bean.SedeEspecialidadIdBean;
import com.doctorfast.cs.service.SedeEspecialidadService;
import com.doctorfast.cs.service.SedeService;
import com.doctorfast.cs.service.util.FuncionesUtil;
import com.doctorfast.cs.web.config.security.CustomUserDetails;
import com.doctorfast.cs.web.util.ConstantesUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBS GROUP
 */
@RestController
@RequestMapping(value = "/rest/sedeEspecialidad")
public class SedeEspecialidadControllerRest {

    @Autowired
    private SedeEspecialidadService sedeEspecialidadService;
    @Autowired
    private SedeService sedeService;

    @RequestMapping(value = "/obtenerSedeEspecialidades", method = RequestMethod.POST)
    public ResponseEntity obtenerSedeEspecialidades(@RequestBody SedeEspecialidadBean bean) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bean.getSede().setClinica(new ClinicaBean(user.getIdClinica()));
        bean.getSede().setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        bean.getEspecialidad().setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        List<SedeEspecialidadBean> lista = sedeEspecialidadService.findAll(bean);
        return ResponseEntity.ok(lista);
    }

    @RequestMapping(value = "/obtenerSedeEspecialidadesNuevos/{idEspecialidad}", method = RequestMethod.GET)
    public ResponseEntity obtenerSedeEspecialidadesNuevos(@PathVariable Integer idEspecialidad) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SedeEspecialidadBean> lista = new ArrayList<>();
        SedeBean sedeBean = new SedeBean(new ClinicaBean(user.getIdClinica()));
        sedeBean.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());

        SedeEspecialidadBean bean = new SedeEspecialidadBean(sedeBean, new EspecialidadBean(idEspecialidad));
        List<SedeEspecialidadBean> sedeEspecialidades = sedeEspecialidadService.findAll(bean);

        List<SedeBean> sedes = sedeService.findAll(sedeBean);

        boolean flag;
        int i;
        for (SedeBean sede : sedes) {
            flag = true;
            i = 0;
            while (i < sedeEspecialidades.size() && flag) {
                if (sede.getIdSede().equals(sedeEspecialidades.get(i).getId().getIdSede())) {
                    flag = false;
                    lista.add(sedeEspecialidades.get(i));
                } else {
                    i++;
                }
            }
            if (flag) {
                bean = new SedeEspecialidadBean(sedeBean, new EspecialidadBean(idEspecialidad));
                bean.setEstado(ConstantesUtil.ESTADO_REGISTRO.INACTIVO.getValor());
                bean.setSede(sede);
                bean.setId(new SedeEspecialidadIdBean(idEspecialidad, sede.getIdSede()));
                lista.add(bean);
            }
        }

        return ResponseEntity.ok(lista);
    }

    @RequestMapping(value = "/desactivar/{idSede}/{idEspecialidad}", method = RequestMethod.GET)
    public ResponseEntity desactivar(@PathVariable Integer idSede, @PathVariable Integer idEspecialidad) {
        return ResponseEntity.ok(sedeEspecialidadService.setEstadoByIdSedeAndIdEspecialidad(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(), idSede, idEspecialidad));
    }

    @RequestMapping(value = "/agregarEditar", method = RequestMethod.POST)
    public ResponseEntity agregarEditar(@RequestBody List<SedeEspecialidadBean> lista) {
        return ResponseEntity.ok(sedeEspecialidadService.save(lista));
    }

    @RequestMapping(value = "/obtenerSedeEspecialidadesPublico", method = RequestMethod.POST)
    public ResponseEntity obtenerSedeEspecialidadesPublico(@RequestBody SedeEspecialidadBean bean) {
        if (FuncionesUtil.esNuloOVacio(bean.getSede().getClinica().getIdClinica())) {
            bean.getSede().setClinica(new ClinicaBean(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor()));
        }
        if (FuncionesUtil.esNuloOVacio(bean.getEspecialidad().getIdEspecialidad())) {
            bean.getEspecialidad().setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        }
        bean.getSede().setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        bean.setEstado(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        List<SedeEspecialidadBean> lista = sedeEspecialidadService.findAll(bean);
        return ResponseEntity.ok(lista);
    }

}
