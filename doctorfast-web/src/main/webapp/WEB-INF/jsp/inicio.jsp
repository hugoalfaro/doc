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
    <spring:url value="/clinica/actualizarDatos.do" var="actualizarDatos" />

    <!-- PORTFOLIO SECTION 1 -->
    <div class="page-section" style="min-height: 500px;">
        <div class="relative">
            <div id="seccion-actualizar-datos" class="page-section">
                <div class="container">
                    <h1>Bienvenido a Doctorfast</h1>                    
                </div>
            </div>

        </div>
    </div>    

</t:privado>
