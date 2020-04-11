/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.validator;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.service.util.FuncionesUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author MBS GROUP
 */
@Component
public class ClinicaValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return ClinicaBean.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ClinicaBean bean = (ClinicaBean) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ruc", "validation.ruc.notEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombreComercial", "validation.nombreComercial.notEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombreAbreviado", "validation.nombreAbreviado.notEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "direccionComercial", "validation.direccionComercial.notEmpty");

        FuncionesUtil.validarTexto("ruc", bean.getRuc(), 11, errors);
        FuncionesUtil.validarTexto("nombreComercial", bean.getNombreComercial(), 50, errors);
        FuncionesUtil.validarTexto("nombreAbreviado", bean.getNombreAbreviado(), 50, errors);
        FuncionesUtil.validarTexto("direccionComercial", bean.getDireccionComercial(), 50, errors);
    }

}
