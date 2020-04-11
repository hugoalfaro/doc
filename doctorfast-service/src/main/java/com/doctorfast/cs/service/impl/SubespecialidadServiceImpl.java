/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.SubespecialidadBean;
import com.doctorfast.cs.data.mapper.SubespecialidadMapper;
import com.doctorfast.cs.data.model.Subespecialidad;
import com.doctorfast.cs.data.repository.SubespecialidadRepository;
import com.doctorfast.cs.data.specification.SubespecialidadSpecification;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.service.SubespecialidadService;
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
public class SubespecialidadServiceImpl implements SubespecialidadService {

    @Autowired
    private SubespecialidadRepository subespecialidadRepository;

    @Override
    public List<SubespecialidadBean> findAll(SubespecialidadBean bean) {
        List<Specification<Subespecialidad>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new SubespecialidadSpecification(criteria));
        }

        Specification<Subespecialidad> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return SubespecialidadMapper.INSTANCE.toBean(subespecialidadRepository.findAll(filtros, new Sort(Sort.Direction.ASC, "nombre")));
    }

    public List<SearchCriteria> obtenerFiltros(SubespecialidadBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getNombre())) {
            lista.add(new SearchCriteria("nombre", ":", bean.getNombre()));
        }
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        }
        if (bean.getEspecialidad() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getEspecialidad().getIdEspecialidad())) {
                lista.add(new SearchCriteria("idEspecialidad", ":", bean.getEspecialidad().getIdEspecialidad(), "especialidad"));
            }
        }

        return lista;
    }

}
