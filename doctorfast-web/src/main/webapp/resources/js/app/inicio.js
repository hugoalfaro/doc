/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var DATATABLE_ES = {
    "sProcessing": "Procesando...",
    "sLengthMenu": "Mostrar _MENU_ registros",
    "sZeroRecords": "No se encontraron resultados",
    "sEmptyTable": "Ningún dato disponible en esta tabla",
    "sInfo": "Del _START_ al _END_ de _TOTAL_ registros",
    "sInfoEmpty": "0 registros",
    "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
    "sInfoPostFix": "",
    "sSearch": "Palabra clave:",
    "sUrl": "",
    "sInfoThousands": ",",
    "sLoadingRecords": "Cargando...",
    "oPaginate": {
        "sFirst": "Primero",
        "sLast": "Último",
        "sNext": "Siguiente",
        "sPrevious": "Anterior"
    },
    "oAria": {
        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
    }
};
var FORMATO_FECHA = "DD/MM/YYYY";
var FORMATO_FECHA_GUIONES = "DD-MM-YYYY";
var FORMATO_FECHA_GUIONES_INVERTIDO = "YYYY-MM-DD";
var FORMATO_HORA = "h:mm A";
var FORMATO_FECHA_HORA = FORMATO_FECHA + " " + FORMATO_HORA;
var FORMATO_FECHA_REPORTE = FORMATO_FECHA + " h:mm:ss A";
var DATERANGEPICKER_ES = {
    format: FORMATO_FECHA,
    "daysOfWeek": ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
    "monthNames": ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"]
};

function validaNumeros(e, admitePunto) {
    var keynum;

    if (window.event) {// IE
        keynum = e.keyCode;

    } else if (e.which) {// Netscape/Firefox/Opera    
        keynum = e.which;
    }

    // NOTE: Backspace = 8, Enter = 13, Delete = 127, '0' = 48, '9' = 57, '.' = 46
    var resultado;
    if (admitePunto)
        resultado = ((keynum >= 48 && keynum <= 57) || keynum === 8 || keynum === 13 || keynum === 127 || keynum === 46);
    else
        resultado = ((keynum >= 48 && keynum <= 57) || keynum === 8 || keynum === 13 || keynum === 127);
    return resultado;
}

function validaLetras(e) {
    var keynum;

    if (window.event) {
        keynum = e.keyCode;
    } else if (e.which) {
        keynum = e.which;
    }

    // NOTE: A-Z, a-z, Space = 32, Backspace = 8, Enter = 13
    return  ((keynum >= 65 && keynum <= 90) || (keynum >= 97 && keynum <= 122)
            || (keynum === 32) || (keynum === 8) || (keynum === 13)
            || (keynum >= 192 && keynum <= 214) || (keynum >= 217 && keynum <= 220) || (keynum >= 224 && keynum <= 246) || (keynum >= 249 && keynum <= 252));
}

function validaFecha(e) {
    var keynum;

    if (window.event) {// IE
        keynum = e.keyCode;

    } else if (e.which) {// Netscape/Firefox/Opera    
        keynum = e.which;
    }

    // NOTE: '0' = 48, '9' = 57, Backspace = 8, Enter = 13, Delete = 127, '/' = 47
    return ((keynum >= 48 && keynum <= 57) || keynum === 8 || keynum === 13 || keynum === 127 || keynum === 47);
}

function emailCorrecto(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function getParameterByName(name, url) {
    if (!url)
        url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
    if (!results)
        return null;
    if (!results[2])
        return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function obtenerParametroGetOCero(parametro) {
    var valor = getParameterByName(parametro);
    if (valor === null || valor === '')
        return "0";
    return valor;
}

function mostrarMensaje(elemento, tipo, mensaje) {
    $(elemento).html('<div class="alert alert-' + tipo + ' animated shake" id="alerta"><a href="javascript:void(0)" class="close" onClick="cerrarAlerta(this)" aria-label="close">&times;</a>' + mensaje + '</div>');
    $(elemento).removeClass("hidden");
    $(elemento).fadeTo(5000, 500).slideUp(500, function () {
        $(elemento).slideUp(500);
    });
}

function consultarCalendario(url, data, calendario, nombre, inicio, fin) {
    calendario.fullCalendar('addEventSource', function (start, end, timezone, callback) {
        if (inicio && fin) {
            var inicio1 = moment(inicio, FORMATO_FECHA);
            var inicio2 = moment(start.toISOString(), FORMATO_FECHA_GUIONES_INVERTIDO);
            var fin1 = moment(fin, FORMATO_FECHA);
            var fin2 = moment(end.toISOString(), FORMATO_FECHA_GUIONES_INVERTIDO);
            data["fechaDesde"] = ((inicio1.isAfter(inicio2))?inicio1:inicio2).toDate();
            data["fechaHasta"] = ((fin1.isAfter(fin2))?fin2:fin1).toDate();
        } else {
            data["fechaDesde"] = moment(start.toISOString(), FORMATO_FECHA_GUIONES_INVERTIDO).toDate();
            data["fechaHasta"] = moment(end.toISOString(), FORMATO_FECHA_GUIONES_INVERTIDO).toDate();
        }

        $.ajax({
            url: url,
            data: JSON.stringify(data),
            dataType: "json",
            type: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (doc) {
                callback(doc);
                if (doc.length === 0 && nombre) {
                    mostrarMensaje('#gestionarMensaje', 'info', 'No se encontraron ' + nombre);
                }
            }
        });
    });
}