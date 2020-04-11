/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.specification;

import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.Cita;
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
public class CitaSpecification implements Specification<Cita> {

    private final SearchCriteria criteria;

    public CitaSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Cita> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

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
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
            
        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspProfesional")) {
            Join<Cita, SedeEspProfesional> join = root.join("sedeEspProfesional");
            return builder.equal(join.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspProfesionalId")) {
            Join<Cita, SedeEspProfesional> join = root.join("sedeEspProfesional");
            return builder.equal(join.get("id").get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("profesional")) {
            Join<Cita, SedeEspProfesional> join = root.join("sedeEspProfesional");
            Join<SedeEspProfesional, Profesional> join1 = join.join("profesional");
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return builder.equal(join1.get(criteria.getKey()), criteria.getValue());
                
            } else if (criteria.getOperation().equalsIgnoreCase("in")) {
                return builder.in(join1.get(criteria.getKey())).value((List<Integer>)criteria.getValue());
            }

        } else if (criteria.getEntityJoin().equalsIgnoreCase("clinica")) {
            Join<Cita, SedeEspProfesional> join = root.join("sedeEspProfesional");
            Join<SedeEspProfesional, Profesional> join1 = join.join("profesional");
            Join<Profesional, Clinica> join2 = join1.join("clinica");
            return builder.equal(join2.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspecialidad")) {
            Join<Cita, SedeEspProfesional> join = root.join("sedeEspProfesional");
            Join<SedeEspProfesional, SedeEspecialidad> join1 = join.join("sedeEspecialidad");
            return builder.equal(join1.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sede")) {
            Join<Cita, SedeEspProfesional> join = root.join("sedeEspProfesional");
            Join<SedeEspProfesional, SedeEspecialidad> join1 = join.join("sedeEspecialidad");
            Join<SedeEspecialidad, Sede> join2 = join1.join("sede");
            return builder.equal(join2.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("especialidad")) {
            Join<Cita, SedeEspProfesional> join = root.join("sedeEspProfesional");
            Join<SedeEspProfesional, SedeEspecialidad> join1 = join.join("sedeEspecialidad");
            Join<SedeEspecialidad, Especialidad> join2 = join1.join("especialidad");
            return builder.equal(join2.get(criteria.getKey()), criteria.getValue());

        }
        return null;
    }
    
}
