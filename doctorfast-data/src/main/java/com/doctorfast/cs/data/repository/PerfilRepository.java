/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author MBS GROUP
 */
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    public Perfil findByNoPerfil(String noPerfil);
    
}
