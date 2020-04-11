/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.specification;

import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.Disponibilidad;
import com.doctorfast.cs.data.model.DisponibilidadEvento;
import com.doctorfast.cs.data.model.Especialidad;
import com.doctorfast.cs.data.model.Profesional;
import com.doctorfast.cs.data.model.Sede;
import com.doctorfast.cs.data.model.SedeEspProfesional;
import com.doctorfast.cs.data.model.SedeEspecialidad;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author MBS GROUP
 */
public class DisponibilidadEventoSpecification implements Specification<DisponibilidadEvento> {

    private final SearchCriteria criteria;

    public DisponibilidadEventoSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<DisponibilidadEvento> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getEntityJoin() == null) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                    return builder.greaterThanOrEqualTo(
                        root.<Date>get(criteria.getKey()), (Date)criteria.getValue());
                } else {
                    return builder.greaterThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
                }                
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                    return builder.lessThanOrEqualTo(
                        root.<Date>get(criteria.getKey()), (Date)criteria.getValue());
                } else {
                    return builder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
                }
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return builder.like(
                            root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            }
        } else if (criteria.getEntityJoin().equalsIgnoreCase("disponibilidad")) {
            Join<DisponibilidadEvento, Disponibilidad> join = root.join("disponibilidad");
            return builder.equal(join.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspProfesional")) {
            Join<DisponibilidadEvento, Disponibilidad> join = root.join("disponibilidad");
            Join<Disponibilidad, SedeEspProfesional> join1 = join.join("sedeEspProfesional");
            return builder.equal(join1.get("id").get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspProfesionalId")) {
            Join<DisponibilidadEvento, Disponibilidad> join = root.join("disponibilidad");
            Join<Disponibilidad, SedeEspProfesional> join1 = join.join("sedeEspProfesional");
            return builder.equal(join1.get("id").get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("profesional")) {
            Join<DisponibilidadEvento, Disponibilidad> join = root.join("disponibilidad");
            Join<Disponibilidad, SedeEspProfesional> join1 = join.join("sedeEspProfesional");
            Join<SedeEspProfesional, Profesional> join2 = join1.join("profesional");
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return builder.equal(join2.get(criteria.getKey()), criteria.getValue());

            } else if (criteria.getOperation().equalsIgnoreCase("in")) {
                return builder.in(join2.get(criteria.getKey())).value((List<Integer>) criteria.getValue());
            }

        } else if (criteria.getEntityJoin().equalsIgnoreCase("clinica")) {
            Join<DisponibilidadEvento, Disponibilidad> join = root.join("disponibilidad");
            Join<Disponibilidad, SedeEspProfesional> join1 = join.join("sedeEspProfesional");
            Join<SedeEspProfesional, Profesional> join2 = join1.join("profesional");
            Join<Profesional, Clinica> join3 = join2.join("clinica");
            return builder.equal(join3.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspecialidad")) {
            Join<DisponibilidadEvento, Disponibilidad> join = root.join("disponibilidad");
            Join<Disponibilidad, SedeEspProfesional> join1 = join.join("sedeEspProfesional");
            Join<SedeEspProfesional, SedeEspecialidad> join2 = join1.join("sedeEspecialidad");
            return builder.equal(join2.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sede")) {
            Join<DisponibilidadEvento, Disponibilidad> join = root.join("disponibilidad");
            Join<Disponibilidad, SedeEspProfesional> join1 = join.join("sedeEspProfesional");
            Join<SedeEspProfesional, SedeEspecialidad> join2 = join1.join("sedeEspecialidad");
            Join<SedeEspecialidad, Sede> join3 = join2.join("sede");
            return builder.equal(join3.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("especialidad")) {
            Join<DisponibilidadEvento, Disponibilidad> join = root.join("disponibilidad");
            Join<Disponibilidad, SedeEspProfesional> join1 = join.join("sedeEspProfesional");
            Join<SedeEspProfesional, SedeEspecialidad> join2 = join1.join("sedeEspecialidad");
            Join<SedeEspecialidad, Especialidad> join3 = join2.join("especialidad");
            return builder.equal(join3.get(criteria.getKey()), criteria.getValue());

        }
        return null;
    }
    
}