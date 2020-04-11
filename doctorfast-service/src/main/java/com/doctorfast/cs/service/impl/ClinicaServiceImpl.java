/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.ClinicaBean;
import com.doctorfast.cs.data.mapper.ClinicaMapper;
import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.repository.ClinicaRepository;
import com.doctorfast.cs.data.specification.ClinicaSpecification;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.service.ClinicaService;
import com.doctorfast.cs.service.util.FuncionesUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBS GROUP
 */
@Service
public class ClinicaServiceImpl implements ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public List<ClinicaBean> findAll(ClinicaBean bean, String order) {
        List<Specification<Clinica>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new ClinicaSpecification(criteria));
        }

        Specification<Clinica> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return ClinicaMapper.INSTANCE.toBean(clinicaRepository.findAll(filtros, new Sort(Sort.Direction.ASC, order)));
    }

    @Override
    public ClinicaBean findByAdministradorsIdAdministrador(Integer idAdministrador) {
        return ClinicaMapper.INSTANCE.toBean(clinicaRepository.findByAdministradorsIdAdministrador(idAdministrador));
    }

    @Override
    public ClinicaBean save(ClinicaBean bean) {
        Clinica model = ClinicaMapper.INSTANCE.toModel(bean);
        model = clinicaRepository.save(model);
        return ClinicaMapper.INSTANCE.toBean(model);
    }

    @Override
    public ClinicaBean findOne(Integer idClinica) {
        return ClinicaMapper.INSTANCE.toBean(clinicaRepository.findOne(idClinica));
    }

    public List<SearchCriteria> obtenerFiltros(ClinicaBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        }
        if (bean.getProfesional() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getProfesional().getEstado())) {
                lista.add(new SearchCriteria("estado", ":", bean.getProfesional().getEstado(), "profesional"));
            }
            if (bean.getProfesional().getPersonaUsuario() != null) {
                if (!FuncionesUtil.esNuloOVacio(bean.getProfesional().getPersonaUsuario().getDocumentoIdentidad())) {
                    lista.add(new SearchCriteria("documentoIdentidad", ":", bean.getProfesional().getPersonaUsuario().getDocumentoIdentidad(), "personaUsuario"));
                }
            }
        }

        return lista;
    }

}
