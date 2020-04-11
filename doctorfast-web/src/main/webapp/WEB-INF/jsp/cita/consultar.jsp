<%-- 
    Document   : consultar
    Created on : 21-sep-2017, 13:48:16
    Author     : MBS GROUP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<t:publico>

    <div class="container pt-80" style="min-height: 600px;">
        <c:if test="${not empty ok}">
            <div class="alert alert-success animated shake">
                Se envío la información de su cita a su correo.
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger animated shake">
                <p class="error">No se encontro la información en el Sistema.</p>
            </div>
        </c:if>
        <div class="ts4-text-cont bg-opacity">
            <h2 class="section-title2 pr-0 pt-70 pb-80 text-center">CONSULTAR CITA</h2>
            <form id="form-consultar-cita" action="<c:url value="/citas/consultar"/>" method="post" role="form" class="form-horizontal">
                <input id="timezone" type="hidden" name="timezone" />
                <div class="col-sm-12 mb-23">
                    Ingrese los datos solicitados, se le enviar&aacute; en breve la informaci&oacute;n de su cita a su correo.
                </div>
                <div class="col-sm-4 mb-23">
                    <input id="codigo" class="form-control" type="text" name="codigo" placeholder="Código de Cita generado" />
                </div>
                <div class="col-sm-4 mb-23">
                    <input id="correo" class="form-control" type="email" name="correo" placeholder="Correo" />
                </div>
                <div class="col-sm-4 mb-23 text-right text-xxs-center">
                    <input value="Enviar" class="button medium rounded gray font-open-sans" data-loading-text="Loading..." type="submit">
                </div>
                <div class="clear"></div>
            </form>
        </div>
    </div>

    <script type="text/javascript" src="<c:url value="/webjars/moment-timezone/0.5.5/moment-timezone-with-data.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            $('#form-consultar-cita').validate({
                rules: {
                    codigo: {
                        required: true,
                        maxlength: 50
                    },
                    correo: {
                        required: true,
                        maxlength: 50
                    }
                }
            });
            $('#timezone').val(moment.tz.guess());
        });
    </script>

</t:publico>
