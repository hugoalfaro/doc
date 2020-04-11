<%-- 
    Document   : index
    Created on : 19-may-2017, 22:25:02
    Author     : MBS GROUP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<t:privado>
    <spring:url value="/clinica/actualizarDatos.do" var="actualizarDatos" />

    <!-- PORTFOLIO SECTION 1 -->
    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-actualizar-datos" class="page-section">
                <div class="container">
                    <h1>Datos de Cl&iacute;nica</h1>
                    <div class="col-md-10 col-md-offset-1">
                        <div id="actualizarMensaje"></div>
                        <form:form id="actualizarForm" method="post" modelAttribute="clinicaBean" 
                                   action="${actualizarDatos}" class="form-horizontal form-label-left" enctype="multipart/form-data">
                            <form:hidden path="idClinica" />
                            <form:hidden path="logo" />
                            <div class="row">
                                <label class="control-label col-sm-3">RUC&nbsp;*</label>
                                <div class="col-sm-6 mb-23">
                                    <form:input path="ruc" type="text" class="form-control" maxlength="11" />
                                </div>
                            </div>
                            <div class="row">
                                <label class="control-label col-sm-3">Nombre Comercial&nbsp;*</label>
                                <div class="col-sm-6 mb-23">
                                    <form:input path="nombreComercial" type="text" class="form-control" maxlength="50" />
                                </div>
                            </div>
                            <div class="row">
                                <label class="control-label col-sm-3">Nombre Abreviado&nbsp;*</label>
                                <div class="col-sm-6 mb-23">
                                    <form:input path="nombreAbreviado" type="text" class="form-control" maxlength="50" />
                                </div>
                            </div>
                            <div class="row">
                                <label class="control-label col-sm-3">Direcci&oacute;n Comercial&nbsp;*</label>
                                <div class="col-sm-6 mb-23">
                                    <form:input path="direccionComercial" type="text" class="form-control" maxlength="50" />
                                </div>
                            </div>
                            <div class="row">
                                <label class="control-label col-sm-3">Tiempo de cita por defecto&nbsp;*</label>
                                <div class="col-sm-6 mb-23">
                                    <div class="select-styled select-tiempoCita">
                                        <form:select path="tiempoCita" class="form-control select-tiempoCita">
                                            <form:option value="30" label="30 minutos" />
                                            <form:option value="60" label="1 hora" />
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <label class="control-label col-sm-3">Logo</label>
                                <div class="col-sm-6 mb-23">
                                    <input type="file" id="archivo"  name="archivo" />
                                </div>
                                <img src="https://s3.us-east-2.amazonaws.com/mbs-dfa-web-data/<c:url value="${clinicaBean.logo}"/>" class="logo-img" alt="Logo">
                            </div>
                            <div class="col-sm-6 mb-23">
                                <div class="text-right text-xxs-center">
                                    <input value="Guardar" class="button medium rounded blue font-open-sans mt-40" data-loading-text="Loading..." type="submit">
                                </div>
                            </div>
                        </form:form>

                    </div>
                </div>
            </div>

        </div>
    </div>

    <script type="text/javascript">
        $(function () {
            $('#actualizarForm').validate({
                rules: {
                    ruc: {
                        required: true,
                        digits: true,
                        minlength: 11,
                        maxlength: 11
                    },
                    nombreComercial: {
                        required: true,
                        maxlength: 50
                    },
                    nombreAbreviado: {
                        required: true,
                        maxlength: 50
                    },
                    direccionComercial: {
                        required: true,
                        maxlength: 50
                    }
                }
            });
        });
    </script>    

</t:privado>
