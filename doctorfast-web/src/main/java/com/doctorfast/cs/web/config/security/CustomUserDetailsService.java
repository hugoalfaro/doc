/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.config.security;

import com.doctorfast.cs.data.model.Administrador;
import com.doctorfast.cs.data.model.Asistente;
import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.PersonaUsuario;
import com.doctorfast.cs.data.model.Profesional;
import com.doctorfast.cs.data.repository.AdministradorRepository;
import com.doctorfast.cs.data.repository.AsistenteRepository;
import com.doctorfast.cs.data.repository.PersonaUsuarioRepository;
import com.doctorfast.cs.data.repository.ProfesionalRepository;
import com.doctorfast.cs.web.util.ConstantesUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MBS GROUP
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonaUsuarioRepository personaUsuarioRepository;
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private AsistenteRepository asistenteRepository;
    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        usuario = usuario.trim();
        List<PersonaUsuario> usuarios = personaUsuarioRepository.findByUsuarioAndEstado(usuario, ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
        List<GrantedAuthority> permisos = new ArrayList<>();
        boolean usuarioActivo = false;
        Integer idClinica = null;
        String nombreClinica = null;
        List<Integer> idsProfesionales = new ArrayList<>();
        PersonaUsuario personaUsuario = new PersonaUsuario();

        if (usuarios.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        } else {
            int i = 0;
            boolean noEncontro = true;
            while (i < usuarios.size() && !usuarioActivo) {
                personaUsuario = usuarios.get(i);

                permisos = new ArrayList<>();
                permisos.add(new SimpleGrantedAuthority("ROLE_" + personaUsuario.getPerfil().getNoPerfil()));
                usuarioActivo = false;
                idClinica = null;
                nombreClinica = null;
                idsProfesionales = new ArrayList<>();
                if (personaUsuario.getPerfil().getNoPerfil().equalsIgnoreCase("administrador")) {
                    Clinica clinica = administradorRepository.findClinicaByIdPersonaUsuario(personaUsuario.getIdPersonaUsuario());
                    Administrador administrador = administradorRepository.findByPersonaUsuarioIdPersonaUsuario(personaUsuario.getIdPersonaUsuario());
                    if (administrador != null && !administrador.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor())) {
                        usuarioActivo = administrador.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
                        idClinica = clinica.getIdClinica();
                        nombreClinica = clinica.getNombreAbreviado();
                        noEncontro = false;
                    }
                }
                if (personaUsuario.getPerfil().getNoPerfil().equalsIgnoreCase("asistente")) {
                    Clinica clinica = asistenteRepository.findClinicaByIdPersonaUsuario(personaUsuario.getIdPersonaUsuario());
                    Asistente asistente = asistenteRepository.findByPersonaUsuarioIdPersonaUsuario(personaUsuario.getIdPersonaUsuario());
                    if (asistente != null && !asistente.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor())) {
                        usuarioActivo = asistente.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
                        idClinica = clinica.getIdClinica();
                        nombreClinica = clinica.getNombreAbreviado();
                        noEncontro = false;
                    }
                }
                if (personaUsuario.getPerfil().getNoPerfil().equalsIgnoreCase("profesional")) {
                    Profesional profesional = profesionalRepository.findByPersonaUsuarioIdPersonaUsuario(personaUsuario.getIdPersonaUsuario());
                    if (profesional != null && !profesional.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ELIMINADO.getValor())) {
                        usuarioActivo = profesional.getEstado().equals(ConstantesUtil.ESTADO_REGISTRO.ACTIVO.getValor());
                        idsProfesionales = profesionalRepository.findIdProfesionalByDocumentoIdentidad(personaUsuario.getDocumentoIdentidad());
                        noEncontro = false;
                    }
                }
                i++;
            }
            if (noEncontro) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
            return new CustomUserDetails(
                    idClinica,
                    nombreClinica,
                    personaUsuario.getIdPersonaUsuario(),
                    personaUsuario.getDocumentoIdentidad(),
                    personaUsuario.getApellidoPaterno() + " " + personaUsuario.getApellidoMaterno() + " " + personaUsuario.getNombre(),
                    idsProfesionales,
                    personaUsuario.getUsuario(),
                    personaUsuario.getClave(),
                    usuarioActivo,
                    true, true, true, permisos);
        }
    }
}
