/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.SedeBean;
import com.doctorfast.cs.data.mapper.SedeMapper;
import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.Sede;
import com.doctorfast.cs.data.repository.SedeRepository;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.data.specification.SedeSpecification;
import com.doctorfast.cs.service.SedeService;
import com.doctorfast.cs.service.exception.ApplicationException;
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
public class SedeServiceImpl implements SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    @Override
    public List<SedeBean> findAll(SedeBean bean) {
        List<Specification<Sede>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new SedeSpecification(criteria));
        }

        Specification<Sede> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return SedeMapper.INSTANCE.toBean(sedeRepository.findAll(filtros, new Sort(Sort.Direction.ASC, "nombre")));
    }

    @Override
    public int setEstadoByIdSede(String estado, Integer id) {
        return sedeRepository.setEstadoByIdSede(estado, id);
    }

    @Override
    public SedeBean save(SedeBean bean, Integer idClinica) throws ApplicationException {
        Sede model = SedeMapper.INSTANCE.toModel(bean);
        if(FuncionesUtil.esNuloOVacio(bean.getIdSede())){
            if(sedeRepository.findByNombreAndClinicaIdClinicaAndUbigeoIdCoDepartamentoAndUbigeoIdCoProvinciaAndUbigeoIdCoDistrito(bean.getNombre(), idClinica, bean.getUbigeo().getId().getCoDepartamento(), bean.getUbigeo().getId().getCoProvincia(), bean.getUbigeo().getId().getCoDistrito())!=null){
                throw new ApplicationException("validation.nombre.alreadyExists");
            }
        }else{
            if(sedeRepository.findByNombreAndClinicaIdClinicaAndUbigeoIdCoDepartamentoAndUbigeoIdCoProvinciaAndUbigeoIdCoDistritoAndIdSedeNot(bean.getNombre(), idClinica, bean.getUbigeo().getId().getCoDepartamento(), bean.getUbigeo().getId().getCoProvincia(), bean.getUbigeo().getId().getCoDistrito(), bean.getIdSede())!=null){
                throw new ApplicationException("validation.nombre.alreadyExists");
            }
        }
        model.setClinica(new Clinica(idClinica));
        if(FuncionesUtil.esNuloOVacio(bean.getCodigo())){
            Integer codigoSiguiente = sedeRepository.obtenerCodigoSiguiente(idClinica);
            model.setCodigo((codigoSiguiente==null)?1:codigoSiguiente);
        }
        return SedeMapper.INSTANCE.toBean(sedeRepository.save(model));
    }

    @Override
    public SedeBean findOne(Integer id) {
        return SedeMapper.INSTANCE.toBean(sedeRepository.findOne(id));
    }

    public List<SearchCriteria> obtenerFiltros(SedeBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getCodigo())) {
            lista.add(new SearchCriteria("codigo", ":", bean.getCodigo()));
        }
        if (!FuncionesUtil.esNuloOVacio(bean.getNombre())) {
            lista.add(new SearchCriteria("nombre", ":", bean.getNombre()));
        }
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        }else{
            lista.add(new SearchCriteria("estado", "!", ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor()));
        }
        if (bean.getUbigeo() != null && bean.getUbigeo().getId() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getUbigeo().getId().getCoDepartamento())) {
                lista.add(new SearchCriteria("coDepartamento", ":", bean.getUbigeo().getId().getCoDepartamento(), "ubigeo"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getUbigeo().getId().getCoProvincia())) {
                lista.add(new SearchCriteria("coProvincia", ":", bean.getUbigeo().getId().getCoProvincia(), "ubigeo"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getUbigeo().getId().getCoDistrito())) {
                lista.add(new SearchCriteria("coDistrito", ":", bean.getUbigeo().getId().getCoDistrito(), "ubigeo"));
            }
        }
        if (bean.getClinica() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getClinica().getIdClinica())) {
                lista.add(new SearchCriteria("idClinica", ":", bean.getClinica().getIdClinica(), "clinica"));
            }
        }

        return lista;
    }

}
