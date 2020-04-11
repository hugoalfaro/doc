/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.specification;

import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.Especialidad;
import com.doctorfast.cs.data.model.Profesional;
import com.doctorfast.cs.data.model.Sede;
import com.doctorfast.cs.data.model.SedeEspProfesional;
import com.doctorfast.cs.data.model.SedeEspecialidad;
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
public class SedeEspProfesionalSpecification implements Specification<SedeEspProfesional> {

    private final SearchCriteria criteria;

    public SedeEspProfesionalSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<SedeEspProfesional> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getEntityJoin() == null) {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspProfesionalId")) {
            return builder.equal(root.get("id").get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("profesional")) {
            Join<SedeEspProfesional, Profesional> join = root.join("profesional");
            return builder.equal(join.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("clinica")) {
            Join<SedeEspProfesional, Profesional> join = root.join("profesional");
            Join<Profesional, Clinica> join1 = join.join("clinica");
            return builder.equal(join1.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspecialidad")) {
            Join<SedeEspProfesional, SedeEspecialidad> join = root.join("sedeEspecialidad");
            return builder.equal(join.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sede")) {
            Join<SedeEspProfesional, SedeEspecialidad> join = root.join("sedeEspecialidad");
            Join<SedeEspecialidad, Sede> join1 = join.join("sede");
            return builder.equal(join1.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("especialidad")) {
            Join<SedeEspProfesional, SedeEspecialidad> join = root.join("sedeEspecialidad");
            Join<SedeEspecialidad, Especialidad> join1 = join.join("especialidad");
            return builder.equal(join1.get(criteria.getKey()), criteria.getValue());

        }
        return null;
    }

}
