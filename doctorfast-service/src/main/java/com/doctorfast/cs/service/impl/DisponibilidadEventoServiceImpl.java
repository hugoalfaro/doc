/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.DisponibilidadEventoBean;
import com.doctorfast.cs.data.mapper.DisponibilidadEventoMapper;
import com.doctorfast.cs.data.model.DisponibilidadEvento;
import com.doctorfast.cs.data.repository.DisponibilidadEventoRepository;
import com.doctorfast.cs.data.specification.DisponibilidadEventoSpecification;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.service.DisponibilidadEventoService;
import com.doctorfast.cs.service.util.FuncionesUtil;
import java.util.ArrayList;
import java.util.Date;
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
public class DisponibilidadEventoServiceImpl implements DisponibilidadEventoService {

    @Autowired
    private DisponibilidadEventoRepository disponibilidadEventoRepository;

    @Override
    public List<DisponibilidadEventoBean> findAll(DisponibilidadEventoBean bean) {
        List<Specification<DisponibilidadEvento>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new DisponibilidadEventoSpecification(criteria));
        }

        Specification<DisponibilidadEvento> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return DisponibilidadEventoMapper.INSTANCE.toBean(disponibilidadEventoRepository.findAll(filtros));
    }

    @Override
    public int setEstadoByIdDisponibilidadEvento(String estado, Integer id) {
        return disponibilidadEventoRepository.setEstadoByIdDisponibilidadEvento(estado, id);
    }

    @Override
    public int setEstadoByIdDisponibilidadAndFechaInicioAfter(String estado, Integer idDisponibilidad, Date fechaInicio) {
        return disponibilidadEventoRepository.setEstadoByIdDisponibilidadAndFechaInicioAfter(estado, idDisponibilidad, fechaInicio);
    }

    @Override
    public Long contarPorIdDisponibilidadEstado(Integer idDisponibilidad, String estado) {
        return disponibilidadEventoRepository.countByDisponibilidadIdDisponibilidadAndEstado(idDisponibilidad, estado);
    }

    @Override
    public DisponibilidadEventoBean save(DisponibilidadEventoBean bean) {
        DisponibilidadEvento model = DisponibilidadEventoMapper.INSTANCE.toModel(bean);
        return DisponibilidadEventoMapper.INSTANCE.toBean(disponibilidadEventoRepository.save(model));
    }

    @Override
    public void save(List<DisponibilidadEventoBean> bean) {
        List<DisponibilidadEvento> model = DisponibilidadEventoMapper.INSTANCE.toModel(bean);
        disponibilidadEventoRepository.save(model);
    }

    @Override
    public void delete(List<DisponibilidadEventoBean> bean) {
        List<DisponibilidadEvento> model = DisponibilidadEventoMapper.INSTANCE.toModel(bean);
        disponibilidadEventoRepository.delete(model);
    }

    @Override
    public DisponibilidadEventoBean findOne(Integer id) {
        return DisponibilidadEventoMapper.INSTANCE.toBean(disponibilidadEventoRepository.findOne(id));
    }

    @Override
    public List<DisponibilidadEventoBean> findByIdProfesionalAndFecha(Integer idProfesional, Date fecha1, Date fecha2) {
        return DisponibilidadEventoMapper.INSTANCE.toBean(disponibilidadEventoRepository.findByIdProfesionalAndFecha(idProfesional, fecha1, fecha2));
    }

    @Override
    public List<DisponibilidadEventoBean> findByIdProfesionalAndFechaAndIdDisponibilidadNot(Integer idProfesional, Date fecha1, Date fecha2, Integer idDisponibilidad) {
        return DisponibilidadEventoMapper.INSTANCE.toBean(disponibilidadEventoRepository.findByIdProfesionalAndFechaAndIdDisponibilidadNot(idProfesional, fecha1, fecha2, idDisponibilidad));
    }

    @Override
    public List<DisponibilidadEventoBean> findByIdSedeIdEspecialidadIdProfesionalAndFecha(Integer idSede, Integer idEspecialidad, Integer idProfesional, Date fecha1, Date fecha2) {
        return DisponibilidadEventoMapper.INSTANCE.toBean(disponibilidadEventoRepository.findByIdSedeIdEspecialidadIdProfesionalAndFecha(idSede, idEspecialidad, idProfesional, fecha1, fecha2));
    }

    public List<SearchCriteria> obtenerFiltros(DisponibilidadEventoBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        }
        if (bean.getFechaInicio() != null) {
            lista.add(new SearchCriteria("fechaInicio", ">", bean.getFechaInicio()));
        }
        if (bean.getFechaFin() != null) {
            lista.add(new SearchCriteria("fechaFin", "<", bean.getFechaFin()));
        }
        if (bean.getDisponibilidad() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getEstado())) {
                lista.add(new SearchCriteria("estado", ":", bean.getDisponibilidad().getEstado(), "disponibilidad"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getIdDisponibilidad())) {
                lista.add(new SearchCriteria("idDisponibilidad", ":", bean.getDisponibilidad().getIdDisponibilidad(), "disponibilidad"));
            }
            if (bean.getDisponibilidad().getSedeEspProfesional() != null) {
                if (bean.getDisponibilidad().getSedeEspProfesional().getId() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getId().getIdEspecialidad())) {
                        lista.add(new SearchCriteria("idEspecialidad", ":", bean.getDisponibilidad().getSedeEspProfesional().getId().getIdEspecialidad(), "sedeEspProfesional"));
                    }
                    if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getId().getIdProfesional())) {
                        lista.add(new SearchCriteria("idProfesional", ":", bean.getDisponibilidad().getSedeEspProfesional().getId().getIdProfesional(), "sedeEspProfesional"));
                    }
                    if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getId().getIdSede())) {
                        lista.add(new SearchCriteria("idSede", ":", bean.getDisponibilidad().getSedeEspProfesional().getId().getIdSede(), "sedeEspProfesional"));
                    }
                }
                if (bean.getDisponibilidad().getSedeEspProfesional().getProfesional() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getEstado())) {
                        lista.add(new SearchCriteria("estado", ":", bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getEstado(), "profesional"));
                    }
                    if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getCop())) {
                        lista.add(new SearchCriteria("cop", ":", bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getCop(), "profesional"));
                    }
                    if (bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getClinica() != null) {
                        if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getClinica().getIdClinica())) {
                            lista.add(new SearchCriteria("idClinica", ":", bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getClinica().getIdClinica(), "clinica"));
                        }
                    }
                    if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getIdProfesionals())) {
                        lista.add(new SearchCriteria("idProfesional", "in", bean.getDisponibilidad().getSedeEspProfesional().getProfesional().getIdProfesionals(), "profesional"));
                    }
                }
                if (bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad() != null) {
                    if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad().getEstado())) {
                        lista.add(new SearchCriteria("estado", ":", bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad().getEstado(), "sedeEspecialidad"));
                    }
                    if (bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad().getSede() != null) {
                        if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad().getSede().getEstado())) {
                            lista.add(new SearchCriteria("estado", ":", bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad().getSede().getEstado(), "sede"));
                        }
                    }
                    if (bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad().getEspecialidad() != null) {
                        if (!FuncionesUtil.esNuloOVacio(bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad().getEspecialidad().getEstado())) {
                            lista.add(new SearchCriteria("estado", ":", bean.getDisponibilidad().getSedeEspProfesional().getSedeEspecialidad().getEspecialidad().getEstado(), "especialidad"));
                        }
                    }
                }
            }
        }

        return lista;
    }

}
