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
    <spring:url value="/rest/sedeEspecialidad/obtenerSedeEspecialidades" var="consultar" />
    <spring:url value="/rest/sedeEspecialidad/agregarEditar" var="agregarEditar" />
    <spring:url value="/rest/sedeEspSubesp/agregar" var="agregarSubespecialidad" />

    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-consultar" class="page-section">
                <div class="container">
                    <h1>Especialidades</h1>
                    <div id="gestionarMensaje"></div>
                    <form:form id="consultarForm" method="post" modelAttribute="sedeEspecialidadBean" action="${consultar}" >
                        <div class="form-group col-sm-3">
                            <form:input path="especialidad.codigo" type="text" class="form-control" maxlength="50" placeholder="Código" />
                        </div>
                        <div class="form-group col-sm-3">
                            <form:input path="especialidad.nombre" type="text" class="form-control" maxlength="50" placeholder="Nombre" />
                        </div>
                        <div class="form-group col-sm-3">
                            <div class="select-styled w-100">
                                <form:select path="sede.idSede" class="form-control w-100">
                                    <form:option value="0" label="Todas las Sedes" />
                                    <c:forEach var = "o" items = "${sedes}">                                            
                                        <form:option value="${o.idSede}" label="${o.nombre}" />
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group col-sm-3">
                            <div class="select-styled select-estado">
                                <form:select path="estado" class="form-control select-estado" placeholder="Estado">
                                    <form:option value="" label="Todos los estados" />
                                    <form:option value="1" label="Activo" />
                                    <form:option value="0" label="Inactivo" />
                                </form:select>
                            </div>
                        </div>
                        <div class="col-sm-12 mb-23  text-right text-xxs-center">
                            <input value="Buscar" class="button medium rounded blue font-open-sans" data-loading-text="Loading..." type="submit">
                            <input value="Limpiar" class="button medium rounded gray font-open-sans" id="limpiarBtn" type="button">
                        </div>
                    </form:form>

                    <hr class="visible-xs"/>

                    <!-- Large modal -->
                    <div class="col-sm-12 mb-23">
                        <button class="button medium rounded blue font-open-sans" data-toggle="modal" data-target="#agregarEditarModal">Agregar</button>
                    </div>

                    <!-- Modal -->
                    <div id="agregarEditarModal" class="modal fade bs-example-modal-lg bootstrap-modal" tabindex="-1" role="dialog" aria-labelledby="agregarEditarModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-body">
                                <div class="modal-content">
                                    <div class="modal-header bg-gray-dark">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="agregarEditarModalLabel">
                                            <span class="mostrarAgregar">Agregar Especialidad</span>
                                            <span class="mostrarEditar hidden">Editar Especialidad</span>
                                        </h4>
                                    </div>
                                    <div class="modal-body p-40">
                                        <div id="agregarEditarMensaje"></div>
                                        <form:form id="agregarEditarForm" method="post" modelAttribute="sedeEspecialidadBean" action="${agregarEditar}" class="form-horizontal">
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Nombre&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled w-100">
                                                        <form:select path="especialidad.idEspecialidad" class="form-control w-100">
                                                            <form:option value="0" label="Elegir Especialidad" />
                                                            <c:forEach var = "o" items = "${especialidades}">                                            
                                                                <form:option value="${o.idEspecialidad}" label="${o.nombre}" data-codigo="${o.codigo}" />
                                                            </c:forEach>
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">C&oacute;digo&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="especialidad.codigo" type="text" class="form-control" disabled="true" />
                                                </div>
                                            </div>
                                            <div class="form-group">                                                    
                                                <label class="control-label col-sm-3">Sedes</label>
                                            </div>
                                            <div class="col-xs-12">
                                                <table id="agregarEditarTabla" class="display" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>Estado</th>
                                                            <th>Sede</th>
                                                            <th>Costo de Consulta (S/.)</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody></tbody>
                                                </table>
                                            </div>

                                            <div class="row">
                                                <div class="text-right">
                                                    <input value="Guardar" class="button medium rounded blue font-open-sans mt-40" data-loading-text="Loading..." type="submit">
                                                    <input value="Cancelar" class="button medium rounded gray font-open-sans mt-40" data-dismiss="modal" id="closeBtn" type="button">
                                                </div>
                                            </div>
                                        </form:form>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-12">
                        <table id="tabla" class="display" cellspacing="0" width="100%">
                            <thead>
                                <tr>
                                    <th>C&oacute;digo</th>
                                    <th>Nombre</th>
                                    <th>Sede</th>
                                    <th>Estado</th>
                                    <th>Editar</th>
                                    <th>Eliminar</th>
                                    <th>Subespecialidades</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>C&oacute;digo</th>
                                    <th>Nombre</th>
                                    <th>Sede</th>
                                    <th>Estado</th>
                                    <th>Editar</th>
                                    <th>Eliminar</th>
                                    <th>Subespecialidades</th>
                                </tr>
                            </tfoot>
                            <tbody></tbody>
                        </table>
                    </div>

                    <!-- Modal -->
                    <div id="subespecialidadesModal" class="modal fade bs-example-modal-lg bootstrap-modal" tabindex="-1" role="dialog" aria-labelledby="subespecialidadesModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-body">
                                <div class="modal-content">
                                    <div class="modal-header bg-gray-dark">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="subespecialidadesModalLabel">Subespecialidades</h4>
                                    </div>
                                    <div class="modal-body p-40">
                                        <div id="subespecialidadMensaje"></div>
                                        <form:form id="agregarSubespecialidadForm" method="post" modelAttribute="sedeEspSubespBean" action="${agregarSubespecialidad}" class="form-horizontal">
                                            <form:hidden path="id.idSede" />
                                            <form:hidden path="id.idEspecialidad" />
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Sede</label>
                                                <div class="col-sm-6">
                                                    <form:input path="sedeEspecialidad.sede.nombre" type="text" class="form-control" disabled="true" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Especialidad</label>
                                                <div class="col-sm-6">
                                                    <form:input path="sedeEspecialidad.especialidad.nombre" type="text" class="form-control" disabled="true" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Subespecialidad&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled w-100">
                                                        <form:select path="id.idSubespecialidad" class="form-control w-100">
                                                            <form:option value="0" label="Elegir Subespecialidad" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Costo&nbsp;(S/.)&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="costo" type="text" class="form-control" maxlength="5" onkeypress="return validaNumeros(event, true)" />
                                                </div>
                                            </div>
                                            <div class="col-sm-12 mb-23 text-right text-xxs-center">
                                                <input value="Agregar" class="button medium rounded blue font-open-sans" data-loading-text="Loading..." type="submit">
                                                <input value="Limpiar" class="button medium rounded gray font-open-sans" id="limpiarBtn" type="button">
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <table id="subespecialidadesTabla" class="display" cellspacing="0" width="100%">
                                                        <thead>
                                                            <tr>
                                                                <th>Subespecialidad</th>
                                                                <th>Costo (S/.)</th>
                                                                <th>Eliminar</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody></tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        var BASE_URL = "<c:url value="/" />";
    </script>
    <script type="text/javascript" src="<c:url value="/resources/js/app/sedeEspecialidad.js"/>"></script>

</t:privado>
