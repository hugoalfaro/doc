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
    <spring:url value="/seguridad" var="actualizarDatos" />

    <!-- PORTFOLIO SECTION 1 -->
    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-actualizar-datos" class="page-section">
                <div class="container">
                    <h1>Seguridad</h1>
                    <div class="col-md-10 col-md-offset-1">
                        <div id="actualizarMensaje">
                            <c:if test="${ok == 'true'}">
                                <div class="alert alert-info animated shake" id="alerta">
                                    La operación se realizó con éxito.
                                    <a href="javascript:void(0)" class="close" onclick="cerrarAlerta(this)" aria-label="close">×</a>
                                </div>
                            </c:if>
                            <c:if test="${not empty ok && ok == 'false'}">
                                <div class="alert alert-danger animated shake" id="alerta">
                                    No se pudo realizar la operación.
                                    <a href="javascript:void(0)" class="close" onclick="cerrarAlerta(this)" aria-label="close">×</a>
                                </div>
                            </c:if>
                        </div>

                        <form:form id="seguridadForm" method="post" modelAttribute="personaUsuarioBean" 
                                   action="${actualizarDatos}" class="form-horizontal form-label-left">
                            <div class="row">
                                <label class="control-label col-sm-3">Clave Nueva&nbsp;*</label>
                                <div class="col-sm-6 mb-23">
                                    <form:password path="clave" class="form-control" maxlength="50" placeholder="Min. 8 carácteres" />
                                </div>
                            </div>
                            <div class="row">
                                <label class="control-label col-sm-3">Repetir Clave Nueva&nbsp;*</label>
                                <div class="col-sm-6 mb-23">
                                    <form:password path="repetirClave" class="form-control" maxlength="50" />
                                </div>
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
            $('#seguridadForm').validate({
                rules: {
                    clave: {
                        required: true,
                        minlength: 8,
                        maxlength: 50
                    },
                    repetirClave: {
                        required: true,
                        minlength: 8,
                        maxlength: 50,
                        equalTo: "#clave"
                    }
                }
            });
            $('#actualizarMensaje').fadeTo(5000, 500).slideUp(500, function () {
                $('#actualizarMensaje').slideUp(500);
            });
        });
    </script> 

</t:privado>
