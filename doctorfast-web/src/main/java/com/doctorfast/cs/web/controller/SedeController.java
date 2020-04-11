/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller;

import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.bean.UbigeoBean;
import com.doctorfast.cs.data.bean.UbigeoIdBean;
import com.doctorfast.cs.service.UbigeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author MBS GROUP
 */
@Controller
@RequestMapping(value = "/sedes")
public class SedeController {

    @Autowired
    private UbigeoService ubigeoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("sedeBean", new SedeBean(new UbigeoBean(new UbigeoIdBean())));
        model.addAttribute("departamentos", ubigeoService.obtenerDepartamentos());
        return "sede/index";
    }

}
