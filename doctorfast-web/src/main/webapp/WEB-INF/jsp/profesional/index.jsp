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
    <spring:url value="/rest/profesional/obtenerProfesionales" var="consultar" />
    <spring:url value="/rest/profesional/agregarEditar" var="agregarEditar" />

    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-consultar" class="page-section">
                <div class="container">
                    <h1>Profesionales</h1>
                    <div id="gestionarMensaje"></div>
                    <form:form id="consultarForm" method="post" modelAttribute="profesionalBean" action="${consultar}" >                        
                        <div class="form-group col-sm-3">
                            <form:input path="personaUsuario.documentoIdentidad" type="text" class="form-control" maxlength="13" placeholder="Número de Documento" onkeypress="return validaNumeros(event, false)" />
                        </div>
                        <div class="form-group col-sm-3">
                            <form:input path="personaUsuario.apellidoPaterno" type="text" class="form-control" maxlength="50" placeholder="Apellido Paterno" onkeypress="return validaLetras(event)" />
                        </div>
                        <div class="form-group col-sm-3">
                            <form:input path="personaUsuario.apellidoMaterno" type="text" class="form-control" maxlength="50" placeholder="Apellido Materno" onkeypress="return validaLetras(event)" />
                        </div>
                        <div class="form-group col-sm-3">
                            <form:input path="personaUsuario.nombre" type="text" class="form-control" maxlength="50" placeholder="Nombres" onkeypress="return validaLetras(event)" />
                        </div>
                        <div class="form-group col-sm-3">
                            <form:input path="cop" type="text" class="form-control" maxlength="50" placeholder="COP" />
                        </div>
                        <div class="form-group col-sm-3">
                            <div class="select-styled w-100">
                                <form:select path="sedeEspProfesional.id.idEspecialidad" class="form-control w-100">
                                    <form:option value="0" label="Todas las Especialidades" />
                                    <c:forEach var = "o" items = "${especialidades}">
                                        <form:option value="${o.idEspecialidad}" label="${o.nombre}" />
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group col-sm-3">
                            <div class="select-styled select-estado">
                                <form:select path="estado" class="form-control select-estado">
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
                    <div id="agregarEditarModal" class="modal fade bs-example-modal-lg bootstrap-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-body">
                                <div class="modal-content">
                                    <div class="modal-header bg-gray-dark">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myLargeModalLabel">
                                            <span class="mostrarAgregar">Agregar Profesional</span>
                                            <span class="mostrarEditar hidden">Editar Profesional</span>
                                        </h4>
                                    </div>
                                    <div class="modal-body p-40">
                                        <div id="agregarEditarMensaje"></div>
                                        <form:form id="agregarEditarForm" method="post" modelAttribute="profesionalBean" action="${agregarEditar}" class="form-horizontal">
                                            <h3 class="mt-0">Datos Personales</h3>
                                            <form:hidden path="idProfesional"/>
                                            <form:hidden path="personaUsuario.idPersonaUsuario"/>
                                            <input type="hidden" id="tiempoCitaClinica" name="tiempoCitaClinica" value="${profesionalBean.tiempoCita}"/>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Tipo de Documento&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-tipoDocumento">
                                                        <form:select path="personaUsuario.tipoDocumento" class="form-control select-tipoDocumento">
                                                            <form:option value="D" label="DNI" />
                                                            <form:option value="R" label="RUC" />
                                                            <form:option value="L" label="Libreta Electoral" />
                                                            <form:option value="C" label="Carnet de extranjería" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">N&uacute;mero de Documento&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="personaUsuario.documentoIdentidad" type="text" class="form-control" maxlength="13" onkeypress="return validaNumeros(event, false)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Apellido Paterno&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="personaUsuario.apellidoPaterno" type="text" class="form-control" maxlength="50" onkeypress="return validaLetras(event)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Apellido Materno&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="personaUsuario.apellidoMaterno" type="text" class="form-control" maxlength="50" onkeypress="return validaLetras(event)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Nombres&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="personaUsuario.nombre" type="text" class="form-control" maxlength="50" onkeypress="return validaLetras(event)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Fecha Nacimiento&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="personaUsuario.fechaNacimiento" type="text" class="form-control" maxlength="10" onkeypress="return validaFecha(event)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Correo&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="personaUsuario.correo" type="email" class="form-control" maxlength="50" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">COP&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="cop" type="text" class="form-control" maxlength="50" />
                                                </div>
                                            </div>
                                            <h3>Datos del Sistema</h3>
                                            <div class="form-group mostrarEditar">
                                                <label class="control-label col-sm-4">Usuario&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="personaUsuario.usuario" type="text" class="form-control" maxlength="50" disabled="true" />
                                                </div>
                                            </div>
                                            <div class="form-group mostrarEditar">
                                                <label class="control-label col-sm-4">Clave&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="personaUsuario.clave" type="password" class="form-control" maxlength="50" disabled="true" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Color de Disponibilidad&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="colorDisponibilidad" type="color" class="form-control" />
                                                </div>
                                                <input type="hidden" id="colorDisponibilidadPorDefecto" value="${profesionalBean.colorDisponibilidad}"/>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Color de Cita&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="colorCita" type="color" class="form-control" />
                                                <input type="hidden" id="colorCitaPorDefecto" value="${profesionalBean.colorCita}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Tiempo de Cita</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-tiempoCita">
                                                        <form:select path="tiempoCita" class="form-control select-tiempoCita">
                                                            <form:option value="30" label="30 minutos" />
                                                            <form:option value="60" label="1 hora" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Estado</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-estado">
                                                        <form:select path="estado" class="form-control select-estado">
                                                            <form:option value="1" label="Activo" />
                                                            <form:option value="0" label="Inactivo" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <h3>Especialidades y Sedes</h3>
                                            <div id="agregarEspecialidadSedeMensaje"></div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Especialidad&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled w-100">
                                                        <form:select path="sedeEspProfesional.id.idEspecialidad" class="form-control w-100 ignore">
                                                            <form:option value="0" label="Elegir Especialidad" />
                                                            <c:forEach var = "o" items = "${especialidades}">
                                                                <form:option value="${o.idEspecialidad}" label="${o.nombre}" />
                                                            </c:forEach>
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Sede&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled w-100">
                                                        <form:select path="sedeEspProfesional.id.idSede" class="form-control w-100 ignore">
                                                            <form:option value="0" label="Elegir Sede" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-sm-10 mb-23 text-right text-xxs-center">
                                                    <input value="Agregar" class="button small rounded blue font-open-sans" data-loading-text="Loading..." id="agregarEspecialidadSedeBtn" type="button">
                                                    <input value="Limpiar" class="button small rounded gray font-open-sans" id="limpiarEspecialidadSedeBtn" type="button">
                                                </div>
                                            </div>
                                            <div class="col-xs-12">
                                                <table id="agregarEditarTabla" class="display" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>Especialidad</th>
                                                            <th>Sede</th>
                                                            <th>Eliminar</th>
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
                                    <th>Nro. Doc.</th>
                                    <th>Nombre</th>
                                    <th>COP</th>
                                    <th>Estado</th>
                                    <th>Editar</th>
                                    <th>Eliminar</th>
                                    <th>Disponibilidad</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>Nro. Doc.</th>
                                    <th>Nombre</th>
                                    <th>COP</th>
                                    <th>Estado</th>
                                    <th>Editar</th>
                                    <th>Eliminar</th>
                                    <th>Disponibilidad</th>
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
    <script src="${pageContext.request.contextPath}/webjars/bootstrap-daterangepicker/2.1.24/js/bootstrap-daterangepicker.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/app/profesional.js"/>"></script>

</t:privado>
