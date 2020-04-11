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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<t:privado>
    <spring:url value="/rest/cita/obtenerCitas" var="consultar" />
    <spring:url value="/rest/cita/agregarEditar" var="agregarEditar" />
    <spring:url value="/rest/cita/editar" var="editar" />

    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-consultar" class="page-section">
                <div class="container">
                    <h1>Citas</h1>
                    <div id="gestionarMensaje"></div>
                    <form:form id="consultarForm" method="post" modelAttribute="filtroForm" action="${consultar}" >
                        <div class="form-group col-sm-3">
                            <div class="select-styled w-100">
                                <form:select path="idSede" class="form-control w-100" placeholder="Sede">
                                    <form:option value="0" label="Todas las Sedes" />
                                    <c:forEach var = "o" items = "${sedes}">
                                        <form:option value="${o.idSede}" label="${o.nombre}" />
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
                                <form:select path="idProfesional" class="form-control w-100">
                                    <form:option value="0" label="Todos los Profesionales" />
                                </form:select>
                            </div>
                        </div>
                        <!--div class="form-group col-sm-3">
                            <input id="rangoConsulta" type="text" class="form-control" maxlength="50" placeholder="Fecha" />
                        </div-->
                        <div class="form-group col-sm-3">
                            <form:input path="cop" type="text" class="form-control" maxlength="50" placeholder="COP" />
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
                                        <h4 class="modal-title" id="myLargeModalLabel">
                                            <span class="mostrarAgregar">Agregar Cita de Profesional</span>
                                            <span class="mostrarEditar hidden">Editar Cita de Profesional</span>
                                        </h4>
                                    </div>
                                    <div class="modal-body p-40">
                                        <div id="agregarEditarMensaje"></div>
                                        <form:form id="agregarEditarForm" method="post" modelAttribute="citaBean" action="${agregarEditar}" class="form-horizontal">
                                            <h3 class="mt-0">Paciente</h3>
                                            <form:hidden path="tiempoCita"/>
                                            <input type="hidden" id="tiempoCitaProfesor" name="tiempoCitaProfesor"/>
                                            <input type="hidden" id="nombreClinica" value="${nombreClinica}"/>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Apellido Paterno&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="paciente.personaUsuario.apellidoPaterno" type="text" class="form-control desabilitarAlMostrar" maxlength="50" onkeypress="return validaLetras(event)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Apellido Materno&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="paciente.personaUsuario.apellidoMaterno" type="text" class="form-control desabilitarAlMostrar" maxlength="50" onkeypress="return validaLetras(event)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Nombres&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="paciente.personaUsuario.nombre" type="text" class="form-control desabilitarAlMostrar" maxlength="50" onkeypress="return validaLetras(event)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Correo&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="paciente.personaUsuario.correo" type="text" class="form-control desabilitarAlMostrar" maxlength="50" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Tel&eacute;fono&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="paciente.personaUsuario.telefono" type="text" class="form-control desabilitarAlMostrar" maxlength="12" onkeypress="return validaNumeros(event, false)" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <input value="Ver más" class="button medium rounded gray font-open-sans ml-40 mostrarMenos" id="mostrarMasBtn" type="button">
                                                <input value="Ver menos" class="button medium rounded gray font-open-sans ml-40 mostrarMas" id="mostrarMenosBtn" type="button">
                                            </div>
                                            <div class="mostrarMas">
                                                <div class="form-group">
                                                    <label class="control-label col-sm-4">Fecha Nacimiento</label>
                                                    <div class="col-sm-6">
                                                        <form:input path="paciente.personaUsuario.fechaNacimiento" type="text" class="form-control desabilitarAlMostrar" maxlength="10" onkeypress="return validaFecha(event)" />
                                                    </div>
                                                    <span class="input-group-btn"><button class="btn btn-default mt-sm-10" type="button" onclick="limpiarFechaNacimiento()">Limpiar</button></span>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-sm-4">Tipo de Documento</label>
                                                    <div class="col-sm-6">
                                                        <div class="select-styled select-tipoDocumento">
                                                            <form:select path="paciente.personaUsuario.tipoDocumento" class="form-control select-tipoDocumento desabilitarAlMostrar">
                                                                <form:option value="" label="Elegir Tipo de Documento" />
                                                                <form:option value="D" label="DNI" />
                                                                <form:option value="R" label="RUC" />
                                                                <form:option value="L" label="Libreta Electoral" />
                                                                <form:option value="C" label="Carnet de extranjería" />
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-sm-4">N&uacute;mero de Documento</label>
                                                    <div class="col-sm-6">
                                                        <form:input path="paciente.personaUsuario.documentoIdentidad" type="text" class="form-control desabilitarAlMostrar" maxlength="13" onkeypress="return validaNumeros(event, false)" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-sm-4">Sexo</label>
                                                    <div class="col-sm-6">
                                                        <div class="select-styled select-sexo">
                                                            <form:select path="paciente.personaUsuario.sexo" class="form-control select-sexo desabilitarAlMostrar">
                                                                <form:option value="" label="Elegir Sexo" />
                                                                <form:option value="1" label="Masculino" />
                                                                <form:option value="2" label="Femenino" />
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <h3 class="mt-0">Cita</h3>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Especialidad&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled w-100">
                                                        <form:select path="sedeEspProfesional.id.idEspecialidad" class="form-control w-100" disabled="true">
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
                                                        <form:select path="sedeEspProfesional.id.idSede" class="form-control w-100" disabled="true">
                                                            <form:option value="0" label="Elegir Sede" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Profesional&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled w-100">
                                                        <form:select path="sedeEspProfesional.id.idProfesional" class="form-control w-100" disabled="true">
                                                            <form:option value="0" label="Elegir Profesional" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Subespecialidad</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled w-100">
                                                        <form:select path="subespecialidad.idSubespecialidad" class="form-control w-100 desabilitarAlMostrar">
                                                            <form:option value="0" label="Elegir Subespecialidad" />
                                                        </form:select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Fecha&nbsp;*</label>
                                                <div class="col-sm-6">
                                                    <form:input path="fechaAtencion" type="text" class="form-control" readonly="true" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Hora&nbsp;*</label>
                                                <div class="col-sm-2">
                                                    <input id="horaAtencion" type="text" class="form-control" maxlength="50" readonly="true" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-sm-4">Estado</label>
                                                <div class="col-sm-6">
                                                    <div class="select-styled select-estado">
                                                        <form:select path="estadoCita" class="form-control select-estado desabilitarAlMostrar">
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
                                                    <form:textarea path="comentario" class="form-control desabilitarAlMostrar" maxlength="100"/>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-sm-3">
                                                    <input value="Exportar en PDF" class="button medium rounded gray font-open-sans mostrarEditar" id="pdfBtn" type="button">
                                                    <div id="editor"></div>
                                                </div>
                                                <div class="col-sm-9 text-right">
                                                    <input value="Eliminar" class="button medium rounded red font-open-sans mostrarEditar hidden" data-loading-text="Loading..." id="eliminarBtn" type="button">
                                                    <input value="Guardar" class="button medium rounded blue font-open-sans" data-loading-text="Loading..." id="submitBtn" type="submit">
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
        var VISTA = "CITAS";
        var POPUP = "AGREGAR_EDITAR";
    </script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap-daterangepicker/2.1.24/js/bootstrap-daterangepicker.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/pdfmake/0.1.32/build/pdfmake.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/pdfmake/0.1.32/build/vfs_fonts.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/app/cita.js"/>"></script>

</t:privado>
