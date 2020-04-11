/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.data.specification;

/**
 *
 * @author MBS GROUP
 */
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
    private String entityJoin;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String key, String operation, Object value, String entityJoin) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.entityJoin = entityJoin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getEntityJoin() {
        return entityJoin;
    }

    public void setEntityJoin(String entityJoin) {
        this.entityJoin = entityJoin;
    }

}
