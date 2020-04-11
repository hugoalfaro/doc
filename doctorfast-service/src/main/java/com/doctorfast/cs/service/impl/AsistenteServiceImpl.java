/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.AsistenteBean;
import com.doctorfast.cs.data.mapper.AsistenteMapper;
import com.doctorfast.cs.data.model.Asistente;
import com.doctorfast.cs.data.repository.AdministradorRepository;
import com.doctorfast.cs.data.repository.PerfilRepository;
import com.doctorfast.cs.data.repository.PersonaUsuarioRepository;
import com.doctorfast.cs.data.repository.AsistenteRepository;
import com.doctorfast.cs.data.repository.ProfesionalRepository;
import com.doctorfast.cs.data.specification.AsistenteSpecification;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.service.AsistenteService;
import com.doctorfast.cs.service.exception.ApplicationException;
import com.doctorfast.cs.service.util.ConstantesUtil;
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
public class AsistenteServiceImpl implements AsistenteService {

    @Autowired
    private AsistenteRepository asistenteRepository;
    @Autowired
    private PersonaUsuarioRepository personaUsuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private ProfesionalRepository profesionalRepository;
    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public List<AsistenteBean> findAll(AsistenteBean bean) {
        List<Specification<Asistente>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new AsistenteSpecification(criteria));
        }

        Specification<Asistente> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return AsistenteMapper.INSTANCE.toBean(asistenteRepository.findAll(filtros));
    }

    @Override
    public int setEstadoByIdAsistente(String estado, Integer id) {
        Asistente model = asistenteRepository.findOne(id);
        personaUsuarioRepository.setEstadoByIdPersonaUsuario(estado, model.getPersonaUsuario().getIdPersonaUsuario());
        return asistenteRepository.setEstadoByIdAsistente(estado, id);
    }

    @Override
    public AsistenteBean save(AsistenteBean bean) throws ApplicationException {
        Asistente model = AsistenteMapper.INSTANCE.toModel(bean);
        if (FuncionesUtil.esNuloOVacio(bean.getPersonaUsuario().getIdPersonaUsuario())) {
            if (!personaUsuarioRepository.findByEstadoNotAndDocumentoIdentidad(
                    ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                    bean.getPersonaUsuario().getDocumentoIdentidad()).isEmpty()) {
                throw new ApplicationException("validation.documentoIdentidad.alreadyExists");
            }
            if (!personaUsuarioRepository.findByEstadoNotAndCorreo(
                    ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                    bean.getPersonaUsuario().getCorreo()).isEmpty()) {
                throw new ApplicationException("validation.correo.alreadyExists");
            }
        } else {
            if (!personaUsuarioRepository.findByEstadoNotAndDocumentoIdentidadAndIdPersonaUsuarioNot(
                    ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                    bean.getPersonaUsuario().getDocumentoIdentidad(),
                    bean.getPersonaUsuario().getIdPersonaUsuario()).isEmpty()) {
                throw new ApplicationException("validation.documentoIdentidad.alreadyExists");
            }
            if (!personaUsuarioRepository.findByEstadoNotAndCorreoAndIdPersonaUsuarioNot(
                    ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor(),
                    bean.getPersonaUsuario().getCorreo(),
                    bean.getPersonaUsuario().getIdPersonaUsuario()).isEmpty()) {
                throw new ApplicationException("validation.correo.alreadyExists");
            }
        }
        model.getPersonaUsuario().setUsuario(model.getPersonaUsuario().getDocumentoIdentidad());
        model.getPersonaUsuario().setEstado(model.getEstado());
        if (model.getPersonaUsuario().getPerfil() == null) {
            model.getPersonaUsuario().setPerfil(perfilRepository.findByNoPerfil("Asistente"));
        }
        personaUsuarioRepository.save(model.getPersonaUsuario());
        return AsistenteMapper.INSTANCE.toBean(asistenteRepository.save(model));
    }

    @Override
    public AsistenteBean findOne(Integer id) {
        return AsistenteMapper.INSTANCE.toBean(asistenteRepository.findOne(id));
    }

    public List<SearchCriteria> obtenerFiltros(AsistenteBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
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
        if (bean.getClinica() != null) {
            if (!FuncionesUtil.esNuloOVacio(bean.getClinica().getIdClinica())) {
                lista.add(new SearchCriteria("idClinica", ":", bean.getClinica().getIdClinica(), "clinica"));
            }
        }

        return lista;
    }

    @Override
    public AsistenteBean findByPersonaUsuarioIdPersonaUsuario(Integer idPersonaUsuario) {
        return AsistenteMapper.INSTANCE.toBean(asistenteRepository.findByPersonaUsuarioIdPersonaUsuario(idPersonaUsuario));
    }

}
