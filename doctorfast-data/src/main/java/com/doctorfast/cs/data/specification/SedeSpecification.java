/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.specification;

import com.doctorfast.cs.data.model.Clinica;
import com.doctorfast.cs.data.model.Sede;
import com.doctorfast.cs.data.model.Ubigeo;
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
public class SedeSpecification implements Specification<Sede> {

    private final SearchCriteria criteria;

    public SedeSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Sede> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

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
        } else if (criteria.getEntityJoin().equalsIgnoreCase("ubigeo")) {
            Join<Sede, Ubigeo> join = root.join("ubigeo");
            return builder.equal(join.get("id").get(criteria.getKey()), criteria.getValue());

        } else if (criteria.getEntityJoin().equalsIgnoreCase("clinica")) {
            Join<Sede, Clinica> join = root.join("clinica");
            return builder.equal(join.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }
}
