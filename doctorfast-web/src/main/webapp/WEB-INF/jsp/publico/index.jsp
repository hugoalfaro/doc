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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<t:publico>
    <spring:url value="/consultarEspecialidad" var="consultar" />

    <!-- STATIC MEDIA IMAGE -->
    <div id="index-link" class="sm-img-bg-fullscr parallax-section " style="background-image: url(resources/img/fondo-inicio.jpeg)" data-stellar-background-ratio="0.5">
        <div class="container sm-content-cont js-height-fullscr">
            <div class="sm-cont-middle">

                <!-- OPACITY container -->
                <div class="opacity-scroll2">

                    <!-- LAYER NR. 1 -->
                    <div class="light-60-wide ls-norm font-xxs-32-wide mt-0 mb-20 font-norm relative text-center1" >
                        Reserva Tu Cita Online.
                    </div>

                    <!-- LAYER NR. 2 -->
                    <div class="font-18 hide-0-736 mb-210 relative font-norm font-poppins text-center1">
                        Citas Médicas en Tiempo Real, Fácil y a través de Internet
                    </div>
                </div>
            </div>
        </div>
        <!-- SCROLL ICON -->
        <div class="local-scroll-cont">
            <a href="#seccion-promociones" class="scroll-down smooth-scroll">
                <div class="icon icon-arrows-down"></div>
            </a>  
        </div>
    </div>

    <!-- BUSCADOR -->
    <div class="mt-min-500 mt-xs-0 relative">
        <div class="container p-140-cont pb-40">
            <div class="ts4-text-cont bg-opacity mb-50">
                <div id="gestionarMensaje"></div>
                <form:form id="consultarForm" method="get" modelAttribute="sedeEspecialidadBean" action="${consultar}" >
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
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group col-sm-3">
                            <div class="select-styled select-distrito">
                                <form:select path="sede.ubigeo.id.coDistrito" class="form-control select-distrito">
                                    <form:option value="0" label="Elegir Distrito" />
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
    </div>

    <c:if test="${fn:length(promociones) gt 0}">
    <!-- PROMOCIONES -->
    <div id="seccion-promociones" class="page-section p-110-cont pb-0 mt-40 mt-md-20 mt-xs-0">
        <div class="container">
            <div class="row">
                <div class="mb-50">
                    <h2 class="section-title2 pr-0 text-center">PROMOCIONES</h2>
                </div>
            </div>
            <div class="row">
                <c:forEach var = "o" items = "${promociones}">
                    <div class="col-md-4 col-sm-4 shop-dep-item mb-10">
                        <a href="<c:url value="/citas/crearCitaDePromocion?idSede=${o.sedeEspecialidad.id.idSede}&idEspecialidad=${o.sedeEspecialidad.id.idEspecialidad}&idPromocion=${o.idPromocion}"/>">
                            <img src="https://s3.us-east-2.amazonaws.com/mbs-dfa-web-data/<c:url value="${o.imagen}"/>" alt="">
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>      
    </div>     
    </c:if> 

    <!-- PARTNERS -->
    <div id="seccion-partners"  class="page-section p-110-cont pb-10">
        <div class="container">
            <div class="row">

                <div class="col-sm-12">
                    <div class="mb-50">
                        <h2 class="section-title2 pr-0 text-center">PARTNERS</h2>
                    </div>
                    <div class="row">
                        <c:forEach var = "o" items = "${partners}">
                            <div class="col-sm-6 col-md-3 col-lg-3 pb-80">
                                <div class="post-prev-img">
                                    <a href="<c:url value="/consultarEspecialidad?sede.clinica.idClinica=${o.idClinica}"/>" target="_blank"><img src="https://s3.us-east-2.amazonaws.com/mbs-dfa-web-data/<c:url value="${o.logo}"/>" alt="${o.nombreAbreviado}"></a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="<c:url value="/resources/js/vendor/chosen.jquery.min.js"/>"></script>
    <script type="text/javascript">
        var BASE_URL = "<c:url value="/" />";
        $(function () {
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

            $("#limpiarBtn").click(function () {
                $('#sede\\.clinica\\.idClinica').val("0");
                $('#sede\\.ubigeo\\.id\\.coDepartamento').val("0");
                $('#sede\\.ubigeo\\.id\\.coProvincia').val("0");
                $('#sede\\.ubigeo\\.id\\.coDistrito').val("0");
                $('#especialidad\\.idEspecialidad').val("0");
                $("select").trigger("chosen:updated");
            });
        });
    </script> 

</t:publico>
