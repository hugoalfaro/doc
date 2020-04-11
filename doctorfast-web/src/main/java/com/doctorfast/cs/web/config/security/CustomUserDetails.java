/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.config.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author MBS GROUP
 */
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean accountNonLocked;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Integer idClinica;
    private final String nombreClinica;
    private final Integer idPersonaUsuario;
    private final String documentoIdentidad;
    private final String nombrePersona;
    private final List<Integer> idsProfesionales;

    public CustomUserDetails(Integer idClinica, String nombreClinica, Integer idPersonaUsuario, String documentoIdentidad, String nombrePersona, List<Integer> idsProfesionales, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        this.idClinica = idClinica;
        this.nombreClinica = nombreClinica;
        this.idPersonaUsuario = idPersonaUsuario;
        this.documentoIdentidad = documentoIdentidad;
        this.nombrePersona = nombrePersona;
        this.idsProfesionales = idsProfesionales;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Integer getIdClinica() {
        return idClinica;
    }

    public String getNombreClinica() {
        return nombreClinica;
    }

    public Integer getIdPersonaUsuario() {
        return idPersonaUsuario;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public List<Integer> getIdsProfesionales() {
        return idsProfesionales;
    }

}
