/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.specification;

import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.PersonaUsuario;
import com.doctorfast.cs.data.model.Profesional;
import com.doctorfast.cs.data.model.SedeEspProfesional;
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
public class ProfesionalSpecification implements Specification<Profesional> {

    private final SearchCriteria criteria;

    public ProfesionalSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Profesional> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getEntityJoin() == null) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                return builder.greaterThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                return builder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return builder.like(
                            root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            } else if (criteria.getOperation().equalsIgnoreCase("!")) {
                return builder.notEqual(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            }
        } else if (criteria.getEntityJoin().equalsIgnoreCase("personaUsuario")) {
            Join<Profesional, PersonaUsuario> join = root.join("personaUsuario");
            if (join.get(criteria.getKey()).getJavaType() == String.class) {
//                if (criteria.getKey().equals("nombre")) {
//                    Expression<String> exp1 = builder.concat(join.<String>get("apellidoPaterno"), " ");
//                    exp1 = builder.concat(exp1, join.<String>get("apellidoMaterno"));
//                    exp1 = builder.concat(exp1, " ");
//                    exp1 = builder.concat(exp1, join.<String>get("nombre"));
//                    return builder.like(exp1, "%" + criteria.getValue() + "%");
//                }
                return builder.like(
                        join.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(join.get(criteria.getKey()), criteria.getValue());
            }

        } else if (criteria.getEntityJoin().equalsIgnoreCase("sedeEspProfesionals")) {
            Join<Profesional, SedeEspProfesional> join = root.join("sedeEspProfesionals");
            return builder.equal(join.get("id").get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("clinica")) {
            Join<Profesional, Clinica> join = root.join("clinica");
            return builder.equal(join.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }

}
