/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.EspecialidadBean;
import com.doctorfast.cs.data.mapper.EspecialidadMapper;
import com.doctorfast.cs.data.model.Especialidad;
import com.doctorfast.cs.data.repository.EspecialidadRepository;
import com.doctorfast.cs.data.specification.EspecialidadSpecification;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.service.EspecialidadService;
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
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public List<EspecialidadBean> findAll(EspecialidadBean bean) {
        List<Specification<Especialidad>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new EspecialidadSpecification(criteria));
        }

        Specification<Especialidad> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return EspecialidadMapper.INSTANCE.toBean(especialidadRepository.findAll(filtros, new Sort(Sort.Direction.ASC, "nombre")));
    }

    public List<SearchCriteria> obtenerFiltros(EspecialidadBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getCodigo())) {
            lista.add(new SearchCriteria("codigo", ":", bean.getCodigo()));
        }
        if (!FuncionesUtil.esNuloOVacio(bean.getNombre())) {
            lista.add(new SearchCriteria("nombre", ":", bean.getNombre()));
        }
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        }

        return lista;
    }

}
