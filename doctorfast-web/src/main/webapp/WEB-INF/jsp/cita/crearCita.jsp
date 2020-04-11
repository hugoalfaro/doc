<%-- 
    Document   : crearCita
    Created on : 18-sep-2017, 23:20:39
    Author     : MBS GROUP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<t:publico>
    <spring:url value="/rest/cita/obtenerCitasPublico" var="consultar" />
    <spring:url value="/rest/cita/agregarPublico" var="agregar" />

    <div class="container pt-80">
        <div class="mostrarAgregar">
            <div id="resumenCaja" class="ts4-text-cont bg-opacity caja">
                <h2 class="section-title2 pr-0 text-center">RESUMEN DE CITA</h2>
                <div class="logo-container-2">
                    <div class="logo-2">
                        <a href="<c:url value="/"/>" class="clearfix">
                            <img src="https://s3.us-east-2.amazonaws.com/mbs-dfa-web-data/<c:url value="${logo}"/>" class="logo-img" alt="Logo">
                        </a>
                    </div>
                </div>
                <p class="col-sm-6">
                    <b>Especialidad : </b>${especialidad}
                </p>
                <p class="col-sm-6">
                    <b>Clinica : </b>${clinica}
                </p>
                <p class="col-sm-6">
                    <b>Sede : </b>${sede}
                </p>
                <p class="col-sm-6">
                    <b>Dep./Prov./Dist. : </b>${departamento} ${provincia} ${distrito}
                </p>
                <div class="clear"></div>
            </div>

            <div id="gestionarMensaje"></div>
            <p class="col-xs-12">
                <b>I. ELEGIR HORARIO</b>
            </p>
            <div class="col-sm-3">
                <form:form id="consultarForm" method="post" modelAttribute="filtroForm" action="${consultar}" >
                    <input type="hidden" id="promocionInicio" value="${promocionInicio}"/>
                    <input type="hidden" id="promocionFin" value="${promocionFin}"/>
                    <div class="form-group">
                        <div class="select-styled w-100">
                            <form:select path="idProfesional" class="form-control w-100">
                                <form:option value="0" label="Elegir Profesional" />
                                <c:forEach var = "o" items = "${profesionales}">
                                    <form:option value="${o.idProfesional}" label="${o.personaUsuario.apellidoPaterno} ${o.personaUsuario.apellidoMaterno} ${o.personaUsuario.nombre}"
                                                 data-tiempocita="${o.tiempoCita}"/>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <input id="fecha" type="text" class="form-control" maxlength="50" placeholder="Fecha" />
                    </div>
                    <div class="col-sm-12 mb-23 text-right text-xxs-center">
                        <input value="Buscar" class="button medium rounded blue font-open-sans mb-10" data-loading-text="Loading..." type="submit">
                        <input value="Limpiar" class="button medium rounded gray font-open-sans mb-10" id="limpiarBtn" type="button">
                    </div>
                </form:form>
            </div>
            
            <div class="en-touch hidden button medium rounded green font-open-sans mb-10">
                Mantenga presionado por 1 segundo en el horario que desee su cita
            </div>

            <div class="col-sm-9 col-xs-12 mb-23">
                <div id="calendar"></div>
            </div>
        </div>
        <div id="resumenCaja" class="ts4-text-cont bg-opacity caja2 mostrarResumen hidden">
            <p class="col-sm-12 text-right" style="font-size: 17px;text-transform: uppercase;">
                <b>${clinica}</b>
            </p>
            <h2 class="section-title2 pr-0 text-center">CITA</h2>
            <p class="col-xs-12 text-center mb-40" style="font-size: 17px;font-weight: bold">Nro. <span id="codigoCita"></span></p>
            <p class="col-xs-12" style="font-size: 17px;">
                <b>INFORMACIÓN DE CITA</b>
            </p>
            <div class="col-sm-6">
                <p>
                    <b>Clinica : </b>${clinica}
                </p>
                <p>
                    <b>Sede : </b>${sede}
                </p>
                <p>
                    <b>Especialidad : </b>${especialidad}
                </p>       
                <p>
                    <b>Fecha de Atención : </b><span id="fechaLabel2"></span>
                </p> 
                <p>
                    <b>Profesional : </b><span id="profesionalLabel2"></span>
                </p>        
            </div>
            <div class="col-sm-6">
                <p>
                    <b>Departamento : </b><span id="departamentoLabel">${departamento}</span>
                </p>
                <p>
                    <b>Provincia : </b><span id="provinciaLabel">${provincia}</span>
                </p>
                <p>
                    <b>Distrito : </b><span id="distritoLabel">${distrito}</span>
                </p>                
            </div>
            <div id="resumen"></div>
            <div class="clear"></div>
        </div>

        <div class="mostrarResumen hidden caja2 pr-0">
            <div class="col-sm-12 mb-23 text-right text-xxs-center">
                <input value="Imprimir" class="button medium rounded blue font-open-sans" data-loading-text="Loading..." id="pdfBtn" type="button">
                <a class="button medium rounded gray" href="<c:url value="/"/>">Cerrar</a>
            </div>
        </div>

        <!-- Modal -->
        <div id="agregarEditarModal" class="modal fade bs-example-modal-lg bootstrap-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-body">
                    <div class="modal-content">
                        <div class="modal-header bg-gray-dark">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myLargeModalLabel">Crear Cita</h4>
                        </div>
                        <div class="modal-body p-40">
                            <div class="ts4-text-cont bg-opacity caja">
                                <h2 class="section-title2 pr-0 text-center">RESUMEN DE CITA</h2>
                                <div class="logo-container-2">
                                    <div class="logo-2">
                                        <a href="<c:url value="/"/>" class="clearfix">
                                            <img src="https://s3.us-east-2.amazonaws.com/mbs-dfa-web-data/<c:url value="${logo}"/>" class="logo-img" alt="Logo">
                                        </a>
                                    </div>
                                </div>
                                <p class="col-sm-6">
                                    <b>Especialidad : </b><span id="especialidadLabel">${especialidad}</span>
                                </p>
                                <p class="col-sm-6">
                                    <b>Clínica : </b><span id="clinicaLabel">${clinica}</span>
                                </p>
                                <p class="col-sm-6">
                                    <b>Sede : </b><span id="sedeLabel">${sede}</span>
                                </p>
                                <p class="col-sm-6">
                                    <b>Dep./Prov./Dist. : </b>${departamento} ${provincia} ${distrito}
                                </p>
                                <p class="col-sm-6">
                                    <b>Profesional : </b><span id="profesionalLabel"></span>
                                </p>
                                <p class="col-sm-6">
                                    <b>Fecha de Atención : </b><span id="fechaLabel"></span>
                                </p>
                                <div class="clear"></div>
                            </div>

                            <div id="agregarEditarMensaje"></div>

                            <p class="col-xs-12">
                                <b>II. INFORMACIÓN DEL PACIENTE</b>
                            </p>
                            <form:form id="crearForm" method="post" modelAttribute="citaBean" action="${agregar}" class="form-horizontal" >
                                <div class="form-group">
                                    <label class="control-label col-sm-4">Apellido Paterno&nbsp;*</label>
                                    <div class="col-sm-6">
                                        <form:input path="paciente.personaUsuario.apellidoPaterno" type="text" class="form-control" maxlength="50" onkeypress="return validaLetras(event)" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-4">Apellido Materno&nbsp;*</label>
                                    <div class="col-sm-6">
                                        <form:input path="paciente.personaUsuario.apellidoMaterno" type="text" class="form-control" maxlength="50" onkeypress="return validaLetras(event)" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-4">Nombres&nbsp;*</label>
                                    <div class="col-sm-6">
                                        <form:input path="paciente.personaUsuario.nombre" type="text" class="form-control" maxlength="50" onkeypress="return validaLetras(event)" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-4">Fecha Nacimiento&nbsp;*</label>
                                    <div class="col-sm-6">
                                        <form:input path="paciente.personaUsuario.fechaNacimiento" type="text" class="form-control" maxlength="10" onkeypress="return validaFecha(event)" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-4">Tipo de Documento&nbsp;*</label>
                                    <div class="col-sm-6">
                                        <div class="select-styled select-tipoDocumento">
                                            <form:select path="paciente.personaUsuario.tipoDocumento" class="form-control select-tipoDocumento">
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
                                        <form:input path="paciente.personaUsuario.documentoIdentidad" type="text" class="form-control" maxlength="13" onkeypress="return validaNumeros(event, false)" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-4">Sexo&nbsp;*</label>
                                    <div class="col-sm-6">
                                        <div class="select-styled select-sexo">
                                            <form:select path="paciente.personaUsuario.sexo" class="form-control select-sexo">
                                                <form:option value="1" label="Masculino" />
                                                <form:option value="2" label="Femenino" />
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-4">Correo&nbsp;*</label>
                                    <div class="col-sm-6">
                                        <form:input path="paciente.personaUsuario.correo" type="text" class="form-control" maxlength="50" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-4">Tel&eacute;fono&nbsp;*</label>
                                    <div class="col-sm-6">
                                        <form:input path="paciente.personaUsuario.telefono" type="text" class="form-control" maxlength="12" onkeypress="return validaNumeros(event, false)" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-3 col-sm-offset-2 mb-23 text-center p-20 caja">
                                        <label id="captchaTitulo">¿Eres un robot?</label><br>
                                        <label id="captchaTexto"></label>
                                        <input id="captchaInput" type="text" maxlength="2" onkeypress="return validaNumeros(event, false)">
                                    </div>
                                    <div class="col-sm-7 mb-23 text-right text-xxs-center">
                                        <input value="Guardar" class="button medium rounded blue font-open-sans" data-loading-text="Loading..." type="submit">
                                        <input value="Cancelar" class="button medium rounded gray font-open-sans" data-dismiss="modal" id="closeBtn" type="button">
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script>
        var BASE_URL = "<c:url value="/" />";
    </script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap-daterangepicker/2.1.24/js/bootstrap-daterangepicker.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/pdfmake/0.1.32/build/pdfmake.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/pdfmake/0.1.32/build/vfs_fonts.js"></script>
    <script type="text/javascript" src="<c:url value="/webjars/fullcalendar/3.0.1/dist/fullcalendar.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/webjars/fullcalendar/3.0.1/dist/locale-all.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/vendor/chosen.jquery.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/vendor/modernizr-custom.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/webjars/moment-timezone/0.5.5/moment-timezone-with-data.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/app/publicoCita.js"/>"></script>

</t:publico>
