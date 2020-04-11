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
<t:publico>
    <spring:url value="/rest/sedeEspecialidad/obtenerSedeEspecialidadesPublico" var="consultar" />

    <div class="container p-100-cont pb-0">
        <div class="ts4-text-cont bg-opacity caja">
            <div id="gestionarMensaje"></div>
            <form:form id="consultarForm" method="post" modelAttribute="sedeEspecialidadBean" action="${consultar}" >
                <div class="row">
                    <div class="form-group col-sm-3">
                        <div class="select-styled w-100">
                            <form:select path="sede.clinica.idClinica" class="form-control w-100">
                                <form:option value="0" label="Todas las Clinicas" />
                                <c:forEach var = "o" items = "${clinicas}">
                                    <form:option value="${o.idClinica}" label="${o.nombreAbreviado}" />
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group col-sm-3">
                        <div class="select-styled w-100">
                            <form:select path="especialidad.idEspecialidad" class="form-control w-100" placeholder="Especialidad">
                                <form:option value="0" label="Elegir Especialidad" />
                                <c:forEach var = "o" items = "${especialidades}">
                                    <form:option value="${o.idEspecialidad}" label="${o.nombre}" />
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-sm-3">
                        <div class="select-styled select-departamento">
                            <form:select path="sede.ubigeo.id.coDepartamento" class="form-control select-departamento">
                                <form:option value="0" label="Elegir Departamento" />
                                <c:forEach var = "o" items = "${departamentos}">                                            
                                    <form:option value="${o.id.coDepartamento}" label="${o.noDepartamento}" />
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group col-sm-3">
                        <div class="select-styled select-provincia">
                            <form:select path="sede.ubigeo.id.coProvincia" class="form-control select-provincia">
                                <form:option value="0" label="Elegir Provincia" />
                                <c:forEach var = "o" items = "${provincias}">                                            
                                    <form:option value="${o.id.coProvincia}" label="${o.noProvincia}" />
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group col-sm-3">
                        <div class="select-styled select-distrito">
                            <form:select path="sede.ubigeo.id.coDistrito" class="form-control select-distrito">
                                <form:option value="0" label="Elegir Distrito" />
                                <c:forEach var = "o" items = "${distritos}">                                            
                                    <form:option value="${o.id.coDistrito}" label="${o.noDistrito}" />
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12  text-right text-xxs-center">
                    <input value="Buscar" class="button medium rounded blue font-open-sans" data-loading-text="Loading..." type="submit">
                    <input value="Limpiar" class="button medium rounded gray font-open-sans" id="limpiarBtn" type="button">
                </div>
                <div class="clear"></div>
            </form:form>
        </div>
    </div>

    <div id="resultados"  class="page-section pb-50">
        <div class="container">
            <div class="ts4-text-cont bg-opacity">                        
                <div class="col-xs-12">
                    <table id="tabla" class="table table-striped table-bordered" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                <th>Nombre de Clinica</th>
                                <th>Sede</th>
                                <th>Especialidad</th>
                                <th>Dep./Prov./Dist.</th>
                                <th>Costo</th>
                                <th>Generar cita</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- DATATABLES -->    
    <script type="text/javascript" src="<c:url value="/webjars/datatables/1.10.12/js/jquery.dataTables.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"/>"></script>

    <script type="text/javascript" src="<c:url value="/resources/js/vendor/chosen.jquery.min.js"/>"></script>
    <script type="text/javascript">
        var BASE_URL = "<c:url value="/" />";
        var TABLA;
        var FORM_CONSULTAR = "#consultarForm";
        $(function () {
            $('#sede\\.clinica\\.idClinica').val(obtenerParametroGetOCero("sede.clinica.idClinica"));
            $('#especialidad\\.idEspecialidad').val(obtenerParametroGetOCero("especialidad.idEspecialidad"));
            $('#sede\\.ubigeo\\.id\\.coDepartamento').val(obtenerParametroGetOCero("sede.ubigeo.id.coDepartamento"));
            $('#sede\\.ubigeo\\.id\\.coProvincia').val(obtenerParametroGetOCero("sede.ubigeo.id.coProvincia"));
            $('#sede\\.ubigeo\\.id\\.coDistrito').val(obtenerParametroGetOCero("sede.ubigeo.id.coDistrito"));

            $("select").chosen({
                no_results_text: "No se encontró"
            });

            $("#sede\\.ubigeo\\.id\\.coDepartamento").change(function () {
                obtenerProvincias($(this).val(), "#sede\\.ubigeo\\.id\\.coProvincia");
                $("#sede\\.ubigeo\\.id\\.coDistrito").html('<option value="0">Elegir Distrito</option>');
                $("#sede\\.ubigeo\\.id\\.coDistrito").trigger("chosen:updated");
            });

            $("#sede\\.ubigeo\\.id\\.coProvincia").change(function () {
                obtenerDistritos($("#sede\\.ubigeo\\.id\\.coDepartamento").val(), $(this).val(), "#sede\\.ubigeo\\.id\\.coDistrito");
            });

            TABLA = $('#tabla').DataTable({
                language: DATATABLE_ES,
                dom: 'rtp',
                "scrollX": true,
                columns: [
                    {
                        sortable: false,
                        className: "center-text",
                        "render": function (data, type, full, meta) {
                            return '<img src="https://s3.us-east-2.amazonaws.com/mbs-dfa-web-data/' + full.sede.clinica.logo + '" class="img-responsive" alt=""/>';
                        }
                    },
                    {"data": "sede.clinica.nombreAbreviado"},
                    {"data": "sede.nombre"},
                    {"data": "especialidad.nombre"},
                    {
                        "render": function (data, type, full, meta) {
                            return full.sede.ubigeo.noDepartamento + '/' + full.sede.ubigeo.noProvincia + '/' + full.sede.ubigeo.noDistrito;
                        }
                    },
                    {
                        "render": function (data, type, full, meta) {
                            if (full.costoConsulta === null)
                                return '';
                            return 'S/.' + full.costoConsulta;
                        }
                    },
                    {
                        sortable: false,
                        className: "center-text",
                        "render": function (data, type, full, meta) {
                            return '<a id="generarCita" href="' + BASE_URL + 'citas/crearCita?idSede=' + full.sede.idSede + '&idEspecialidad=' + full.especialidad.idEspecialidad +
                                    '"><i class="fa fa-calendar" style="font-size:18px;"></i></a>';
                        }
                    }
                ],
                "order": [[1, "desc"]]
            });

            $(FORM_CONSULTAR).submit(function (event) {

                var data = {
                    sede: {
                        clinica: {
                            "idClinica": $('#sede\\.clinica\\.idClinica').val()
                        },
                        ubigeo: {
                            id: {
                                "coDepartamento": $('#sede\\.ubigeo\\.id\\.coDepartamento').val(),
                                "coProvincia": $('#sede\\.ubigeo\\.id\\.coProvincia').val(),
                                "coDistrito": $('#sede\\.ubigeo\\.id\\.coDistrito').val()
                            }
                        }
                    },
                    especialidad: {
                        "idEspecialidad": $('#especialidad\\.idEspecialidad').val()
                    }

                };

                consultar(FORM_CONSULTAR, data, TABLA, 'Especialidades');
                event.preventDefault();
            });

            $("#limpiarBtn").click(function () {
                $('#sede\\.clinica\\.idClinica').val("0");
                $('#sede\\.ubigeo\\.id\\.coDepartamento').val("0");
                $('#sede\\.ubigeo\\.id\\.coProvincia').val("0");
                $('#sede\\.ubigeo\\.id\\.coDistrito').val("0");
                $('#especialidad\\.idEspecialidad').val("0");
                $("select").trigger("chosen:updated");
                TABLA.clear().draw();
            });

            $(FORM_CONSULTAR).submit();
        });
    </script> 

</t:publico>
