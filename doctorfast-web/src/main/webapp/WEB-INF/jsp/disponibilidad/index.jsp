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
    <spring:url value="/rest/disponibilidad/obtenerDisponibilidades" var="consultar" />
    <spring:url value="/rest/disponibilidad/agregarEditar" var="agregarEditar" />

    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-consultar" class="page-section">
                <div class="container">
                    <h1>Disponibilidad de Profesional</h1>
                    <div id="gestionarMensaje"></div>
                    <form:form id="consultarForm" method="post" modelAttribute="filtroForm" action="${consultar}" >
                        <div class="form-group col-sm-3">
                            <div class="select-styled w-100">
                                <form:select path="idProfesional" class="form-control w-100">
                                    <form:option value="0" label="Todos los Profesionales" />
                                    <c:forEach var = "o" items = "${profesionales}">
                                        <form:option value="${o.idProfesional}" label="${o.personaUsuario.apellidoPaterno} ${o.personaUsuario.apellidoMaterno} ${o.personaUsuario.nombre}" />
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group col-sm-3">
                            <div class="select-styled w-100">
                                <form:select path="idEspecialidad" class="form-control w-100" placeholder="Especialidad">
                                    <form:option value="0" label="Todas las Especialidades" />
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
                        <!--div class="form-group col-sm-3">
                            <input id="rangoConsulta" type="text" class="form-control" maxlength="50" placeholder="Fecha" />
                        </div-->
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
                                        <h4 class="modal-title" id="myLargeModalLabel">
                                            <span class="mostrarAgregar">Agregar Disponibilidad de Profesional</span>
                                            <span class="mostrarEditar hidden">Editar Disponibilidad de Profesional</span>
                                        </h4>
                                    </div>
                                    <div class="modal-body p-40">
                                        <div id="agregarEditarMensaje"></div>
                                        <form:form id="agregarEditarForm" method="post" modelAttribute="disponibilidadBean" action="${agregarEditar}" class="form-horizontal">
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Fecha&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="fechaInicio" type="text" class="form-control" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-3">Hora&nbsp;*</label>
                                                <div class="col-sm-2">
                                                    <input id="horaInicio" type="text" class="form-control time-picker" maxlength="50" />
                                                </div>
                                                <label class="control-label col-sm-1">&nbsp;a&nbsp;</label>
                                                <div class="col-sm-3">
                                                    <input id="horaFin" type="text" class="form-control time-picker" maxlength="50" />
                                                </div>
                                            </div>
                                            <div id="mostrarCheckEditarSiguientes" class="form-group ml-40 hidden">
                                                <label class="control-label">
                                                    <form:checkbox path="editarSiguientes"/>
                                                    <spring:message code="disponibilidad.agregar.labels.editar.siguientes" />
                                                </label>
                                            </div>
                                            <div class="mostrarEditarSiguientes">
                                                <div class="form-group">
                                                    <label class="control-label col-sm-3">Profesional&nbsp;*</label>
                                                    <div class="col-sm-6">
                                                        <div class="select-styled w-100">
                                                            <form:select path="sedeEspProfesional.id.idProfesional" class="form-control w-100">
                                                                <form:option value="0" label="Elegir Profesional" />
                                                                <c:forEach var = "o" items = "${profesionales}">
                                                                    <form:option value="${o.idProfesional}" label="${o.personaUsuario.apellidoPaterno} ${o.personaUsuario.apellidoMaterno} ${o.personaUsuario.nombre}" />
                                                                </c:forEach>
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-sm-3">Especialidad&nbsp;*</label>
                                                    <div class="col-sm-6">
                                                        <div class="select-styled w-100">
                                                            <form:select path="sedeEspProfesional.id.idEspecialidad" class="form-control w-100">
                                                                <form:option value="0" label="Elegir Especialidad" />
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-sm-3">Sede&nbsp;*</label>
                                                    <div class="col-sm-6">
                                                        <div class="select-styled w-100">
                                                            <form:select path="sedeEspProfesional.id.idSede" class="form-control w-100">
                                                                <form:option value="0" label="Elegir Sede" />
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <input value="Repetir" class="button medium rounded gray font-open-sans ml-40 mostrarMenos" id="mostrarMasBtn" type="button">
                                                    <input value="No Repetir" class="button medium rounded gray font-open-sans ml-40 mostrarMas hidden" id="mostrarMenosBtn" type="button">
                                                    <input type="hidden" id="repetir"/>
                                                </div>
                                                <div class="mostrarMas hidden">
                                                    <div class="form-group">
                                                        <label class="control-label col-sm-3"><spring:message code="labels.repeticion.tipo" />&nbsp;*</label>
                                                        <div class="col-sm-6">
                                                            <div class="select-styled select-repeticionTipo">
                                                                <select class="form-control select-repeticionTipo" id="repeticionTipo">
                                                                    <option value="1"><spring:message code="labels.repeticion.tipo.valor1" /></option>
                                                                    <option value="2"><spring:message code="labels.repeticion.tipo.valor2" /></option>
                                                                    <option value="3"><spring:message code="labels.repeticion.tipo.valor3" /></option>
                                                                    <option value="4"><spring:message code="labels.repeticion.tipo.valor4" /></option>
                                                                    <option value="5"><spring:message code="labels.repeticion.tipo.valor5" /></option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="grupoRepeticionCada">
                                                        <label class="control-label col-sm-3 col-xs-12"><spring:message code="labels.repeticion.cada" />&nbsp;*</label>
                                                        <div class="col-sm-2 col-xs-4">
                                                            <div class="select-styled select-repeticionCada">
                                                                <select class="form-control select-repeticionCada" id="repeticionCada">
                                                                    <c:forEach begin="1" end="30" varStatus="loop">
                                                                        <option value="<c:out value="${loop.count}"/>"><c:out value="${loop.count}"/></option>
                                                                    </c:forEach>
                                                                </select><br />
                                                            </div>
                                                        </div>
                                                        <label class="control-label col-sm-4 col-xs-8 fw-normal" style="text-align: left;" id="sufijoRepiteCada">d&iacute;as(s)</label>
                                                    </div>
                                                    <div class="form-group" id="grupoRepeticionDias">
                                                        <label class="control-label col-sm-3 col-xs-12"><spring:message code="labels.repeticion.dias" />&nbsp;*</label>
                                                        <label class="control-label fw-normal col-xs-1"><input type="checkbox" name="repeticionDias" value="L">&nbsp;<spring:message code="labels.repeticion.dias.lunes" /></label>
                                                        <label class="control-label fw-normal col-xs-1"><input type="checkbox" name="repeticionDias" value="M">&nbsp;<spring:message code="labels.repeticion.dias.martes" /></label>
                                                        <label class="control-label fw-normal col-xs-1"><input type="checkbox" name="repeticionDias" value="X">&nbsp;<spring:message code="labels.repeticion.dias.miercoles" /></label>
                                                        <label class="control-label fw-normal col-xs-1"><input type="checkbox" name="repeticionDias" value="J">&nbsp;<spring:message code="labels.repeticion.dias.jueves" /></label>
                                                        <label class="control-label fw-normal col-xs-1"><input type="checkbox" name="repeticionDias" value="V">&nbsp;<spring:message code="labels.repeticion.dias.viernes" /></label>
                                                        <label class="control-label fw-normal col-xs-1"><input type="checkbox" name="repeticionDias" value="S">&nbsp;<spring:message code="labels.repeticion.dias.sabado" /></label>
                                                        <label class="control-label fw-normal col-xs-1"><input type="checkbox" name="repeticionDias" value="D">&nbsp;<spring:message code="labels.repeticion.dias.domingo" /></label>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-sm-3"><spring:message code="labels.repeticion.inicio" /></label>
                                                        <div class="col-sm-6">
                                                            <input class="form-control" id="repeticionInicio" type="text" disabled="disabled"/>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-sm-3 col-xs-12"><spring:message code="labels.repeticion.fin" />&nbsp;*</label>
                                                        <label class="control-label col-sm-3 col-xs-12 fw-normal" style="text-align: left;">
                                                            <input id="finRadio2" name="finRadio" type="radio" value="2" checked="checked"/>
                                                            <spring:message code="labels.repeticion.fin.fecha.prefijo" />
                                                        </label>
                                                        <div class="col-sm-3 col-xs-8">
                                                            <input class="form-control" id="repeticionFin" name="repeticionFin" type="text" />
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-sm-3 col-xs-12 col-sm-offset-3 control-label fw-normal" style="text-align: left;">
                                                            <input id="finRadio1" name="finRadio" type="radio" value="1" />
                                                            <spring:message code="labels.repeticion.fin.veces.prefijo" />
                                                        </label>
                                                        <div class="col-sm-3 col-xs-8">
                                                            <input class="form-control" id="repeticionVeces" name="repeticionVeces" type="number" disabled="disabled" min="1" onkeypress="return validaNumeros(event, false)" />
                                                        </div>
                                                        <label class="control-label col-sm-3 col-xs-4 fw-normal" style="text-align: left;">
                                                            <spring:message code="labels.repeticion.fin.veces.sufijo" />
                                                        </label>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-sm-3"><spring:message code="labels.repeticion.resumen" /></label>
                                                        <label class="col-sm-6 control-label fw-normal" style="text-align: left;" id="resumenEnModal">
                                                            <span id="resumenFrecuencia"></span>
                                                            <span id="resumenDias"></span>
                                                            <span id="resumenFinaliza"></span>
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="text-right">
                                                    <input value="Eliminar" class="button medium rounded red font-open-sans mostrarEditar hidden" data-loading-text="Loading..." id="eliminarBtn" type="button">
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

                    <div id="eliminarDisponibilidadModal" class="modal fade" tabindex="-1" role="dialog">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <form id="repetirForm" method="post" class="form-horizontal">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title"><spring:message code="disponibilidad.eliminar.titulo" /></h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <label class="col-xs-12 control-label" style="text-align: center;">
                                                <spring:message code="disponibilidad.eliminar.labels.elegirOpcion" />
                                            </label>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-5 col-xs-12">
                                                <button type="button" class="btn btn-default col-xs-12" id="soloEstaVezBtn"><spring:message code="disponibilidad.eliminar.btn.soloEstaVez" /></button>
                                            </div>
                                            <label class="col-sm-7 col-xs-12 control-label" style="font-weight: normal;text-align: left;">
                                                <spring:message code="disponibilidad.eliminar.labels.soloEstaVez" />
                                            </label>
                                        </div>
                                        <br />
                                        <div class="row">
                                            <div class="col-sm-5 col-xs-12">
                                                <button type="button" class="btn btn-default col-xs-12" id="todosLosSgtesBtn"><spring:message code="disponibilidad.eliminar.btn.todosLosSgtes" /></button>
                                            </div>
                                            <label class="col-sm-7 col-xs-12 control-label" style="font-weight: normal;text-align: left;">
                                                <spring:message code="disponibilidad.eliminar.labels.todosLosSgtes" />
                                            </label>
                                        </div>
                                        <br />
                                        <div class="row">
                                            <div class="col-sm-5 col-xs-12">
                                                <button type="button" class="btn btn-default col-xs-12" id="todosLosEventosBtn"><spring:message code="disponibilidad.eliminar.btn.todosLosEventos" /></button>
                                            </div>
                                            <label class="col-sm-7 col-xs-12 control-label" style="font-weight: normal;text-align: left;">
                                                <spring:message code="disponibilidad.eliminar.labels.todosLosEventos" />
                                            </label>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal" id="closeEliminarBtn">Cancelar</button>
                                    </div>
                                </form>
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
    </script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap-daterangepicker/2.1.24/js/bootstrap-daterangepicker.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/app/disponibilidad.js"/>"></script>

</t:privado>
