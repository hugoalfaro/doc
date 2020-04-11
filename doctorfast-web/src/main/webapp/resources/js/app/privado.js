/* --------------------------------------------
 DOCUMENT.READY
 --------------------------------------------- */
$(document).ready(function () {
    'use strict';

    /* --------------------------------------------
     HEADER TRANSPARENT MOBILE FIX
     --------------------------------------------- */
    if ($('.header-black-bg').length) {
        $("#menu-btn").on("click", function () {

            if ($("#nav").hasClass("transparent-fix")) {
                $("#nav").removeClass("transparent-fix");
            } else {
                $("#nav").addClass("transparent-fix");
            }
        });
    }
    ;

    /* --------------------------------------------
     SEARCH
     --------------------------------------------- */
    //if you change this breakpoint in the style.css file (or _layout.scss if you use SASS), don't forget to update this value as well
    var MqL = 1170;
    //move nav element position according to window width

    //open search form
    $('.cd-search-trigger').on('click', function (event) {
        event.preventDefault();
        toggleSearch();
    });

    function toggleSearch(type) {
        if (type == "close") {
            //close serach 
            $('.cd-search').removeClass('is-visible');
            $('.cd-search-trigger').removeClass('search-is-visible');
            $('.cd-overlay').removeClass('search-is-visible');
        } else {
            //toggle search visibility
            $('.cd-search').toggleClass('is-visible');
            $('.cd-search-trigger').toggleClass('search-is-visible');
            $('.cd-overlay').toggleClass('search-is-visible');
            if (windowT.width() > MqL && $('.cd-search').hasClass('is-visible'))
                $('.cd-search').find('input[type="search"]').focus();
            ($('.cd-search').hasClass('is-visible')) ? $('.cd-overlay').addClass('is-visible') : $('.cd-overlay').removeClass('is-visible');
        }
    }

    /* --------------------------------------------
     EQUAL HEIGHTS
     --------------------------------------------- */
    //init equal height
    $('.equal-height').equalHeights();

    /* --------------------------------------------
     SCROLL TO TOP
     --------------------------------------------- */
    // hide #back-top first
    $("#back-top").hide();

    // fade in #back-top
    $(function () {
        windowT.scroll(function () {
            if ($(this).scrollTop() > 100) {
                $('#back-top').fadeIn();
            } else {
                $('#back-top').fadeOut();
            }
        });

        // scroll body to 0px on click
        $('#back-top a').on('click', function () {
            $('body,html').animate({
                scrollTop: 0
            }, 600);
            return false;
        });
    });

    /* --------------------------------------------
     TOGGLE
     --------------------------------------------- */
    $('.toggle-view-custom').on('click', 'li', function () {

        var text = $(this).children('div.panel');

        if (text.is(':hidden')) {
            text.slideDown('10');
            $(this).children('.ui-accordion-header').addClass('ui-accordion-header-active');
        } else {
            text.slideUp('10');
            $(this).children('.ui-accordion-header').removeClass('ui-accordion-header-active');
        }

    });

    /* --------------------------------------------
     SMOOTH SCROLL TO 
     --------------------------------------------- */
    $('a.smooth-scroll[href^="#"]').on('click', function (event) {

        var target = $($(this).attr('href'));

        if (target.length) {
            event.preventDefault();
            $('html, body').animate({
                scrollTop: target.offset().top
            }, 600);
        }

    });

    /* --------------------------------------------
     JS NOT FOR MOBILE (PARALLAX, OPACITY SCROLL)
     --------------------------------------------- */
    if (mobileDetect == false) {
        /* --------------------------------------------
         OPACITY SCROLL
         --------------------------------------------- */
        if ($('.opacity-scroll2').length) {
            windowT.on('scroll', function () {
                $('.opacity-scroll2').css('opacity', function () {
                    return 1 - ((windowT.scrollTop() / windowT.height()) * 1.5);
                });
            });
        }
        ;

    }//END JS NOT FOR MOBILE

    /* --------------------------------------------
     SKILL BAR ANIMATION
     --------------------------------------------- */
    $('.skillbar').appear(function () {
        $('.skillbar').each(function () {
            $(this).find('.skillbar-bar').animate({
                width: $(this).attr('data-percent')
            }, 2000);
        });
    });

    /* --------------------------------------------
     BOOTSTRAP JS
     --------------------------------------------- */
    //TOOLOTIPS INITIALIZE
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    });

    //POPOVER INITIALIZE	
    $(function () {
        $('[data-toggle="popover"]').popover()
    });

    // ACCORDION
    var accordPanels = $(".accordion > dd").hide();

    accordPanels.first().slideDown("easeOutExpo");
    $(".accordion > dt > a").first().addClass("active");

    $(".accordion").on('click', 'dt > a', function () {

        var current = $(this).parent().next("dd");
        $(".accordion > dt > a").removeClass("active");
        $(this).addClass("active");
        accordPanels.not(current).slideUp("easeInExpo");
        $(this).parent().next().slideDown("easeOutExpo");

        return false;

    });

    // TOGGLE
    $(".toggle > dd").hide();

    $(".toggle").on('click', 'dt > a', function () {

        if ($(this).hasClass("active")) {

            $(this).parent().next().slideUp("easeOutExpo");
            $(this).removeClass("active");

        } else {
            var current = $(this).parent().next("dd");
            $(this).addClass("active");
            $(this).parent().next().slideDown("easeOutExpo");
        }

        return false;
    });

    /* --------------------------------------------
     FUNCTIONS
     --------------------------------------------- */
    initMenu();

    if ($('#flickr-feeds').length) {
        initFlickrFeeds();
    }
    ;
    if ($('#twitter-feeds').length) {
        initTwitterFeeds();
    }
    ;
    if ($('#nav').length) {
        initAffixCheck();
    }
    ;
    if ($('.mobile-transparent').length) {
        initMobTranspFix();
    }
    ;
    if ($('#items-grid').length) {
        initWorkFilter();
    }
    ;
    if ($('.masonry').length) {
        initMasonry();
    }
    ;
    if ($('.wow').length) {
        initWow();
    }
    ;
    if ($('.owl-plugin').length) {
        initPageSliders();
    }
    ;
    if ($('.mfp-plugin').length) {
        initMagnPopup();
    }
    ;
    if ($('.js-height-fullscr').length) {
        initImgHeight();
    }
    ;
    if ($('.count-number').length) {
        initCounters();
    }
    ;
    if ($('#header-left-nav').length) {
        initLeftMenu();
    }
    ;
    if ($('#google-map').length) {
        initMap();
    }
    ;

    //WINDOW RESIZE
    windowT.resize(function () {
        $('.equal-height').css('height', 'auto').equalHeights();
        if ($('#nav').length) {
            initAffixCheck();
        }
        ;
        initImgHeight();
        initLeftMenu();
    });

    //WINDOW WIDTH CHANGE
    var widthWin = windowT.width();
    windowT.resize(function () {
        if ($(this).width() != widthWin) {
            widthWin = $(this).width();
            initLeftMenu();
        }
    });

});

/* --------------------------------------------
 HEADER MOBILE MENU TRANSPARENT FIX
 --------------------------------------------- */
function initMobTranspFix() {
    'use strict';
    var menuMob = $('#nav');

    $('#menu-btn').on('click', function () {

        if (menuMob.hasClass('transparent-fix')) {
            menuMob.removeClass('transparent-fix');
        } else {
            menuMob.addClass('transparent-fix');
        }
    });
}
;

/* --------------------------------------------
 HEADER MENU
 --------------------------------------------- */
function initMenu() {
    'use strict';
    var $ = jQuery,
            body = $('body'),
            primary = '#main-menu';

    $(primary).on('click', '.open-sub', function (event) {
        event.preventDefault();

        var item = $(this).closest('li, .box');

        if ($(item).hasClass('active')) {
            $(item).children().last().slideUp(150);
            $(item).removeClass('active');
        } else {
            var li = $(this).closest('li, .box').parent('ul, .sub-list').children('li, .box');

            if ($(li).is('.active')) {
                $(li).removeClass('active').children('ul').slideUp(150);
            }

            $(item).children().last().slideDown(150);
            $(item).addClass('active');
        }
    });

}
;

/* --------------------------------------------
 PLATFORM DETECT
 --------------------------------------------- */

var htmlT = $('html'),
        windowT = $(window),
        ieDetect = false,
        mobileDetect = false,
        ua = window.navigator.userAgent,
        old_ie = ua.indexOf('MSIE '),
        new_ie = ua.indexOf('Trident/');

if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent)) {
    mobileDetect = true;
    htmlT.addClass('mobile');
} else {
    htmlT.addClass('no-mobile');
}
;

//IE Detect
if ((old_ie > -1) || (new_ie > -1)) {
    ieDetect = true;
}
;

/* --------------------------------------------
 PAGE LOADER
 --------------------------------------------- */
$("body").imagesLoaded(function () {
//    $(".loader-cont").fadeOut();
    $("#loader-overflow").delay(200).fadeOut(700);
});

$(document).on({
    ajaxStart: function () {
        $("#loader-overflow").fadeIn();
    },
    ajaxStop: function () {
        $("#loader-overflow").delay(200).fadeOut(700);
    }
});

/* --------------------------------------------
 MAGNIFIC POPUP SETTINGS
 --------------------------------------------- */
function initMagnPopup() {
    //Inline popups
    $('#inline-popups').magnificPopup({
        delegate: 'a',
        removalDelay: 500, //delay removal by X to allow out-animation
        callbacks: {
            beforeOpen: function () {
                this.st.mainClass = this.st.el.attr('data-effect');
            }
        },
        midClick: true // allow opening popup on middle mouse click. Always set it to true if you don't provide alternative source.
    });

    //Image popups
    $('.lightbox').magnificPopup({
        // delegate: 'a',
        type: 'image',
        mainClass: 'mfp-3d-unfold',
        removalDelay: 500, //delay removal by X to allow out-animation
        callbacks: {
            beforeOpen: function () {
                // just a hack that adds mfp-anim class to markup 
                this.st.image.markup = this.st.image.markup.replace('mfp-figure', 'mfp-figure mfp-with-anim');
                // this.st.mainClass = this.st.el.attr('data-effect');
            }
        },
        closeOnContentClick: true,
        midClick: true // allow opening popup on middle mouse click. Always set it to true if you don't provide alternative source.
    });

    //Hinge effect popup
    $('a.hinge').magnificPopup({
        mainClass: 'mfp-with-fade',
        removalDelay: 1000, //delay removal by X to allow out-animation
        callbacks: {
            beforeClose: function () {
                this.content.addClass('hinge');
            },
            close: function () {
                this.content.removeClass('hinge');
            }
        },
        midClick: true
    });

    //GALERY
    $('.popup-gallery').magnificPopup({
        delegate: 'a',
        type: 'image',
        tLoading: 'Loading image #%curr%...',
        mainClass: 'mfp-3d-unfold',
        removalDelay: 500, //delay removal by X to allow out-animation
        callbacks: {
            beforeOpen: function () {
                // just a hack that adds mfp-anim class to markup 
                this.st.image.markup = this.st.image.markup.replace('mfp-figure', 'mfp-figure mfp-with-anim');
                // this.st.mainClass = this.st.el.attr('data-effect');
            }
        },
        gallery: {
            enabled: true,
            navigateByImgClick: true,
            preload: [0, 1] // Will preload 0 - before current, and 1 after the current image
        },
        image: {
            tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
            /*titleSrc: function(item) {
             return item.el.attr('title') + '<small>by Marsel Van Oosten</small>';
             }*/
        }
    });

    //GALERY 2
    $('.popup-gallery2').magnificPopup({
        delegate: 'a',
        type: 'image',
        tLoading: 'Loading image #%curr%...',
        mainClass: 'mfp-3d-unfold',
        removalDelay: 500, //delay removal by X to allow out-animation
        callbacks: {
            beforeOpen: function () {
                // just a hack that adds mfp-anim class to markup 
                this.st.image.markup = this.st.image.markup.replace('mfp-figure', 'mfp-figure mfp-with-anim');
                // this.st.mainClass = this.st.el.attr('data-effect');
            }
        },
        gallery: {
            enabled: true,
            navigateByImgClick: true,
            preload: [0, 1] // Will preload 0 - before current, and 1 after the current image
        },
        image: {
            tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
            /*titleSrc: function(item) {
             return item.el.attr('title') + '<small>by Marsel Van Oosten</small>';
             }*/
        }
    });

    //MULTI GALERY 
    $('.popup-multi-gallery').each(function () { // the containers for all your galleries
        $(this).magnificPopup({
            delegate: 'a', // the selector for gallery item
            type: 'image',
            gallery: {
                enabled: true
            }
        });
    });

    //VIDEO GMAPS POPUP
    $('.popup-youtube, .popup-vimeo, .popup-gmaps').magnificPopup({
        //disableOn: 700,
        type: 'iframe',
        mainClass: 'mfp-fade',
        removalDelay: 160,
        preloader: false,
        fixedContentPos: false
    });
}
;

/* --------------------------------------------
 FIXED HEADER ON - OFF
 --------------------------------------------- */
function initAffixCheck() {
    'use strict';
    var navAffix = $('#nav');

    //FIXED HEADER ON
    navAffix.affix({
        offset: {top: 1, }
    });

    if ((windowT.width() < 1025)) {
        //FIXED HEADER OFF
        windowT.off('.affix');
        navAffix.removeData('bs.affix').removeClass('affix affix-top affix-bottom');
    }

}
;

/* --------------------------------------------
 HEADER LEFT MENU
 --------------------------------------------- */
function initLeftMenu() {
    var hlNav = $('#header-left-nav');

    if ((windowT.width() < 1025)) {
        hlNav.removeClass('in')
    }
    if ((windowT.width() > 1024) && (!hlNav.hasClass('in'))) {
        hlNav.addClass('in')
    }

}
;

/* ---------------------------------------------
 Height 100%  
 --------------------------------------------- */
function initImgHeight() {
    (function ($) {
        $(".js-height-fullscr").height($(window).height());
    })(jQuery);
}

/* ---------------------------------------------
 MASONRY
 --------------------------------------------- */
function initMasonry() {
    (function ($) {

        $(".masonry").imagesLoaded(function () {
            $(".masonry").masonry();
        });

    })(jQuery);
}

//EQUAL HEIGHTS PLUGIN-------------------------------------------------------------------
/*!
 * Simple jQuery Equal Heights
 *
 * Copyright (c) 2013 Matt Banks
 * Dual licensed under the MIT and GPL licenses.
 * Uses the same license as jQuery, see:
 * http://docs.jquery.com/License
 *
 * @version 1.6.0
 */
!function (a) {
    a.fn.equalHeights = function () {
        var b = 0, c = a(this);
        return c.each(function () {
            var c = a(this).innerHeight();
            c > b && (b = c)
        }), c.css("height", b)
    }, a("[data-equal]").each(function () {
        var b = a(this), c = b.data("equal");
        b.find(c).equalHeights()
    })
}(jQuery);

jQuery.validator.setDefaults({
    onkeyup: false,
    errorPlacement: $.noop,
    highlight: function (element) {
        $(element).addClass('error');
    },
    success: function (element) {
        element.removeClass('error');
    }
//    , showErrors: function (errorMap, errorList) {
//        mostrarMensaje('#agregarEditarMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
////        mostrarMensaje('#agregarEditarMensaje', 'danger', 'El formulario contiene ' + this.numberOfInvalids() + ' errores.');
//        this.defaultShowErrors();
//    }
});

jQuery.validator.addMethod("notEqual", function (value, element, param) {
    return this.optional(element) || value != param;
}, "Please specify a different (non-default) value");

$.each($.validator.methods, function (key, value) {
    $.validator.methods[key] = function () {
        if (arguments.length > 0 && arguments[1] !== "") {
            var el = $(arguments[1]);
            el.val($.trim(el.val()));
        }
        return value.apply(this, arguments);
    };
});

function cerrarAlerta(evento) {
    $(evento).closest("#alerta").addClass("hidden");
}

function confirmarDesactivar(elemento, nombre, funcionOnclickHtml) {
    if (!$('#confirmarDesactivar' + elemento + 'Modal').length) {
        $('body').append('<div id="confirmarDesactivar' + elemento + 'Modal" class="modal fade bs-example-modal-lg bootstrap-modal" tabindex="-1" role="dialog" aria-labelledby="confirmarDesactivar' + elemento + 'ModalLabel" aria-hidden="true">' +
                '<div class="modal-dialog modal-sm">' +
                '<div class="modal-body">' +
                '<div class="modal-content">' +
                '<div class="modal-header bg-gray-dark">' +
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' +
                '<h4 class="modal-title" id="confirmarDesactivar' + elemento + 'ModalLabel">Confirmar</h4>' +
                '</div>' +
                '<div class="modal-body p-40">¿Está seguro que desea eliminar ' + nombre + '?</div><div class="modal-footer">' +
                '<input value="Eliminar" class="button small rounded red font-open-sans" data-loading-text="Loading..." id="enviarConfirmarDesactivar' + elemento + 'Btn" onclick="' + funcionOnclickHtml + ';return false;" type="button">&nbsp;' +
                '<input value="Cancelar" class="button small rounded gray font-open-sans" data-dismiss="modal" id="closeConfirmarDesactivar' + elemento + 'Btn" type="button">&nbsp;' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>');

        $('#confirmarDesactivar' + elemento + 'Modal').on('hidden.bs.modal', function (e) {
            if ($('.modal').hasClass('in')) {
                $('body').addClass('modal-open');
            }
        });

    } else {
        $('#enviarConfirmarDesactivar' + elemento + 'Btn').attr('onclick', funcionOnclickHtml + ';return false;');
    }
    $('#confirmarDesactivar' + elemento + 'Modal').modal('show');
}

function confirmar(mensaje, funcionOnclickHtml) {
    if (!$('#confirmarModal').length) {
        $('body').append('<div id="confirmarModal" class="modal fade bs-example-modal-lg bootstrap-modal" tabindex="-1" role="dialog" aria-labelledby="confirmarModalLabel" aria-hidden="true">' +
                '<div class="modal-dialog modal-sm">' +
                '<div class="modal-body">' +
                '<div class="modal-content">' +
                '<div class="modal-header bg-gray-dark">' +
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' +
                '<h4 class="modal-title" id="confirmarModalLabel">Confirmar</h4>' +
                '</div>' +
                '<div class="modal-body p-40">' + mensaje + '</div><div class="modal-footer">' +
                '<input value="Aceptar" class="button small rounded red font-open-sans" data-loading-text="Loading..." onclick="' + funcionOnclickHtml + ';return false;" type="button">&nbsp;' +
                '<input value="Cancelar" class="button small rounded gray font-open-sans" data-dismiss="modal" id="closeConfirmarBtn" type="button">&nbsp;' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>');

        $('#confirmarModal').on('hidden.bs.modal', function (e) {
            if ($('.modal').hasClass('in')) {
                $('body').addClass('modal-open');
            }
        });
    }
    $('#confirmarModal').modal('show');
}

function consultar(formulario, data, tabla, nombre) {
    $.ajax({
        url: $(formulario).attr("action"),
        data: JSON.stringify(data),
        dataType: 'json',
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            tabla.clear().draw();
            tabla.rows.add(data).draw();
            if (data.length === 0) {
                mostrarMensaje('#gestionarMensaje', 'info', 'No se encontraron ' + nombre);
            }
        }
    });
}

$('#consultarForm input[type="submit"]').click(function() {
  $("#gestionarMensaje").addClass('hidden');
});

$('.time-picker').datetimepicker({
    format: 'LT'
});

function obtenerSedesDeEspecialidad(elemento, idEspecialidad, seleccion, elementoProfesional, seleccionProfesional, elementoSubespecialidad, seleccionSubespecialidad, elementoTiempoCita) {
    var html = '<option value="0">Elegir Sede</option>';
    if (idEspecialidad === "0") {
        $(elemento).html(html);
        return;
    }
    var data = {
        especialidad: {
            idEspecialidad: idEspecialidad
        },
        sede: {},
        estado: "1"
    };
    $.ajax({
        url: BASE_URL + 'rest/sedeEspecialidad/obtenerSedeEspecialidades',
        data: JSON.stringify(data),
        dataType: 'json',
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            $.each(data, function (key, value) {
                html += '<option value="' + value.sede.idSede + '">' + value.sede.nombre + '</option>';
            });
            $(elemento).html(html);
            if (seleccion) {
                $(elemento).val(seleccion);
                if (elementoProfesional) {
                    obtenerProfesionalesDeSedeEspecialidad(elementoProfesional, seleccion, idEspecialidad, seleccionProfesional, elementoTiempoCita);
                }
                if (elementoSubespecialidad) {
                    obtenerSubespecialidadesDeSedeEspecialidad(elementoSubespecialidad, seleccion, idEspecialidad, seleccionSubespecialidad);
                }
            }
        }
    });
}

function obtenerSedesDeEspecialidadProfesional(elemento, idEspecialidad, idProfesional, seleccion, etiquetaSinElegir) {
    var html;
    if (etiquetaSinElegir) {
        html = '<option value="0">' + etiquetaSinElegir + '</option>';
    } else {
        html = '<option value="0">Elegir Sede</option>';
    }
    if (idEspecialidad === "0") {
        $(elemento).html(html);
        return;
    }
    var data = {
        id: {
            idEspecialidad: idEspecialidad,
            idProfesional: idProfesional
        }
    };
    $.ajax({
        url: BASE_URL + 'rest/sedeEspProfesional/obtenerSedesDeEspecialidadProfesional',
        data: JSON.stringify(data),
        dataType: 'json',
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            $.each(data, function (key, value) {
                html += '<option value="' + value.idSede + '">' + value.nombre + '</option>';
            });
            $(elemento).html(html);
            if (seleccion) {
                $(elemento).val(seleccion);
            }
        }
    });
}

function obtenerSedesDeClinica(elemento, idClinica) {
    var html = '<option value="0">Todas las Sedes</option>';
    if (idClinica === "0") {
        $(elemento).html(html);
        return;
    }
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/sede/obtenerSedesDeClinica/{idClinica}',
                {idClinica: idClinica}).toString(),
        method: 'GET',
        success: function (data) {
            $.each(data, function (key, value) {
                html += '<option value="' + value.idSede + '">' + value.nombre + '</option>';
            });
            $(elemento).html(html);
        }
    });
}

function obtenerEspecialidadesDeSede(elemento, idSede) {
    var html = '<option value="0">Todas las Especialidades</option>';
    if (idSede === "0") {
        $(elemento).html(html);
        return;
    }
    var data = {
        especialidad: {},
        sede: {
            idSede: idSede
        },
        estado: "1"
    };
    $.ajax({
        url: BASE_URL + 'rest/sedeEspecialidad/obtenerSedeEspecialidades',
        data: JSON.stringify(data),
        dataType: 'json',
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            $.each(data, function (key, value) {
                html += '<option value="' + value.especialidad.idEspecialidad + '">' + value.especialidad.nombre + '</option>';
            });
            $(elemento).html(html);
        }
    });
}

function obtenerEspecialidadesDeProfesional(elemento, idProfesional, seleccion, elementoSede, seleccionSede, etiquetaSinElegir) {
    var html;
    if (etiquetaSinElegir) {
        html = '<option value="0">' + etiquetaSinElegir + '</option>';
    } else {
        html = '<option value="0">Elegir Especialidad</option>';
    }
    if (idProfesional === "0") {
        $(elemento).html(html);
        return;
    }
    var data = {
        id: {
            idProfesional: idProfesional
        }
    };
    $.ajax({
        url: BASE_URL + 'rest/sedeEspProfesional/obtenerEspecialidadesDeProfesional',
        data: JSON.stringify(data),
        dataType: 'json',
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            $.each(data, function (key, value) {
                html += '<option value="' + value.idEspecialidad + '">' + value.nombre + '</option>';
            });
            $(elemento).html(html);
            if (seleccion) {
                $(elemento).val(seleccion);
                if (elementoSede) {
                    obtenerSedesDeEspecialidadProfesional(elementoSede, seleccion, idProfesional, seleccionSede);
                }
            }
        }
    });
}

function obtenerProfesionalesDeSedeEspecialidad(elemento, idSede, idEspecialidad, seleccion, elementoTiempoCita, etiquetaSinElegir) {
    var html;
    if (etiquetaSinElegir) {
        html = '<option value="0">' + etiquetaSinElegir + '</option>';
    } else {
        html = '<option value="0">Elegir Profesional</option>';
    }
    if (idSede === "0" || idEspecialidad === "0") {
        $(elemento).html(html);
        return;
    }
    var data = {
        id: {
            idSede: idSede,
            idEspecialidad: idEspecialidad
        }
    };
    $.ajax({
        url: BASE_URL + 'rest/sedeEspProfesional/obtenerProfesionalesDeSedeEspecialidad',
        data: JSON.stringify(data),
        dataType: 'json',
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            if (elementoTiempoCita) {
                $.each(data, function (key, value) {
                    html += '<option value="' + value.idProfesional + '" data-tiempocita="' + value.tiempoCita + '">' + value.personaUsuario.apellidoPaterno + ' ' + value.personaUsuario.apellidoMaterno + ' ' + value.personaUsuario.nombre + '</option>';
                });
            } else {
                $.each(data, function (key, value) {
                    html += '<option value="' + value.idProfesional + '">' + value.personaUsuario.apellidoPaterno + ' ' + value.personaUsuario.apellidoMaterno + ' ' + value.personaUsuario.nombre + '</option>';
                });
            }
            $(elemento).html(html);
            if (seleccion) {
                $(elemento).val(seleccion);
            }
            if (elementoTiempoCita) {
                $(elementoTiempoCita).val($(elemento + ' option:selected').data('tiempocita'));
            }
        }
    });
}

function obtenerSubespecialidadesDeSedeEspecialidad(elemento, idSede, idEspecialidad, seleccion) {
    var html = '<option value="0">Elegir Subespecialidad</option>';
    if (idEspecialidad === "0") {
        $(elemento).html(html);
        return;
    }
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/sedeEspSubesp/obtenerSedeEspSubesps/{idSede}/{idEspecialidad}',
                {idSede: idSede, idEspecialidad: idEspecialidad}).toString(),
        method: 'GET',
        success: function (data) {
            $.each(data, function (key, value) {
                html += '<option value="' + value.subespecialidad.idSubespecialidad + '">' + value.subespecialidad.nombre + '</option>';
            });
            $(elemento).html(html);
            if (seleccion) {
                $(elemento).val(seleccion);
            }
        }
    });
}

function limpiarSelect(elemento, nombre) {
    $(elemento).html('<option value="0">Elegir ' + nombre + '</option>');
}

function limpiarSelectConsultar(elemento, etiquetaSinElegir) {
    $(elemento).html('<option value="0">' + etiquetaSinElegir + '</option>');
}