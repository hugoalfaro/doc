/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.ProfesionalBean;
import com.doctorfast.cs.data.mapper.ProfesionalMapper;
import com.doctorfast.cs.data.model.Perfil;
import com.doctorfast.cs.data.model.Profesional;
import com.doctorfast.cs.data.repository.PerfilRepository;
import com.doctorfast.cs.data.repository.PersonaUsuarioRepository;
import com.doctorfast.cs.data.repository.ProfesionalRepository;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.data.specification.ProfesionalSpecification;
import com.doctorfast.cs.service.ProfesionalService;
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
public class ProfesionalServiceImpl implements ProfesionalService {

    @Autowired
    private ProfesionalRepository profesionalRepository;
    @Autowired
    private PersonaUsuarioRepository personaUsuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public List<ProfesionalBean> findAll(ProfesionalBean bean) {
        List<Specification<Profesional>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new ProfesionalSpecification(criteria));
        }

        Specification<Profesional> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return ProfesionalMapper.INSTANCE.toBean(profesionalRepository.findAll(filtros, new Sort(Sort.Direction.ASC, "personaUsuario.apellidoPaterno")));
    }

    @Override
    public int setEstadoByIdProfesional(String estado, Integer id) {
        Profesional model = profesionalRepository.findOne(id);
        personaUsuarioRepository.setEstadoByIdPersonaUsuario(estado, model.getPersonaUsuario().getIdPersonaUsuario());
        return profesionalRepository.setEstadoByIdProfesional(estado, id);
    }

    @Override
    public ProfesionalBean save(ProfesionalBean bean) throws ApplicationException {
        Profesional model = ProfesionalMapper.INSTANCE.toModel(bean);
        Perfil perfil = perfilRepository.findByNoPerfil("Profesional");
        if (!personaUsuarioRepository.findByEstadoNotAndDocumentoIdentidadAndPerfilIdPerfilNot(
                ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                bean.getPersonaUsuario().getDocumentoIdentidad(),
                perfil.getIdPerfil()).isEmpty()) {
            throw new ApplicationException("validation.documentoIdentidad.alreadyExists");
        }
        if (FuncionesUtil.esNuloOVacio(bean.getPersonaUsuario().getIdPersonaUsuario())) {
            if (!profesionalRepository.findByEstadoNotAndPersonaUsuarioDocumentoIdentidadAndClinicaIdClinica(
                    ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                    bean.getPersonaUsuario().getDocumentoIdentidad(),
                    bean.getClinica().getIdClinica()).isEmpty()) {
                throw new ApplicationException("validation.documentoIdentidad.alreadyExists");
            }
            if (!personaUsuarioRepository.findByEstadoNotAndDocumentoIdentidadNotAndCorreoAndPerfilIdPerfil(
                    ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                    bean.getPersonaUsuario().getDocumentoIdentidad(),
                    bean.getPersonaUsuario().getCorreo(),
                    perfil.getIdPerfil()).isEmpty()
                    || !personaUsuarioRepository.findByEstadoNotAndCorreoAndPerfilIdPerfilNot(
                            ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                            bean.getPersonaUsuario().getCorreo(),
                            perfil.getIdPerfil()).isEmpty()) {
                throw new ApplicationException("validation.correo.alreadyExists");
            }
        } else {
            if (!profesionalRepository.findByEstadoNotAndPersonaUsuarioDocumentoIdentidadAndClinicaIdClinicaAndPersonaUsuarioIdPersonaUsuarioNot(
                    ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                    bean.getPersonaUsuario().getDocumentoIdentidad(),
                    bean.getClinica().getIdClinica(),
                    bean.getPersonaUsuario().getIdPersonaUsuario()).isEmpty()) {
                throw new ApplicationException("validation.documentoIdentidad.alreadyExists");
            }
            if (!personaUsuarioRepository.findByEstadoNotAndDocumentoIdentidadNotAndCorreoAndPerfilIdPerfilAndIdPersonaUsuarioNot(
                    ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                    bean.getPersonaUsuario().getDocumentoIdentidad(),
                    bean.getPersonaUsuario().getCorreo(),
                    perfil.getIdPerfil(),
                    bean.getPersonaUsuario().getIdPersonaUsuario()).isEmpty()
                    || !personaUsuarioRepository.findByEstadoNotAndCorreoAndPerfilIdPerfilNotAndIdPersonaUsuarioNot(
                            ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                            bean.getPersonaUsuario().getCorreo(),
                            perfil.getIdPerfil(),
                            bean.getPersonaUsuario().getIdPersonaUsuario()).isEmpty()) {
                throw new ApplicationException("validation.correo.alreadyExists");
            }
        }
        model.getPersonaUsuario().setUsuario(model.getPersonaUsuario().getDocumentoIdentidad());
        model.getPersonaUsuario().setEstado(model.getEstado());
        if (model.getPersonaUsuario().getPerfil() == null) {
            model.getPersonaUsuario().setPerfil(perfil);
        }
        personaUsuarioRepository.save(model.getPersonaUsuario());
        return ProfesionalMapper.INSTANCE.toBean(profesionalRepository.save(model));
    }

    @Override
    public ProfesionalBean findOne(Integer id) {
        return ProfesionalMapper.INSTANCE.toBean(profesionalRepository.findOne(id));
    }

    @Override
    public List<ProfesionalBean> findClinicaByDocumentoIdentidad(String documentoIdentidad) {
        return ProfesionalMapper.INSTANCE.toBean(profesionalRepository.findClinicaByDocumentoIdentidad(documentoIdentidad));
    }

    public List<SearchCriteria> obtenerFiltros(ProfesionalBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getCop())) {
            lista.add(new SearchCriteria("cop", ":", bean.getCop()));
        }
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        } else {
            lista.add(new SearchCriteria("estado", "!", ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor()));
        }
        if (bean.getPersonaUsuario() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getPersonaUsuario().getDocumentoIdentidad())) {
                lista.add(new SearchCriteria("documentoIdentidad", ":", bean.getPersonaUsuario().getDocumentoIdentidad(), "personaUsuario"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getPersonaUsuario().getApellidoPaterno())) {
                lista.add(new SearchCriteria("apellidoPaterno", ":", bean.getPersonaUsuario().getApellidoPaterno(), "personaUsuario"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getPersonaUsuario().getApellidoMaterno())) {
                lista.add(new SearchCriteria("apellidoMaterno", ":", bean.getPersonaUsuario().getApellidoMaterno(), "personaUsuario"));
            }
            if (!FuncionesUtil.esNuloOVacio(bean.getPersonaUsuario().getNombre())) {
                lista.add(new SearchCriteria("nombre", ":", bean.getPersonaUsuario().getNombre(), "personaUsuario"));
            }
        }
        if (bean.getSedeEspProfesional() != null && bean.getSedeEspProfesional().getId() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getSedeEspProfesional().getId().getIdEspecialidad())) {
                lista.add(new SearchCriteria("idEspecialidad", ":", bean.getSedeEspProfesional().getId().getIdEspecialidad(), "sedeEspProfesionals"));
            }
        }
        if (bean.getClinica() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getClinica().getIdClinica())) {
                lista.add(new SearchCriteria("idClinica", ":", bean.getClinica().getIdClinica(), "clinica"));
            }
        }

        return lista;
    }

    @Override
    public ProfesionalBean findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario) {
        return ProfesionalMapper.INSTANCE.toBean(profesionalRepository.findByPersonaUsuarioIdPersonaUsuario(idPersonaUsuario));
    }

}
