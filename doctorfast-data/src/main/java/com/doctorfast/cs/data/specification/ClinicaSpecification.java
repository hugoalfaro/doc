/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.specification;

import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.PersonaUsuario;
import com.doctorfast.cs.data.model.Profesional;
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
public class ClinicaSpecification implements Specification<Clinica> {

    private final SearchCriteria criteria;

    public ClinicaSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Clinica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getEntityJoin() == null) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                return builder.greaterThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                return builder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
            
        } else if (criteria.getEntityJoin().equalsIgnoreCase("profesional")) {
            Join<Clinica, Profesional> join = root.join("profesionals");
            return builder.equal(join.get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("personaUsuario")) {
            Join<Clinica, Profesional> join = root.join("profesionals");
            Join<Profesional, PersonaUsuario> join1 = join.join("personaUsuario");
            return builder.equal(join1.get(criteria.getKey()), criteria.getValue());

        }
        return null;
    }
    
}
