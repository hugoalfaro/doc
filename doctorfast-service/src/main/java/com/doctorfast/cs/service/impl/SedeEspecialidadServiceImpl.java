/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.SedeEspecialidadBean;
import com.doctorfast.cs.data.mapper.SedeEspecialidadMapper;
import com.doctorfast.cs.data.model.SedeEspecialidad;
import com.doctorfast.cs.data.repository.SedeEspecialidadRepository;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.data.specification.SedeEspecialidadSpecification;
import com.doctorfast.cs.service.SedeEspecialidadService;
import com.doctorfast.cs.service.util.ConstantesUtil;
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
public class SedeEspecialidadServiceImpl implements SedeEspecialidadService {

    @Autowired
    private SedeEspecialidadRepository sedeEspecialidadRepository;

    @Override
    public List<SedeEspecialidadBean> findAll(SedeEspecialidadBean bean) {
        List<Specification<SedeEspecialidad>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new SedeEspecialidadSpecification(criteria));
        }

        Specification<SedeEspecialidad> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return SedeEspecialidadMapper.INSTANCE.toBean(sedeEspecialidadRepository.findAll(filtros, new Sort(Sort.Direction.ASC, "especialidad.nombre")));
    }

    @Override
    public int setEstadoByIdSedeAndIdEspecialidad(String estado, Integer idSede, Integer idEspecialidad) {
        return sedeEspecialidadRepository.setEstadoByIdSedeAndIdEspecialidad(estado, idSede, idEspecialidad);
    }

    @Override
    public List<SedeEspecialidadBean> save(List<SedeEspecialidadBean> bean) {
        List<SedeEspecialidad> lista = SedeEspecialidadMapper.INSTANCE.toModel(bean);
        return SedeEspecialidadMapper.INSTANCE.toBean(sedeEspecialidadRepository.save(lista));
    }

    public List<SearchCriteria> obtenerFiltros(SedeEspecialidadBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        } else {
            lista.add(new SearchCriteria("estado", "!", ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor()));
        }
        if (bean.getId() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getId().getIdSede())) {
                lista.add(new SearchCriteria("idSede", ":", bean.getId().getIdSede(), "SedeEspecialidadId"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getId().getIdEspecialidad())) {
                lista.add(new SearchCriteria("idEspecialidad", ":", bean.getId().getIdEspecialidad(), "SedeEspecialidadId"));
            }
        }
        if (bean.getSede() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getSede().getIdSede())) {
                lista.add(new SearchCriteria("idSede", ":", bean.getSede().getIdSede(), "sede"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getSede().getEstado())) {
                lista.add(new SearchCriteria("estado", ":", bean.getSede().getEstado(), "sede"));
            }
            if (bean.getSede().getClinica() != null) {
                if (!FuncionesUtil.esNuloOVacio(bean.getSede().getClinica().getIdClinica())) {
                    lista.add(new SearchCriteria("idClinica", ":", bean.getSede().getClinica().getIdClinica(), "clinica"));
                }
                if (!FuncionesUtil.esNuloOVacio(bean.getSede().getClinica().getEstado())) {
                    lista.add(new SearchCriteria("estado", ":", bean.getSede().getClinica().getEstado(), "clinica"));
                }
            }
            if (bean.getSede().getUbigeo() != null && bean.getSede().getUbigeo().getId() != null) {
                if (!FuncionesUtil.esNuloOVacio(bean.getSede().getUbigeo().getId().getCoDepartamento())) {
                    lista.add(new SearchCriteria("coDepartamento", ":", bean.getSede().getUbigeo().getId().getCoDepartamento(), "ubigeo"));
                }
                if (!FuncionesUtil.esNuloOVacio(bean.getSede().getUbigeo().getId().getCoProvincia())) {
                    lista.add(new SearchCriteria("coProvincia", ":", bean.getSede().getUbigeo().getId().getCoProvincia(), "ubigeo"));
                }
                if (!FuncionesUtil.esNuloOVacio(bean.getSede().getUbigeo().getId().getCoDistrito())) {
                    lista.add(new SearchCriteria("coDistrito", ":", bean.getSede().getUbigeo().getId().getCoDistrito(), "ubigeo"));
                }
            }
        }
        if (bean.getEspecialidad() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getEspecialidad().getIdEspecialidad())) {
                lista.add(new SearchCriteria("idEspecialidad", ":", bean.getEspecialidad().getIdEspecialidad(), "especialidad"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getEspecialidad().getCodigo())) {
                lista.add(new SearchCriteria("codigo", ":", bean.getEspecialidad().getCodigo(), "especialidad"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getEspecialidad().getNombre())) {
                lista.add(new SearchCriteria("nombre", ":", bean.getEspecialidad().getNombre(), "especialidad"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getEspecialidad().getEstado())) {
                lista.add(new SearchCriteria("estado", ":", bean.getEspecialidad().getEstado(), "especialidad"));
            }
        }

        return lista;
    }

}
