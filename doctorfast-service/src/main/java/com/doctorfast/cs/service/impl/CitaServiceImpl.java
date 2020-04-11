/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.service.impl;

import com.doctorfast.cs.data.bean.CitaBean;
import com.doctorfast.cs.data.mapper.CitaMapper;
import com.doctorfast.cs.data.model.Cita;
import com.doctorfast.cs.data.repository.CitaRepository;
import com.doctorfast.cs.data.repository.PacienteRepository;
import com.doctorfast.cs.data.repository.PersonaUsuarioRepository;
import com.doctorfast.cs.data.specification.CitaSpecification;
import com.doctorfast.cs.data.specification.SearchCriteria;
import com.doctorfast.cs.service.CitaService;
import com.doctorfast.cs.service.exception.ApplicationException;
import com.doctorfast.cs.service.util.FuncionesUtil;
import java.util.ArrayList;
import java.util.Date;
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
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private PersonaUsuarioRepository personaUsuarioRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<CitaBean> findAll(CitaBean bean) {
        List<Specification<Cita>> lista = new ArrayList<>();

        for (SearchCriteria criteria : obtenerFiltros(bean)) {
            lista.add(new CitaSpecification(criteria));
        }

        Specification<Cita> filtros = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            filtros = Specifications.where(filtros).and(lista.get(i));
        }

        return CitaMapper.INSTANCE.toBean(citaRepository.findAll(filtros, new Sort(Sort.Direction.ASC, "fechaAtencion")));
    }

    @Override
    public int setEstadoByIdCita(String estado, Integer id) {
        return citaRepository.setEstadoByIdCita(estado, id);
    }

    @Override
    public int setEstadoCitaAndComentarioByIdCita(String estadoCita, String comentario, Integer id) {
        return citaRepository.setEstadoCitaAndComentarioByIdCita(estadoCita, comentario, id);
    }

    @Override
    public CitaBean save(CitaBean bean) throws ApplicationException {
        Cita model = CitaMapper.INSTANCE.toModel(bean);
        personaUsuarioRepository.save(model.getPaciente().getPersonaUsuario());
        pacienteRepository.save(model.getPaciente());
        if (bean.getIdCita() == null) {
            model.setCodigo(obtenerCodigoAlfaNumerico());
            try {
                return CitaMapper.INSTANCE.toBean(citaRepository.save(model));
            } catch (Exception e) {
                model.setCodigo(obtenerCodigoAlfaNumerico());
                try {
                    return CitaMapper.INSTANCE.toBean(citaRepository.save(model));
                } catch (Exception e1) {
                     throw new ApplicationException("Ocurrió un error al guardar la cita, vuelva a intentarlo.");
                }                
            }            
        }
        return CitaMapper.INSTANCE.toBean(citaRepository.save(model));
    }

    public String obtenerCodigoAlfaNumerico() {
        Cita cita = citaRepository.findTopByOrderByIdCitaDesc();
        if (cita == null || cita.getCodigo() == null) {
            return "AAA000";
        } else {
            return FuncionesUtil.obtenerSiguienteCodigoAlfaNumerico(cita.getCodigo());
        }
    }

    @Override
    public CitaBean findOne(Integer id) {
        return CitaMapper.INSTANCE.toBean(citaRepository.findOne(id));
    }

    @Override
    public List<CitaBean> findByIdProfesionalAndFecha(Integer idProfesional, Date fecha1, Date fecha2) {
        return CitaMapper.INSTANCE.toBean(citaRepository.findByIdProfesionalAndFecha(idProfesional, fecha1, fecha2));
    }

    @Override
    public List<CitaBean> findByIdProfesionalAndFechaAndIdCitaNot(Integer idProfesional, Date fecha1, Date fecha2, Integer idCita) {
        return CitaMapper.INSTANCE.toBean(citaRepository.findByIdProfesionalAndFechaAndIdCitaNot(idProfesional, fecha1, fecha2, idCita));
    }

    @Override
    public List<CitaBean> findByIdSedeIdEspecialidadIdProfesionalAndFecha(Integer idSede, Integer idEspecialidad, Integer idProfesional, Date fecha1, Date fecha2) {
        return CitaMapper.INSTANCE.toBean(citaRepository.findByIdSedeIdEspecialidadIdProfesionalAndFecha(idSede, idEspecialidad, idProfesional, fecha1, fecha2));
    }

    @Override
    public List<CitaBean> findByCodigoAndPacientePersonaUsuarioCorreo(String codigo, String correo) {
        return CitaMapper.INSTANCE.toBean(citaRepository.findByCodigoAndPacientePersonaUsuarioCorreo(codigo, correo));
    }

    public List<SearchCriteria> obtenerFiltros(CitaBean bean) {
        List<SearchCriteria> lista = new ArrayList<>();
        if (!FuncionesUtil.esNuloOVacio(bean.getEstado())) {
            lista.add(new SearchCriteria("estado", ":", bean.getEstado()));
        }
        if (!FuncionesUtil.esNuloOVacio(bean.getEstadoCita())) {
            lista.add(new SearchCriteria("estadoCita", ":", bean.getEstadoCita()));
        }
        if (bean.getFechaAtencion() != null) {
            lista.add(new SearchCriteria("fechaAtencion", ">", bean.getFechaAtencion()));
        }
        if (bean.getFechaAtencionFin() != null) {
            lista.add(new SearchCriteria("fechaAtencion", "<", bean.getFechaAtencionFin()));
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
