/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.DisponibilidadBean;
import com.doctorfast.cs.data.mapper.DisponibilidadMapper;
import com.doctorfast.cs.data.model.Disponibilidad;
import com.doctorfast.cs.data.repository.DisponibilidadRepository;
import com.doctorfast.cs.data.specification.DisponibilidadSpecification;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.service.DisponibilidadService;
import com.doctorfast.cs.service.util.FuncionesUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBS GROUP
 */
@Service
public class DisponibilidadServiceImpl implements DisponibilidadService {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    @Override
    public List<DisponibilidadBean> findAll(DisponibilidadBean bean) {
        List<Specification<Disponibilidad>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new DisponibilidadSpecification(criteria));
        }

        Specification<Disponibilidad> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return DisponibilidadMapper.INSTANCE.toBean(disponibilidadRepository.findAll(filtros));
    }

    @Override
    public int setEstadoByIdDisponibilidad(String estado, Integer id) {
        return disponibilidadRepository.setEstadoByIdDisponibilidad(estado, id);
    }

    @Override
    public DisponibilidadBean save(DisponibilidadBean bean) {
        Disponibilidad model = DisponibilidadMapper.INSTANCE.toModel(bean);
        return DisponibilidadMapper.INSTANCE.toBean(disponibilidadRepository.save(model));
    }

    @Override
    public DisponibilidadBean findOne(Integer id) {
        return DisponibilidadMapper.INSTANCE.toBean(disponibilidadRepository.findOne(id));
    }

    public List<SearchCriteria> obtenerFiltros(DisponibilidadBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        }
        if (bean.getSedeEspProfesional() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getEstado())) {
                lista.add(new SearchCriteria("estado", ":", bean.getSedeEspProfesional().getEstado(), "sedeEspProfesional"));
            }
            if (bean.getSedeEspProfesional().getId() != null) {
                if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getId().getIdEspecialidad())) {
                    lista.add(new SearchCriteria("idEspecialidad", ":", bean.getSedeEspProfesional().getId().getIdEspecialidad(), "sedeEspProfesionalId"));
                }
                if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getId().getIdProfesional())) {
                    lista.add(new SearchCriteria("idProfesional", ":", bean.getSedeEspProfesional().getId().getIdProfesional(), "sedeEspProfesionalId"));
                }
                if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getId().getIdSede())) {
                    lista.add(new SearchCriteria("idSede", ":", bean.getSedeEspProfesional().getId().getIdSede(), "sedeEspProfesionalId"));
                }
            }
            if (bean.getSedeEspProfesional().getProfesional() != null) {
                if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getProfesional().getEstado())) {
                    lista.add(new SearchCriteria("estado", ":", bean.getSedeEspProfesional().getProfesional().getEstado(), "profesional"));
                }
                if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getProfesional().getCop())) {
                    lista.add(new SearchCriteria("cop", ":", bean.getSedeEspProfesional().getProfesional().getCop(), "profesional"));
                }
                if (bean.getSedeEspProfesional().getProfesional().getClinica() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getProfesional().getClinica().getIdClinica())) {
                        lista.add(new SearchCriteria("idClinica", ":", bean.getSedeEspProfesional().getProfesional().getClinica().getIdClinica(), "clinica"));
                    }
                }
                if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getProfesional().getIdProfesionals())) {
                    lista.add(new SearchCriteria("idProfesional", "in", bean.getSedeEspProfesional().getProfesional().getIdProfesionals(), "profesional"));
                }
            }
            if (bean.getSedeEspProfesional().getSedeEspecialidad() != null) {
                if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getSedeEspecialidad().getEstado())) {
                    lista.add(new SearchCriteria("estado", ":", bean.getSedeEspProfesional().getSedeEspecialidad().getEstado(), "sedeEspecialidad"));
                }
                if (bean.getSedeEspProfesional().getSedeEspecialidad().getSede() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getSedeEspecialidad().getSede().getEstado())) {
                        lista.add(new SearchCriteria("estado", ":", bean.getSedeEspProfesional().getSedeEspecialidad().getSede().getEstado(), "sede"));
                    }
                }
                if (bean.getSedeEspProfesional().getSedeEspecialidad().getEspecialidad() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getSedeEspecialidad().getEspecialidad().getEstado())) {
                        lista.add(new SearchCriteria("estado", ":", bean.getSedeEspProfesional().getSedeEspecialidad().getEspecialidad().getEstado(), "especialidad"));
                    }
                }
            }
        }

        return lista;
    }
}
