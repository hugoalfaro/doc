<%-- 
    Document   : index
    Created on : 19-may-2017, 22:25:02
    Author     : MBS GROUP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<t:privado>
    <spring:url value="/clinica/actualizarDatos.do" var="actualizarDatos" />

    <!-- PORTFOLIO SECTION 1 -->
    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-403" class="page-section">
                <div class="container">
                    <div class="text-center">
                        <h1 class="error404-numb2">404</h1>
                        <h3 class="error404-text2">La página que estás buscando,<br>no se pudo encontrar</h3>
                        <a class="button medium rounded gray" href="<c:url value="/inicio"/>">VOLVER A LA PÁGINA DE INICIO</a>
                    </div>
                </div>
            </div>

        </div>
    </div>

</t:privado>
