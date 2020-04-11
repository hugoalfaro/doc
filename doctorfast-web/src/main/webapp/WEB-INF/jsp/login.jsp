<%-- 
    Document   : login
    Created on : 19-may-2017, 20:15:09
    Author     : MBS GROUP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<t:publico>

    <!-- STATIC MEDIA IMAGE -->
    <div id="pagina-login" class="sm-img-bg-fullscr parallax-section " style="background-image: url(resources/img/fondo-inicio.jpeg)" data-stellar-background-ratio="0.5">
        <div class="container sm-content-cont js-height-fullscr">
            <div class="sm-cont-middle">

                <!-- OPACITY container -->
                <div class="opacity-scroll2">

                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-4">

                            <div class="row">

                                <!-- TABS CONTENT -->
                                <div class="col-md-12">
                                    <div class="fes14-tab-content tab-content border-cont bg-white">

                                        <!-- TAB 1 -->
                                        <div class="row tab-pane fade in active" id="web-design">
                                            <c:if test="${not empty param.authentication_error}">
                                                <c:choose>
                                                    <c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message eq 'User is disabled'}">
                                                        <div class="alert alert-danger animated shake">
                                                            <p class="error">El usuario se encuentra inactivo. Por favor comuníquese con su administrador.</p>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="alert alert-danger animated shake">
                                                            <p class="error">Usuario y contraseña inválidos.</p>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:if test="${not empty param.authorization_error}">
                                                <div class="alert alert-danger animated shake">
                                                    <p class="error">No tiene permiso para acceder a esta informaci&oacute;n.</p>
                                                </div>
                                            </c:if>
                                            <c:if test="${not empty recuperacion_error}">
                                                <div class="alert alert-danger animated shake">
                                                    <p class="error">No se encontro el usuario en el Sistema.</p>
                                                </div>
                                            </c:if>
                                            <c:if test="${not empty recuperacion_ok}">
                                                <div class="alert alert-success animated shake">
                                                    Se envío los accesos a su correo.
                                                </div>
                                            </c:if>
                                            <form id="form-login" action="<c:url value="/administrador"/>" method="post" role="form">
                                                <div class="col-sm-12 mb-23">
                                                    <input id="usuario" class="form-control" type="text" name="usuario" placeholder="Usuario" />
                                                </div>
                                                <div class="col-sm-12 mb-23">
                                                    <input id="clave" class="form-control" type="password" name="clave" placeholder="Contrase&ntilde;a" />
                                                </div>
                                                <div class="col-sm-12 mb-23">
                                                    <div class="text-right text-xxs-center">
                                                        <input value="Ingresar" class="button medium rounded gray font-open-sans mt-40" data-loading-text="Loading..." type="submit">
                                                    </div>
                                                </div>
                                            </form>
                                        </div>

                                        <!-- TAB 2 -->
                                        <div class="row tab-pane fade" id="ui-design">
                                            <form id="form-recuperar-contrasena" action="<c:url value="/recuperarContrasena"/>" method="get" role="form">
                                                <div class="col-sm-12 mb-23">
                                                    Ingrese el usuario, se le enviar&aacute; en breve los accesos a su correo
                                                </div>
                                                <div class="col-sm-12 mb-23">
                                                    <input id="usuario" class="form-control" type="text" name="usuario" placeholder="Usuario" required />
                                                </div>
                                                <div class="col-sm-12 mb-23">
                                                    <div class="text-right text-xxs-center">
                                                        <input value="Enviar" class="button medium rounded gray font-open-sans mt-40" data-loading-text="Loading..." type="submit">
                                                    </div>
                                                </div>
                                            </form>
                                        </div>

                                        <!-- TAB 3 -->
                                        <div class="row tab-pane fade" id="graphic-design">
                                            <form id="form-recuperar-usuario" action="<c:url value="/recuperarUsuario"/>" method="get" role="form">
                                                <div class="col-sm-12 mb-23">
                                                    Ingrese el correo con que se registro, se le enviar&aacute; en breve los accesos
                                                </div>
                                                <div class="col-sm-12 mb-23">
                                                    <input id="correo" class="form-control" type="text" name="correo" placeholder="Correo" required />
                                                </div>
                                                <div class="col-sm-12 mb-23">
                                                    <div class="text-right text-xxs-center">
                                                        <input value="Enviar" class="button medium rounded blue font-open-sans mt-40" data-loading-text="Loading..." type="submit">
                                                    </div>
                                                </div>
                                            </form>
                                        </div>

                                    </div>
                                </div>

                            </div>

                            <!-- TABS NAV -->
                            <div class="row">
                                <div class="col-md-12">

                                    <ul class="fes14-nav-tabs nav nav-tabs bootstrap-tabs mt-0 login-opciones">
                                        <li class="active"><a href="#web-design" class="fes14-nav-a" data-toggle="tab"><small>Login</small></a></li>
                                        <li><a href="#ui-design"  class="fes14-nav-a" data-toggle="tab"><small>¿Olvidaste tu contrase&ntilde;a?</small></a></li>
                                        <li><a href="#graphic-design"  class="fes14-nav-a" data-toggle="tab"><small>¿Olvidaste tu usuario?</small></a></li>
                                    </ul>

                                </div>
                            </div>



                        </div>
                    </div>

                </div>

            </div>
        </div>
        <!-- SCROLL ICON -->
        <div class="local-scroll-cont">
            <a href="#hot-link" class="scroll-down smooth-scroll">
                <div class="icon icon-arrows-down"></div>
            </a>  
        </div>
    </div>

    <script type="text/javascript" src="<c:url value="/resources/js/app/login.js"/>"></script>

</t:publico>