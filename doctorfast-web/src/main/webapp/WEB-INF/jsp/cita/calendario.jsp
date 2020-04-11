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
    <spring:url value="/rest/cita/obtenerCitas" var="consultar" />
    <spring:url value="/rest/cita/editar" var="agregarEditar" />

    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-consultar" class="page-section">
                <div class="container">
                    <h1>Calendario de Profesional</h1>
                    <div id="gestionarMensaje"></div>
                    <form:form id="consultarForm" method="post" modelAttribute="filtroForm" action="${consultar}" >
                        <div class="form-group col-sm-3">
                            <div class="select-styled w-100">
                                <form:select path="idClinica" class="form-control w-100">
                                    <form:option value="0" label="Todas las Clinicas" />
                                    <c:forEach var = "o" items = "${clinicas}">
                                        <form:option value="${o.clinica.idClinica}" label="${o.clinica.nombreAbreviado}" data-profesional="${o.idProfesional}" />
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group col-sm-3">
                            <div class="select-styled w-100">
                                <form:select path="idSede" class="form-control w-100" placeholder="Sede">
                                    <form:option value="0" label="Todas las Sedes" />
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group col-sm-3">
                            <div class="select-styled select-estado">
                                <form:select path="estado" class="form-control select-estado">
                                    <form:option value="" label="Todos los estados" />
                                    <form:option value="1" label="Registrado" />
                                    <form:option value="2" label="Confirmado" />
                                    <form:option value="3" label="En Atención" />
                                    <form:option value="4" label="Finalizado" />
                                </form:select>
                            </div>
                        </div>
                        <div class="col-sm-12 mb-23  text-right text-xxs-center">
                            <input value="Buscar" class="button medium rounded blue font-open-sans" data-loading-text="Loading..." type="submit">
                            <input value="Limpiar" class="button medium rounded gray font-open-sans" id="limpiarBtn" type="button">
                        </div>
                    </form:form>

                    <!-- Modal -->
                    <div id="agregarEditarModal" class="modal fade bs-example-modal-lg bootstrap-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-body">
                                <div class="modal-content">
                                    <div class="modal-header bg-gray-dark">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myLargeModalLabel">Editar Cita</h4>
                                    </div>
                                    <div class="modal-body p-40">
                                        <div id="agregarEditarMensaje"></div>
                                        <div id="contenidoPdf"></div>
                                        <div class="clear"></div>
                                        <form:form id="agregarEditarForm" method="post" modelAttribute="citaBean" action="${agregarEditar}" class="form-horizontal">
                                            <hr>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Estado de Cita</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-estado">
                                                        <form:select path="estadoCita" class="form-control select-estado">
                                                            <form:option value="1" label="Registrado" />
                                                            <form:option value="2" label="Confirmado" />
                                                            <form:option value="3" label="En Atención" />
                                                            <form:option value="4" label="Finalizado" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Comentario</label>
                                                <div class="col-sm-6">
                                                    <form:textarea path="comentario" class="form-control" maxlength="100"/>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-sm-3">
                                                    <input value="Exportar en PDF" class="button medium rounded gray font-open-sans" id="pdfBtn" type="button">
                                                    <div id="editor"></div>
                                                </div>
                                                <div class="col-sm-9 text-right">
                                                    <input value="Guardar" class="button medium rounded blue font-open-sans" data-loading-text="Loading..." type="submit">
                                                    <input value="Cancelar" class="button medium rounded gray font-open-sans" data-dismiss="modal" id="closeBtn" type="button">
                                                </div>
                                            </div>
                                        </form:form>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-12 mb-23">
                        <div id="calendar"></div>
                    </div>
                </div>
            </div>

        </div>
    </div>


    <script>
        var BASE_URL = "<c:url value="/" />";
        var VISTA = "CALENDARIO";
        var POPUP = "EDITAR";
    </script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap-daterangepicker/2.1.24/js/bootstrap-daterangepicker.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/pdfmake/0.1.32/build/pdfmake.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/pdfmake/0.1.32/build/vfs_fonts.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/app/cita.js"/>"></script>

</t:privado>
