<%-- 
    Document   : publico
    Created on : 23-may-2017, 23:49:38
    Author     : MBS GROUP
--%>

<%@tag description="Plantilla Sitio Publico" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />

<!DOCTYPE html>
<html>
    <head>
        <title>Doctorfast</title>
        <meta charset=utf-8 >
        <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge"><![endif]-->
        <meta name="robots" content="index, follow" > 
        <meta name="keywords" content="clinica,doctor,cita,especialidad,subespecialidad" > 
        <meta name="description" content="Doctorfast Comparation Software" > 
        <meta name="author" content="MBS GROUP">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="theme-color" content="#2a2b2f">

        <!-- FAVICONS -->
        <link rel="shortcut icon" href="<c:url value="/resources/img/favicon/favicon.ico"/>">

        <!-- CSS -->
        <!--  GOOGLE FONT -->		
        <link href='http://fonts.googleapis.com/css?family=Poppins:400,600,300%7COpen+Sans:400,300,700' rel='stylesheet' type='text/css'>

        <!-- BOOTSTRAP -->
        <link type="text/css" rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>" />

        <!-- ICONS ELEGANT FONT & FONT AWESOME & LINEA ICONS  -->		
        <link rel="stylesheet" href="<c:url value="/resources/css/vendor/icons-fonts/icons-fonts.css"/>" >	

        <!-- CSS THEME -->		
        <link rel="stylesheet" href="<c:url value="/resources/css/vendor/theme/style.css"/>" >

        <!-- ANIMATE -->	
        <link type="text/css" rel="stylesheet" href="<c:url value="/webjars/animate.css/3.5.2/animate.min.css"/>" />

        <link rel="stylesheet" href="<c:url value="/webjars/datatables/1.10.12/css/dataTables.bootstrap.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/webjars/fullcalendar/3.0.1/dist/fullcalendar.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/webjars/bootstrap-daterangepicker/2.1.24/css/bootstrap-daterangepicker.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/vendor/chosen/chosen.min.css"/>" >

        <!-- MBSGROUP -->
        <link rel="stylesheet" href="<c:url value="/resources/css/app/main.css"/>" >

        <!-- IE Warning CSS -->
        <!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/vendor/theme/ie-warning.css"/>" ><![endif]-->
        <!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/vendor/theme/ie8-fix.css"/>" ><![endif]-->

        <!-- Magnific popup, Owl Carousel Assets in style.css -->		

        <!-- CSS end -->

        <!-- JS begin some js files in bottom of file-->

        <script type="text/javascript" src="<c:url value="/webjars/jquery/2.1.4/jquery.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/webjars/moment/2.16.0/min/moment.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/webjars/URI.js/1.14.1/URI.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/webjars/URI.js/1.14.1/URITemplate.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/app/inicio.js"/>"></script>

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>
    <body>

        <!-- LOADER -->	
        <div id="loader-overflow">
            <div id="loader3" class="loader-cont">Please enable JS</div>
        </div>	

        <div id="wrap" class="boxed ">
            <div class="grey-bg"> <!-- Grey BG  -->	

                <!--[if lte IE 8]>
                <div id="ie-container">
                        <div id="ie-cont-close">
                                <a href='#' onclick='javascript&#058;this.parentNode.parentNode.style.display="none"; return false;'><img src='<c:url value="/resources/css/vendor/theme/images/ie-warn/ie-warning-close.jpg"/>' style='border: none;' alt='Close'></a>
                        </div>
                        <div id="ie-cont-content" >
                                <div id="ie-cont-warning">
                                        <img src='<c:url value="/resources/css/vendor/theme/images/ie-warn/ie-warning.jpg"/>' alt='Warning!'>
                                </div>
                                <div id="ie-cont-text" >
                                        <div id="ie-text-bold">
                                                You are using an outdated browser
                                        </div>
                                        <div id="ie-text">
                                                For a better experience using this site, please upgrade to a modern web browser.
                                        </div>
                                </div>
                                <div id="ie-cont-brows" >
                                        <a href='http://www.firefox.com' target='_blank'><img src='<c:url value="/resources/css/vendor/theme/images/ie-warn/ie-warning-firefox.jpg"/>' alt='Download Firefox'></a>
                                        <a href='http://www.opera.com/download/' target='_blank'><img src='<c:url value="/resources/css/vendor/theme/images/ie-warn/ie-warning-opera.jpg"/>' alt='Download Opera'></a>
                                        <a href='http://www.apple.com/safari/download/' target='_blank'><img src='<c:url value="/resources/css/vendor/theme/images/ie-warn/ie-warning-safari.jpg"/>' alt='Download Safari'></a>
                                        <a href='http://www.google.com/chrome' target='_blank'><img src='<c:url value="/resources/css/vendor/theme/images/ie-warn/ie-warning-chrome.jpg"/>' alt='Download Google Chrome'></a>
                                </div>
                        </div>
                </div>
                <![endif]-->

                <!-- HEADER 1 FONT WHITE TRANSPARENT -->
                <div class="header-black-bg"></div> <!-- NEED FOR TRANSPARENT HEADER ON MOBILE -->
                <header id="nav" class="header header-1">

                    <c:if test="${pageContext.request.userPrincipal.authenticated}">

                        <!-- TOP BAR -->
                        <div class="top-bar top-bar-black">
                            <div class="container-m-30 clearfix">

                                <!-- LEFT SECTION -->
                                <ul class="top-bar-section left">
                                    <sec:authorize access="hasRole('Administrador')">
                                        <li><a href="<c:url value="/clinica"/>"><i class="fa fa-user mr-5"></i>${user.nombrePersona}</a></li>
                                            </sec:authorize>
                                            <sec:authorize access="hasRole('Asistente')">
                                        <li><a href="<c:url value="/citas"/>"><i class="fa fa-user mr-5"></i>${user.nombrePersona}</a></li>
                                            </sec:authorize>
                                            <sec:authorize access="hasRole('Profesional')">
                                        <li><a href="<c:url value="/citas/calendario"/>"><i class="fa fa-user mr-5"></i>${user.nombrePersona}</a></li>
                                            </sec:authorize>
                                </ul>

                                <!-- RIGHT SECTION -->
                                <ul class="top-bar-section right">
                                    <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out mr-5"></i> Cerrar la sesión</a></li>
                                </ul>

                            </div>
                        </div>

                    </c:if>

                    <div class="header-wrapper">
                        <div class="container-m-30 clearfix">
                            <div class="logo-row">

                                <!-- LOGO --> 
                                <div class="logo-container-2">
                                    <div class="logo-2">
                                        <a href="<c:url value="/"/>" class="clearfix">
                                            <img src="<c:url value="/resources/img/logo.png"/>" class="logo-img" alt="Logo">
                                        </a>
                                    </div>
                                </div>
                                <!-- BUTTON --> 
                                <div class="menu-btn-respons-container">
                                    <button id="menu-btn" type="button" class="navbar-toggle btn-navbar collapsed" data-toggle="collapse" data-target="#main-menu .navbar-collapse">
                                        <span aria-hidden="true" class="icon_menu hamb-mob-icon"></span>
                                    </button>
                                </div>
                            </div>
                        </div>

                        <!-- MAIN MENU CONTAINER -->
                        <div class="main-menu-container">

                            <div class="container-m-30 clearfix">
                                
                                <div id="main-menu">
                                    <div class="navbar navbar-default" role="navigation">
                                        
                                        <nav class="collapse collapsing navbar-collapse right-1024">
                                            <ul class="nav navbar-nav">
                                                
                                                <!--li class="parent current">
                                                    <a href="#" class="open-sub"><div class="main-menu-title">Inicio</div></a>
                                                    <ul class="sub">
                                                        <li><a href="<c:url value="/"/>">Inicio</a></li>
                                                        <li><a href="#">Acerca de Nosotros</a></li>
                                                        <li><a href="#">¿Quienes Somos?</a></li>
                                                        <li><a href="#">Mapa del Sitio</a></li>
                                                    </ul>
                                                </li>
                                                <li><a href="#">Partners</a></li>
                                                <li><a href="#">Cont&aacute;ctanos</a></li-->
                                                <li><a href="<c:url value="/citas/consultar"/>">Consulta tu cita</a></li>
                                                <!--li><a href="#">Ayuda</a></li-->

                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </header>

                <jsp:doBody />

                <!-- FOOTER 4 BLACK -->
                <footer id="footer4" class="page-section pt-0 pb-50 footer2-black">
                    <div class="container">
                        <!-- <div class="row">

                            <div class="col-md-3 col-sm-3 widget">
                                <div class="logo-footer-cont">
                                    <a href="<c:url value="/"/>">
                                        <img class="logo-footer" src="<c:url value="/resources/img/logo-footer.png"/>" alt="logo">
                                    </a>
                                </div>
                                <div class="footer2-text-cont">
                                    <address>
                                        555 California str, Suite 100<br>
                                        San&nbsp;Francisco, CA 94107
                                    </address>
                                </div>
                                <div class="footer2-text-cont">
                                    1-800-312-2121<br>
                                    <a class="a-text" href="mailto:info@haswell.com">info@elementy.com</a>
                                </div>
                            </div>

                            <div class="col-md-3 col-sm-3 widget">
                                <ul class="links-list a-text-cont a-text-main-cont font-poppins">
                                    <li><a href="<c:url value="/"/>">Inicio</a></li>
                                    <li><a href="#">Acerca de Nosotros</a></li>
                                    <li><a href="#">¿Quienes Somos?</a></li>
                                    <li><a href="#">Mapa del Sitio</a></li>
                                    <li><a href="#">Partners</a></li>
                                    <li><a href="#">Cont&aacute;ctanos</a></li>
                                </ul>
                            </div>

                            <div class="col-md-3 col-sm-3 widget">
                                <ul class="links-list a-text-cont a-text-main-cont font-poppins">
                                    <li><a href="#">T&eacute;rminos y condiciones</a></li>
                                    <li><a href="#">Privacidad</a></li>
                                    <li><a href="#">Libro de reclamaciones</a></li>
                                    <li><a href="#">Preguntas frecuentes</a></li>
                                    <li><a href="#">Pol&iacute;ticas del sitio</a></li>
                                </ul>
                            </div>

                            <div class="col-md-3 col-sm-3 widget"></div>

                        </div>   -->  

                        <!-- SUB FOOTER -->
                        <div class="footer2-copy-cont clearfix">
                            <!-- Social Links -->
                            <div class="footer2-soc-a right">
                                <a href="https://www.facebook.com/DoctorfastPeru" title="Facebook" target="_blank"><i class="fa fa-facebook"></i></a>
                                <a href="https://twitter.com/doctorfastperu" title="Twitter" target="_blank"><i class="fa fa-twitter"></i></a>
                                <a href="https://www.linkedin.com/company/doctorfast" title="LinkedIn+" target="_blank"><i class="fa fa-linkedin"></i></a>
                            </div>

                            <!-- Copyright -->
                            <div class="left">
                                <a class="footer2-copy" href="<c:url value="/"/>" target="_blank">&copy; DOCTOR FAST - 2017</a>
                            </div>

                        </div>

                    </div>
                </footer>

                <!-- BACK TO TOP -->
                <p id="back-top">
                    <a href="#top" title="Back to Top"><span class="icon icon-arrows-up"></span></a>
                </p>

            </div><!-- End BG -->	
        </div><!-- End wrap -->	

        <!-- JS begin -->

        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>

        <!-- MAGNIFIC POPUP -->
        <script type="text/javascript" src="<c:url value="/webjars/Magnific-Popup/1.0.0/jquery.magnific-popup.min.js"/>"></script>

        <!-- PORTFOLIO SCRIPTS -->
        <script type="text/javascript" src="<c:url value="/webjars/imagesloaded/3.1.8/imagesloaded.pkgd.min.js"/>"></script>

        <!-- APPEAR -->    
        <script type="text/javascript" src="<c:url value="/resources/js/vendor/jquery.appear.js"/>"></script>


        <!-- OWL CAROUSEL --> 
        <script type="text/javascript" src="<c:url value="/webjars/OwlCarousel/1.3.2/owl.carousel.min.js"/>"></script>

        <!-- PARALLAX -->    
        <script type="text/javascript" src="<c:url value="/resources/js/vendor/jquery.stellar.min.js"/>"></script>

        <!-- DATATIMEPICKER -->    
        <script type="text/javascript" src="<c:url value="/webjars/Eonasdan-bootstrap-datetimepicker/4.15.35/js/bootstrap-datetimepicker.min.js"/>"></script>

        <!-- JQUERY TWEETS 
        <script src="js/twitter/jquery.tweet.js"></script> -->

        <!-- VALIDATION -->    
        <script type="text/javascript" src="<c:url value="/webjars/jquery-validation/1.16.0/jquery.validate.min.js"/>"></script>

        <!-- MAIN SCRIPT -->
        <script type="text/javascript" src="<c:url value="/resources/js/app/publico.js"/>"></script>

        <!-- JS end -->	

    </body>
</html>		