/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.SedeEspSubespBean;
import com.doctorfast.cs.service.SedeEspSubespService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/rest/sedeEspSubesp")
public class SedeEspSubespControllerRest {

    @Autowired
    private SedeEspSubespService sedeEspSubespService;

    @RequestMapping(value = "/obtenerSedeEspSubesps/{idSede}/{idEspecialidad}", method = RequestMethod.GET)
    public ResponseEntity obtenerSedeEspSubesps(@PathVariable Integer idSede, @PathVariable Integer idEspecialidad) {
        List<SedeEspSubespBean> lista = sedeEspSubespService.findByIdIdSedeAndIdIdEspecialidad(idSede, idEspecialidad);
        return ResponseEntity.ok(lista);
    }

    @RequestMapping(value = "/desactivar/{idSede}/{idEspecialidad}/{idSubespecialidad}", method = RequestMethod.GET)
    public ResponseEntity desactivar(@PathVariable Integer idSede, @PathVariable Integer idEspecialidad, @PathVariable Integer idSubespecialidad) {
        sedeEspSubespService.delete(idSede, idEspecialidad, idSubespecialidad);
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public ResponseEntity agregar(@RequestBody SedeEspSubespBean bean) {
        return ResponseEntity.ok(sedeEspSubespService.save(bean));
    }

}
