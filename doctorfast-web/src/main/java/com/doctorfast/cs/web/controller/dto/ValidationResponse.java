/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.controller.dto;

import java.util.List;

/**
 *
 * @author MBS GROUP
 */
public class ValidationResponse {

    private String status;
    private List<ErrorMessage> errorMessageList;

    public ValidationResponse() {
    }

    public ValidationResponse(String status) {
        this.status = status;
    }

    public ValidationResponse(String status, List<ErrorMessage> errorMessageList) {
        this.status = status;
        this.errorMessageList = errorMessageList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ErrorMessage> getErrorMessageList() {
        return errorMessageList;
    }

    public void setErrorMessageList(List<ErrorMessage> errorMessageList) {
        this.errorMessageList = errorMessageList;
    }

}
