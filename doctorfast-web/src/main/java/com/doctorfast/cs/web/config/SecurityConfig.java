/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author MBS GROUP
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new Md5PasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/resources/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/consultarEspecialidad").permitAll()
                .antMatchers("/administrador").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/recuperarContrasena").permitAll()
                .antMatchers("/recuperarUsuario").permitAll()
                .antMatchers("/citas/crearCitaDePromocion").permitAll()
                .antMatchers("/citas/crearCita").permitAll()
                .antMatchers("/citas/consultar").permitAll()
                .antMatchers("/rest/ubigeo/obtenerProvincias/*").permitAll()
                .antMatchers("/rest/ubigeo/obtenerDistritos/*/*").permitAll()
                .antMatchers("/rest/sedeEspecialidad/obtenerSedeEspecialidadesPublico").permitAll()
                .antMatchers("/rest/disponibilidad/obtenerDisponibilidadesPublico").permitAll()
                .antMatchers("/rest/cita/obtenerCitasPublico").permitAll()
                .antMatchers("/rest/cita/agregarPublico").permitAll()
                .antMatchers("/inicio").hasAnyRole("Administrador", "Asistente", "Profesional")
                .antMatchers("/rest/disponibilidad/obtenerDisponibilidades").hasAnyRole("Administrador", "Asistente", "Profesional")
                .antMatchers("/rest/cita/obtenerCitas").hasAnyRole("Administrador", "Asistente", "Profesional")
                .antMatchers("/rest/sedeEspProfesional/obtenerEspecialidadesDeProfesional").hasAnyRole("Administrador", "Asistente")
                .antMatchers("/rest/sedeEspProfesional/obtenerSedesDeEspecialidadProfesional").hasAnyRole("Administrador", "Asistente")
                .antMatchers("/rest/sedeEspProfesional/obtenerProfesionalesDeSedeEspecialidad").hasAnyRole("Administrador", "Asistente")
                .antMatchers("/rest/sedeEspecialidad/obtenerSedeEspecialidades").hasAnyRole("Administrador", "Asistente")
                .antMatchers("/rest/sedeEspSubesp/obtenerSedeEspSubesps/*/*").hasAnyRole("Administrador", "Asistente")
                .antMatchers("/rest/cita/agregarEditar").hasAnyRole("Administrador", "Asistente")
                .antMatchers("/citas").hasAnyRole("Administrador", "Asistente")
                .antMatchers("/seguridad").hasAnyRole("Asistente", "Profesional")
                .antMatchers("/rest/cita/editar").hasAnyRole("Asistente", "Profesional")
                .antMatchers("/rest/sede/obtenerSedesDeClinica/*").hasRole("Profesional")
                .antMatchers("/citas/calendario").hasRole("Profesional")
                .anyRequest().hasRole("Administrador")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/forbidden")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/administrador")
                .and()
                .formLogin()
                .usernameParameter("usuario").passwordParameter("clave")
                .loginProcessingUrl("/administrador")
                .defaultSuccessUrl("/inicio")
                .failureUrl("/administrador?authentication_error=true")
                .loginPage("/administrador");
    }
}
