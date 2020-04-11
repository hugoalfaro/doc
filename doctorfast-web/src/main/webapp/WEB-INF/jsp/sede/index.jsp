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
    <spring:url value="/rest/sede/obtenerSedes" var="consultar" />
    <spring:url value="/rest/sede/agregarEditar" var="agregarEditar" />

    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-consultar" class="page-section">
                <div class="container">
                    <h1>Sedes</h1>
                    <div id="gestionarMensaje"></div>
                    <form:form id="consultarForm" method="post" modelAttribute="sedeBean" action="${consultar}" >
                        <div class="row">
                            <div class="form-group col-sm-3">
                                <form:input path="codigoLargo" type="text" class="form-control" maxlength="50" placeholder="Código" />
                            </div>
                            <div class="form-group col-sm-3">
                                <form:input path="nombre" type="text" class="form-control" maxlength="50" placeholder="Nombre" />
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
                        </div>
                        <div class="row">
                            <div class="form-group col-sm-3">
                                <div class="select-styled select-departamento">
                                    <form:select path="ubigeo.id.coDepartamento" class="form-control select-departamento">
                                        <form:option value="0" label="Elegir Departamento" />
                                        <c:forEach var = "o" items = "${departamentos}">                                            
                                            <form:option value="${o.id.coDepartamento}" label="${o.noDepartamento}" />
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group col-sm-3">
                                <div class="select-styled select-provincia">
                                    <form:select path="ubigeo.id.coProvincia" class="form-control select-provincia">
                                        <form:option value="0" label="Elegir Provincia" />
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group col-sm-3">
                                <div class="select-styled select-distrito">
                                    <form:select path="ubigeo.id.coDistrito" class="form-control select-distrito">
                                        <form:option value="0" label="Elegir Distrito" />
                                    </form:select>
                                </div>
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
                    <div id="agregarEditarModal" class="modal fade bs-example-modal-lg bootstrap-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-body">
                                <div class="modal-content">
                                    <div class="modal-header bg-gray-dark">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myLargeModalLabel">
                                            <span class="mostrarAgregar">Agregar Sede</span>
                                            <span class="mostrarEditar hidden">Editar Sede</span>
                                        </h4>
                                    </div>
                                    <div class="modal-body p-40">
                                        <div id="agregarEditarMensaje"></div>
                                        <form:form id="agregarEditarForm" method="post" modelAttribute="sedeBean" action="${agregarEditar}" class="form-horizontal">
                                            <div class="form-group mostrarEditar hidden">
                                                <label class="control-label col-sm-3">C&oacute;digo&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="codigoLargo" type="text" class="form-control" readonly="true" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Nombre&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="nombre" type="text" class="form-control" maxlength="50" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Departamento&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-departamento">
                                                        <form:select path="ubigeo.id.coDepartamento" class="form-control select-departamento">
                                                            <form:option value="0" label="Elegir Departamento" />
                                                            <c:forEach var = "o" items = "${departamentos}">
                                                                <form:option value="${o.id.coDepartamento}" label="${o.noDepartamento}" />
                                                            </c:forEach>
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Provincia&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-provincia">
                                                        <form:select path="ubigeo.id.coProvincia" class="form-control select-provincia">
                                                            <form:option value="0" label="Elegir Provincia" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Distrito&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-distrito">
                                                        <form:select path="ubigeo.id.coDistrito" class="form-control select-distrito">
                                                            <form:option value="0" label="Elegir Distrito" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Direcci&oacute;n&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="direccion" type="text" class="form-control" maxlength="50" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Estado</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-estado">
                                                        <form:select path="estado" class="form-control select-estado" placeholder="Estado">
                                                            <form:option value="1" label="Activo" />
                                                            <form:option value="0" label="Inactivo" />
                                                        </form:select>
                                                    </div>
                                                </div>
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
                                    <th>Departamento</th>
                                    <th>Provincia</th>
                                    <th>Distrito</th>
                                    <th>Estado</th>
                                    <th>Editar</th>
                                    <th>Eliminar</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>C&oacute;digo</th>
                                    <th>Nombre</th>
                                    <th>Departamento</th>
                                    <th>Provincia</th>
                                    <th>Distrito</th>
                                    <th>Estado</th>
                                    <th>Editar</th>
                                    <th>Eliminar</th>
                                </tr>
                            </tfoot>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>


    <script>
        var BASE_URL = "<c:url value="/" />";
    </script>
    <script type="text/javascript" src="<c:url value="/webjars/jQuery-Autocomplete/1.2.7/jquery.autocomplete.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/app/sede.js"/>"></script>

</t:privado>
