/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.rest;

import com.doctorfast.cs.data.bean.UbigeoBean;
import com.doctorfast.cs.service.UbigeoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBS GROUP
 */
@RestController
@RequestMapping(value = "/rest/ubigeo")
public class UbigeoControllerRest {

    @Autowired
    private UbigeoService ubigeoService;

    @RequestMapping(value = "/obtenerDepartamentos", method = RequestMethod.GET)
    public ResponseEntity obtenerDepartamentos() {

        List<UbigeoBean> lista = ubigeoService.obtenerDepartamentos();
        return ResponseEntity.ok(lista);
    }

    @RequestMapping(value = "/obtenerProvincias/{coDepartamento}", method = RequestMethod.GET)
    public ResponseEntity obtenerProvincias(@PathVariable Integer coDepartamento) {

        List<UbigeoBean> lista = ubigeoService.obtenerProvincias(coDepartamento);
        return ResponseEntity.ok(lista);
    }

    @RequestMapping(value = "/obtenerDistritos/{coDepartamento}/{coProvincia}", method = RequestMethod.GET)
    public ResponseEntity obtenerDistritos(@PathVariable Integer coDepartamento, @PathVariable Integer coProvincia) {

        List<UbigeoBean> lista = ubigeoService.obtenerDistritos(coDepartamento, coProvincia);
        return ResponseEntity.ok(lista);
    }

}
