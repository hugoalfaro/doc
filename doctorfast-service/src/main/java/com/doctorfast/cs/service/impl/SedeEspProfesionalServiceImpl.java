/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.SedeEspProfesionalBean;
import com.doctorfast.cs.data.mapper.SedeEspProfesionalMapper;
import com.doctorfast.cs.data.model.SedeEspProfesional;
import com.doctorfast.cs.data.repository.SedeEspProfesionalRepository;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.data.specification.SedeEspProfesionalSpecification;
import com.doctorfast.cs.service.SedeEspProfesionalService;
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
public class SedeEspProfesionalServiceImpl implements SedeEspProfesionalService {

    @Autowired
    private SedeEspProfesionalRepository sedeEspProfesionalRepository;

    @Override
    public List<SedeEspProfesionalBean> findAll(SedeEspProfesionalBean bean, String order) {
        List<Specification<SedeEspProfesional>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new SedeEspProfesionalSpecification(criteria));
        }

        Specification<SedeEspProfesional> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }
        
        if(FuncionesUtil.esNuloOVacio(order))
            return SedeEspProfesionalMapper.INSTANCE.toBean(sedeEspProfesionalRepository.findAll(filtros));

        return SedeEspProfesionalMapper.INSTANCE.toBean(sedeEspProfesionalRepository.findAll(filtros, new Sort(Sort.Direction.ASC, order)));
    }

    @Override
    public List<SedeEspProfesionalBean> save(List<SedeEspProfesionalBean> bean) {
        List<SedeEspProfesional> model = SedeEspProfesionalMapper.INSTANCE.toModel(bean);
        return SedeEspProfesionalMapper.INSTANCE.toBean(sedeEspProfesionalRepository.save(model));
    }

    @Override
    public void delete(List<SedeEspProfesionalBean> bean) {
        List<SedeEspProfesional> model = SedeEspProfesionalMapper.INSTANCE.toModel(bean);
        sedeEspProfesionalRepository.delete(model);
    }

    @Override
    public List<SedeEspProfesionalBean> findByIdProfesionalAndIdClinica(Integer idProfesional, Integer idClinica) {
        return SedeEspProfesionalMapper.INSTANCE.toBean(sedeEspProfesionalRepository.findByIdIdProfesionalAndSedeEspecialidadSedeClinicaIdClinica(idProfesional, idClinica));
    }

    public List<SearchCriteria> obtenerFiltros(SedeEspProfesionalBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        }
        if (bean.getId() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getId().getIdSede())) {
                lista.add(new SearchCriteria("idSede", ":", bean.getId().getIdSede(), "sedeEspProfesionalId"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getId().getIdEspecialidad())) {
                lista.add(new SearchCriteria("idEspecialidad", ":", bean.getId().getIdEspecialidad(), "sedeEspProfesionalId"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getId().getIdProfesional())) {
                lista.add(new SearchCriteria("idProfesional", ":", bean.getId().getIdProfesional(), "sedeEspProfesionalId"));
            }
        }
            if (bean.getProfesional() != null) {
                if (!FuncionesUtil.esNuloOVacio(bean.getProfesional().getEstado())) {
                    lista.add(new SearchCriteria("estado", ":", bean.getProfesional().getEstado(), "profesional"));
                }
                if (bean.getProfesional().getClinica() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getProfesional().getClinica().getIdClinica())) {
                        lista.add(new SearchCriteria("idClinica", ":", bean.getProfesional().getClinica().getIdClinica(), "clinica"));
                    }
                }
            }
            if (bean.getSedeEspecialidad() != null) {
                if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspecialidad().getEstado())) {
                    lista.add(new SearchCriteria("estado", ":", bean.getSedeEspecialidad().getEstado(), "sedeEspecialidad"));
                }
                if (bean.getSedeEspecialidad().getSede() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspecialidad().getSede().getEstado())) {
                        lista.add(new SearchCriteria("estado", ":", bean.getSedeEspecialidad().getSede().getEstado(), "sede"));
                    }
                }
                if (bean.getSedeEspecialidad().getEspecialidad() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspecialidad().getEspecialidad().getEstado())) {
                        lista.add(new SearchCriteria("estado", ":", bean.getSedeEspecialidad().getEspecialidad().getEstado(), "especialidad"));
                    }
                }
            }
        return lista;
    }

}
