/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.repository;

import com.doctorfast.cs.data.model.Ubigeo;
import com.doctorfast.cs.data.model.UbigeoId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author MBS GROUP
 */
public interface UbigeoRepository extends JpaRepository<Ubigeo, UbigeoId> {

    @Query("select o from Ubigeo o "
            + "join o.id i "
            + "where i.coProvincia = 0 and i.coDistrito = 0 and o.estado = '1'  ")
    List<Ubigeo> obtenerDepartamentos();

    @Query("select o from Ubigeo o "
            + "join o.id i "
            + "where i.coDepartamento = :coDepartamento and i.coProvincia != 0 and i.coDistrito = 0 and o.estado = '1' ")
    List<Ubigeo> obtenerProvincias(@Param(value = "coDepartamento") Integer coDepartamento);

    @Query("select o from Ubigeo o "
            + "join o.id i "
            + "where i.coDepartamento = :coDepartamento and i.coProvincia = :coProvincia and i.coDistrito != 0 and o.estado = '1' ")
    List<Ubigeo> obtenerDistritos(@Param(value = "coDepartamento") Integer coDepartamento, @Param(value = "coProvincia") Integer coProvincia);

}
