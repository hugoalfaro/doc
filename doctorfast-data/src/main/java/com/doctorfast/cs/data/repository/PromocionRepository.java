/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Promocion;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author MBS GROUP
 */
public interface PromocionRepository extends JpaRepository<Promocion, Integer> {

    @Query("select o from Promocion o "
            + "join o.sedeEspecialidad se "
            + "join se.sede s "
            + "join se.especialidad e "
            + "where o.estado = :estado and o.fechaFin >= :fecha and se.estado = '1' and s.estado = '1' and e.estado = '1' ")
    List<Promocion> findByEstadoAndFechaFinAfterOrderByOrdenAsc(@Param("estado") String estado, @Param("fecha") Date fecha);

}
