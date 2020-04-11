/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var TABLA;
var FORM_CONSULTAR = "#consultarForm";
var FORM_AGREGAR_EDITAR = "#agregarEditarForm";
var ES_EDITAR_DISPONIBILIDAD;
var EVENTO_SELECCIONADO;
var CALENDARIO;

$(document).ready(function () {

    $('#rangoConsulta').daterangepicker({
        locale: DATERANGEPICKER_ES,
        minDate: new Date()
    });

    $('#fechaInicio').daterangepicker({
        locale: DATERANGEPICKER_ES,
        singleDatePicker: true,
        minDate: new Date()
    });

    $('#repeticionFin').daterangepicker({
        locale: DATERANGEPICKER_ES,
        singleDatePicker: true,
        minDate: new Date()
    });

    CALENDARIO = $('#calendar').fullCalendar({
        locale: 'es',
        "allDaySlot": false,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'agendaWeek,month,day,agendaDay,list'
        },
        defaultView: 'agendaWeek',
        selectable: true,
        selectHelper: true,
        longPressDelay: 350,
        timezone: 'local',
        selectConstraint: {
            start: '00:00',
            end: '24:00'
        },
        select: function (start, end, jsEvent, view) {
            ES_EDITAR_DISPONIBILIDAD = false;
            inicializarAgregarEditarDisponibilidadModal(start, end, null, view);
        },
        selectAllow: function (selectInfo) {
            var fechaMin = new Date();
            var fechaSel = moment(selectInfo.start.format(FORMATO_FECHA_HORA), FORMATO_FECHA_HORA).toDate();
            if (fechaSel < fechaMin) {
                console.log("Fecha seleccionada: " + fechaSel + " es menor a actual: " + fechaMin);
                return false;
            }
            return true;
        },
        eventClick: function (event, jsEvent, view) {
            var fechaMin = new Date();
            var fechaSel = moment(event.end).toDate();
            if (fechaSel < fechaMin) {
                console.log("Fecha seleccionada: " + fechaSel + " es menor a actual: " + fechaMin);
                return;
            }
            ES_EDITAR_DISPONIBILIDAD = true;
            EVENTO_SELECCIONADO = event;
            inicializarAgregarEditarDisponibilidadModal(null, null, event);
        },
        viewRender: function (view, element) {
            $("#gestionarMensaje").addClass('hidden');
        }
    });

    bloqueConsultar();
    bloqueAgregarEditar();

});

function bloqueConsultar() {

    var idProfesional = getParameterByName('idProfesional');
    if (idProfesional !== null) {
        $(FORM_CONSULTAR + ' #idProfesional').val(idProfesional);
        obtenerEspecialidadesDeProfesional(FORM_CONSULTAR + " #idEspecialidad", idProfesional, null, null, null, "Todas las Especialidades");
    }

    $(FORM_CONSULTAR + " #idProfesional").change(function () {
        obtenerEspecialidadesDeProfesional(FORM_CONSULTAR + " #idEspecialidad", $(this).val(), null, null, null, "Todas las Especialidades");
        limpiarSelectConsultar(FORM_CONSULTAR + " #idSede", "Todas las Sedes");
    });

    $(FORM_CONSULTAR + " #idEspecialidad").change(function () {
        obtenerSedesDeEspecialidadProfesional(FORM_CONSULTAR + " #idSede", $(this).val(),
                $(FORM_CONSULTAR + " #idProfesional").val(), null, "Todas las Sedes");
    });

    $(FORM_CONSULTAR).submit(function (event) {
        var data = {
            idProfesional: $(FORM_CONSULTAR + ' #idProfesional').val(),
            idEspecialidad: $(FORM_CONSULTAR + ' #idEspecialidad').val(),
            idSede: $(FORM_CONSULTAR + ' #idSede').val()
        };

        CALENDARIO.fullCalendar('removeEventSources');
        consultarCalendario($(FORM_CONSULTAR).attr("action"), data, CALENDARIO, 'datos');
        event.preventDefault();
    });

    $("#limpiarBtn").click(function () {
        limpiarConsultar();
        CALENDARIO.fullCalendar('removeEventSources');
    });

    $(FORM_CONSULTAR).submit();
}

function limpiarConsultar() {
    $(FORM_CONSULTAR + ' #idProfesional').val(0);
    limpiarSelectConsultar(FORM_CONSULTAR + " #idEspecialidad", "Todas las Especialidades");
    limpiarSelectConsultar(FORM_CONSULTAR + " #idSede", "Todas las Sedes");
    $(FORM_CONSULTAR + ' #rangoConsulta').val("");
    $('#gestionarMensaje').addClass('hidden');
}

function bloqueAgregarEditar() {

    $(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idProfesional").change(function () {
        obtenerEspecialidadesDeProfesional(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idEspecialidad", $(this).val());
        limpiarSelect(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idSede", "Sede");
    });

    $(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idEspecialidad").change(function () {
        obtenerSedesDeEspecialidadProfesional(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idSede", $(this).val(),
                $(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idProfesional").val());
    });

    $("#mostrarMasBtn").click(function () {
        $(".mostrarMenos").addClass('hidden');
        $(".mostrarMas").removeClass('hidden');
        $('#repetir').val(true);
    });
    $("#mostrarMenosBtn").click(function () {
        $(".mostrarMas").addClass('hidden');
        $(".mostrarMenos").removeClass('hidden');
        $('#repetir').val(false);
    });

    $('#fechaInicio').change(function () {
        $('#repeticionInicio').val($(this).val());
    });

    $('#repeticionTipo').change(function () {
        actualizarRepeticionTipo($(this).val(), true);
    });
    $('#repeticionCada').change(function () {
        actualizarFrecuencia();
    });
    $('#grupoRepeticionDias input').change(function () {
        actualizarRepeticionDias();
    });
    $('input[name=finRadio]').change(function () {
        actualizarFinaliza();
    });
    $('#repeticionVeces').change(function () {
        actualizarRepeticionVeces($(this).val());
    });
    $('#repeticionFin').change(function () {
        actualizarRepeticionFin($(this).val());
    });

    $(FORM_AGREGAR_EDITAR).validate({
        rules: {
            fechaInicio: {
                required: true
            },
            horaInicio: {
                required: true
            },
            horaFin: {
                required: true
            },
            "sedeEspProfesional.id.idProfesional": {
                notEqual: "0"
            },
            "sedeEspProfesional.id.idEspecialidad": {
                notEqual: "0"
            },
            "sedeEspProfesional.id.idSede": {
                notEqual: "0"
            },
            repeticionFin: {
                required: true
            },
            repeticionVeces: {
                required: true
            }
        }
    });

    $(FORM_AGREGAR_EDITAR).submit(function (event) {
        if (!$(FORM_AGREGAR_EDITAR).valid()) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
            event.preventDefault();
            return false;
        }
        if ($('#repetir').val() === 'true' && $('#repeticionFin').val() !== '' && moment($('#fechaInicio').val(), FORMATO_FECHA) >= moment($('#repeticionFin').val(), FORMATO_FECHA)) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'La fecha en que Finaliza la serie debe ser mayor a la Fecha de Inicio.');
            event.preventDefault();
            return false;
        }
        if ($('#repeticionTipo').val() === "5" && $('#grupoRepeticionDias input:checked').size() === 0) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
            event.preventDefault();
            return false;
        }

        var fechaInicio = moment($('#fechaInicio').val() + " " + $('#horaInicio').val(), FORMATO_FECHA_HORA).toDate();
        var fechaFin;
        if ($('#horaFin').val() === "12:00 AM") {
            fechaFin = moment($('#fechaInicio').val() + " " + $('#horaFin').val(), FORMATO_FECHA_HORA).add(1, 'days').toDate();
        } else {
            fechaFin = moment($('#fechaInicio').val() + " " + $('#horaFin').val(), FORMATO_FECHA_HORA).toDate();
        }

        if (ES_EDITAR_DISPONIBILIDAD) {
            if (fechaFin < EVENTO_SELECCIONADO.end.toDate()) {
                var fechaRedondeada = obtenerFechaRedondeada();
                if (fechaFin < fechaRedondeada) {
                    mostrarMensaje('#agregarEditarMensaje', 'danger', 'La fecha y hora final debe ser mayor o igual a ' + moment(fechaRedondeada).format(FORMATO_FECHA_HORA) + ".");
                    event.preventDefault();
                    return false;
                }
            }
        } else {
            if (fechaInicio < new Date()) {
                mostrarMensaje('#agregarEditarMensaje', 'danger', 'La fecha y hora de inicio debe ser mayor a la actual.');
                event.preventDefault();
                return false;
            }
        }
        if (fechaInicio >= fechaFin) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'La hora fin de disponibilidad debe ser mayor su hora inicial.');
            event.preventDefault();
            return false;
        }

        var data = {
            fechaInicio: fechaInicio,
            fechaFin: fechaFin,
            sedeEspProfesional: {
                id: {
                    idSede: $('#sedeEspProfesional\\.id\\.idSede').val(),
                    idEspecialidad: $('#sedeEspProfesional\\.id\\.idEspecialidad').val(),
                    idProfesional: $('#sedeEspProfesional\\.id\\.idProfesional').val()
                }
            },
            estado: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.disponibilidad.estado : "",
            timezone: moment.tz.guess()
        };

        if ($('#repetir').val() === 'true') {
            data['repeticionTipo'] = $('#repeticionTipo').val();
            data['repeticionCada'] = $('#repeticionCada').val();
            data['repeticionDias'] = obtenerDiasRepeticion();
            data['repeticionVeces'] = $('#repeticionVeces').val();
            data['repeticionInicio'] = moment($('#repeticionInicio').val() + ' ' + $('#horaInicio').val(), FORMATO_FECHA_HORA).toDate();

            if ($('#repeticionFin').val() !== '') {
                data['repeticionFin'] = moment($('#repeticionFin').val(), FORMATO_FECHA_HORA).toDate();
            }
        }

        if (ES_EDITAR_DISPONIBILIDAD) {
            data['editarSiguientes'] = $('input[name="editarSiguientes"]').is(":checked");
            data['sinRepeticion'] = EVENTO_SELECCIONADO.disponibilidad.sinRepeticion;

            if ($('#sedeEspProfesional\\.id\\.idSede').val() == EVENTO_SELECCIONADO.disponibilidad.sedeEspProfesional.id.idSede &&
                    $('#sedeEspProfesional\\.id\\.idEspecialidad').val() == EVENTO_SELECCIONADO.disponibilidad.sedeEspProfesional.id.idEspecialidad &&
                    $('#sedeEspProfesional\\.id\\.idProfesional').val() == EVENTO_SELECCIONADO.disponibilidad.sedeEspProfesional.id.idProfesional) {
                data['idDisponibilidad'] = EVENTO_SELECCIONADO.disponibilidad.idDisponibilidad;
                data['idDisponibilidadEvento'] = EVENTO_SELECCIONADO.idDisponibilidadEvento;
            } else {
                if (!desactivarSiguientes(EVENTO_SELECCIONADO.disponibilidad.idDisponibilidad, EVENTO_SELECCIONADO.start.format(FORMATO_FECHA_GUIONES), true)) {
                    mostrarMensaje('#agregarEditarMensaje', 'danger', 'Elimine las citas pendientes antes de modificar la Sede, Especialidad o Profesional de la disponibilidad.');
                    event.preventDefault();
                    return false;
                }
            }
        }

        $.ajax({
            url: $(FORM_AGREGAR_EDITAR).attr("action"),
            data: JSON.stringify(data),
            dataType: 'json',
            type: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (rpta) {
                if (rpta.status === "ok") {
                    $('#closeBtn').click();
                    $('#idProfesional').val($('#sedeEspProfesional\\.id\\.idProfesional').val());
                    $(FORM_CONSULTAR).submit();
                    mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
                } else {
                    var mensajeError = "";
                    for (var i = 0; i < rpta.errorMessageList.length; i++) {
                        var item = rpta.errorMessageList[i];
                        mensajeError += item.message + "<br>";
                    }
                    mostrarMensaje('#agregarEditarMensaje', 'danger', mensajeError.substring(0, mensajeError.length - 4));
                }
            }
        });
        event.preventDefault();
    });

    $('input[name="editarSiguientes"]').change(function () {
        if ($(this).is(":checked")) {
            $(".mostrarEditarSiguientes").removeClass('hidden');
        } else {
            $(".mostrarEditarSiguientes").addClass('hidden');
        }
    });

    $("#eliminarBtn").click(function () {
        if (EVENTO_SELECCIONADO.disponibilidad.sinRepeticion) {
            confirmarDesactivar('DisponibilidadEvento', 'la Disponibilidad', 'desactivarDisponibilidadEvento(' + EVENTO_SELECCIONADO.disponibilidad.idDisponibilidad + ',' + EVENTO_SELECCIONADO.idDisponibilidadEvento + ')');
        } else {
            $('#eliminarDisponibilidadModal').modal('show');
        }
    });

    $("#soloEstaVezBtn").click(function () {
        confirmarDesactivar('DisponibilidadEvento', 'la Disponibilidad', 'desactivarDisponibilidadEvento(' + EVENTO_SELECCIONADO.disponibilidad.idDisponibilidad + ',' + EVENTO_SELECCIONADO.idDisponibilidadEvento + ')');
    });
    $("#todosLosSgtesBtn").click(function () {
        desactivarSiguientes(EVENTO_SELECCIONADO.disponibilidad.idDisponibilidad, EVENTO_SELECCIONADO.start.format(FORMATO_FECHA_GUIONES));
    });
    $("#todosLosEventosBtn").click(function () {
        desactivarTodo(EVENTO_SELECCIONADO.disponibilidad.idDisponibilidad);
    });

    $('#eliminarDisponibilidadModal').on('hidden.bs.modal', function (e) {
        if ($('.modal').hasClass('in')) {
            $('body').addClass('modal-open');
        }
    });
}

function limpiarAgregarEditar() {
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idProfesional').val(0);
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idEspecialidad').val(0);
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idSede').val(0);

    $('#repetir').val(false);
    $('#repeticionInicio').val($('#fechaInicio').val());
    $("#repeticionTipo").val("1");
    $("#repeticionCada").val("1");
    $('#grupoRepeticionDias input').prop('checked', false);
    $('#finRadio1').prop('checked', false);
    $('#finRadio2').prop('checked', true);
    $('#repeticionFin').prop("disabled", false);
    $('#repeticionVeces').prop("disabled", true);
    $('#repeticionFin').val("");
    $('#repeticionVeces').val("");

    $(".mostrarMas").addClass('hidden');
    $(".mostrarMenos").removeClass('hidden');
    $("#grupoRepeticionDias").addClass('hidden');
    $("#grupoRepeticionCada").removeClass('hidden');
    $('#resumenFrecuencia').html("Todos los d&iacute;as");
    $('#resumenDias').html("");
    $('#resumenFinaliza').html("");
    $('#sufijoRepiteCada').html("d&iacute;as(s)");
    $('input[name="editarSiguientes"]').prop("checked", false);
    $(".mostrarEditarSiguientes").removeClass('hidden');
    $("#mostrarCheckEditarSiguientes").addClass('hidden');

    $('#agregarEditarMensaje').addClass('hidden');
}

function desactivarTodo(idDisponibilidad) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/disponibilidad/desactivarTodos/{idDisponibilidad}',
                {idDisponibilidad: idDisponibilidad}).toString(),
        method: 'GET',
        success: function (data) {
            if (data === "error_citas") {
                $('#closeEliminarBtn').click();
                mostrarMensaje('#agregarEditarMensaje', 'danger', 'Elimine las citas pendientes en la disponibilidad antes de eliminarlo.');
                return;
            }
            $(FORM_CONSULTAR).submit();
            $('#closeEliminarBtn').click();
            $('#closeBtn').click();
            mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
        }
    });
}

function desactivarDisponibilidadEvento(idDisponibilidad, idDisponibilidadEvento) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/disponibilidad/desactivarDisponibilidadEvento/{idDisponibilidad}/{idDisponibilidadEvento}',
                {idDisponibilidad: idDisponibilidad, idDisponibilidadEvento: idDisponibilidadEvento}).toString(),
        method: 'GET',
        success: function (data) {
            if (data === "error_citas") {
                $('#closeConfirmarDesactivarDisponibilidadEventoBtn').click();
                $('#closeEliminarBtn').click();
                mostrarMensaje('#agregarEditarMensaje', 'danger', 'Elimine las citas pendientes en la disponibilidad antes de eliminarlo.');
                return;
            }
            $(FORM_CONSULTAR).submit();
            $('#closeConfirmarDesactivarDisponibilidadEventoBtn').click();
            $('#closeEliminarBtn').click();
            $('#closeBtn').click();
            mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
        }
    });
}

function desactivarSiguientes(idDisponibilidad, fechaInicio, noMostrar) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/disponibilidad/desactivarSiguientes/{idDisponibilidad}/{fechaInicio}',
                {idDisponibilidad: idDisponibilidad, fechaInicio: fechaInicio}).toString(),
        method: 'GET',
        success: function (data) {
            if (data === "error_citas") {
                $('#closeConfirmarDesactivarDisponibilidadEventoBtn').click();
                $('#closeEliminarBtn').click();
                mostrarMensaje('#agregarEditarMensaje', 'danger', 'Elimine las citas pendientes en la disponibilidad antes de eliminarlo.');
                return false;
            }
            if (noMostrar)
                return true;
            $(FORM_CONSULTAR).submit();
            $('#closeConfirmarDesactivarDisponibilidadEventoBtn').click();
            $('#closeEliminarBtn').click();
            $('#closeBtn').click();
            mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
        }
    });
}

function editar(idSede) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/sede/obtener/{idSede}',
                {idSede: idSede}).toString(),
        method: 'GET',
        success: function (data) {
            $('#agregarEditarModal').modal('show');
            $('.mostrarEditar').removeClass('hidden');
            $('.mostrarAgregar').addClass('hidden');
            $(FORM_AGREGAR_EDITAR + ' #codigoLargo').val(data.codigoLargo);
            $(FORM_AGREGAR_EDITAR + ' #nombre').val(data.nombre);
            $(FORM_AGREGAR_EDITAR + ' #ubigeo\\.id\\.coDepartamento').val(data.ubigeo.id.coDepartamento);
            obtenerProvincias(data.ubigeo.id.coDepartamento, FORM_AGREGAR_EDITAR + " #ubigeo\\.id\\.coProvincia", data.ubigeo.id.coProvincia, data.ubigeo.id.coDistrito);
            $(FORM_AGREGAR_EDITAR + ' #direccion').val(data.direccion);
            $(FORM_AGREGAR_EDITAR + ' #estado').val(data.estado);
            $('<input>').attr({
                type: 'hidden',
                id: 'idSede',
                name: 'idSede',
                value: data.idSede
            }).appendTo(FORM_AGREGAR_EDITAR);
        }
    });
}

function inicializarAgregarEditarDisponibilidadModal(start, end, event, view) {
    $("input").removeClass('error');
    $("select").removeClass('error');
    if (ES_EDITAR_DISPONIBILIDAD) {
        limpiarAgregarEditar();
        $("#agregarForm").attr("action", $("#accionEditarDisponibilidad").val());
        $(".mostrarAgregar").addClass('hidden');
        $(".mostrarEditar").removeClass('hidden');

        $('#fechaInicio').data('daterangepicker').setStartDate(moment(event.start).format(FORMATO_FECHA));
        $('#fechaInicio').data('daterangepicker').setEndDate(moment(event.start).format(FORMATO_FECHA));
        $("#horaInicio").val(moment(event.start).format(FORMATO_HORA));
        $("#horaFin").val(moment(event.end).format(FORMATO_HORA));
        if (moment(event.start) < moment()) {
            $("#fechaInicio").prop("disabled", "disabled");
            $("#horaInicio").prop("disabled", "disabled");
        } else {
            $("#fechaInicio").prop("disabled", "");
            $("#horaInicio").prop("disabled", "");
        }

        $("#sedeEspProfesional\\.id\\.idProfesional").val(event.disponibilidad.sedeEspProfesional.id.idProfesional);
        obtenerEspecialidadesDeProfesional("#sedeEspProfesional\\.id\\.idEspecialidad", event.disponibilidad.sedeEspProfesional.id.idProfesional,
                event.disponibilidad.sedeEspProfesional.id.idEspecialidad, "#sedeEspProfesional\\.id\\.idSede", event.disponibilidad.sedeEspProfesional.id.idSede);

        var repeticionTipo = event.disponibilidad.repeticionTipo;
        if (repeticionTipo !== null && repeticionTipo !== "") {
            $("#mostrarCheckEditarSiguientes").removeClass('hidden');
            $(".mostrarEditarSiguientes").addClass('hidden');
            $('input[name="editarSiguientes"]').prop("checked", false);

            $("#repeticionTipo").val(repeticionTipo);
            $("#repetir").val(true);
            $(".mostrarMas").removeClass('hidden');
            $(".mostrarMenos").addClass('hidden');
            $("#mostrarMenosBtn").addClass('hidden');
            $("#repeticionCada").val(event.disponibilidad.repeticionCada);
            $("#repeticionInicio").val(moment(event.disponibilidad.repeticionInicio).format(FORMATO_FECHA));
            $("#repeticionFin").val((event.disponibilidad.repeticionFin !== null && event.disponibilidad.repeticionFin !== "") ? moment(event.disponibilidad.repeticionFin).format(FORMATO_FECHA) : "");
            $("#repeticionVeces").val(event.disponibilidad.repeticionVeces);

            obtenerDatosRepetirDisponibilidadModal(event.disponibilidad.repeticionDias);
        }

    } else {
        $(".mostrarAgregar").removeClass('hidden');
        $(".mostrarEditar").addClass('hidden');

        if (view.name === "month") {
            $("#horaInicio").val("08:00am");
            $("#horaFin").val("09:00am");
        } else {
            $("#horaInicio").val(start.format(FORMATO_HORA));
            $("#horaFin").val(end.format(FORMATO_HORA));
        }
        $('#fechaInicio').data('daterangepicker').setStartDate(start.format(FORMATO_FECHA));
        $('#fechaInicio').data('daterangepicker').setEndDate(start.format(FORMATO_FECHA));
        limpiarAgregarEditar();
    }
    $('#agregarEditarModal').modal('show');
}

function actualizarRepeticionTipo(seleccionado, conResumen) {
    $('#grupoRepeticionDias input').prop('checked', false);
    switch (seleccionado) {
        case "1":
            $("#grupoRepeticionCada").removeClass('hidden');
            $("#grupoRepeticionDias").addClass('hidden');
            $('#sufijoRepiteCada').html("d&iacute;as(s)");
            if (conResumen) {
                if ($('#repeticionCada').val() === "1") {
                    $('#resumenFrecuencia').html("Todos los d&iacute;as");
                } else {
                    $('#resumenFrecuencia').html("Cada " + $('#repeticionCada').val() + " d&iacute;a(s)");
                }
                $('#resumenDias').html("");
            }
            break;
        case "2":
            $("#grupoRepeticionCada").addClass('hidden');
            $("#grupoRepeticionDias").addClass('hidden');
            if (conResumen) {
                $('#resumenFrecuencia').html("Semanalmente, lunes a viernes");
                $('#resumenDias').html("");
            }
            break;
        case "3":
            $("#grupoRepeticionCada").addClass('hidden');
            $("#grupoRepeticionDias").addClass('hidden');
            if (conResumen) {
                $('#resumenFrecuencia').html("Semanalmente, lunes, miercoles y viernes");
                $('#resumenDias').html("");
            }
            break;
        case "4":
            $("#grupoRepeticionCada").addClass('hidden');
            $("#grupoRepeticionDias").addClass('hidden');
            if (conResumen) {
                $('#resumenFrecuencia').html("Semanalmente, martes, jueves");
                $('#resumenDias').html("");
            }
            break;
        default:
            $("#grupoRepeticionCada").removeClass('hidden');
            $("#grupoRepeticionDias").removeClass('hidden');
            $('#sufijoRepiteCada').html("semana(s)");
            if (conResumen) {
                if ($('#repeticionCada').val() === "1") {
                    $('#resumenFrecuencia').html("Semanalmente");
                } else {
                    $('#resumenFrecuencia').html("Cada " + $('#repeticionCada').val() + " semana(s)");
                }
            }

    }
}

function actualizarFrecuencia() {
    switch ($('#repeticionTipo').val()) {
        case "1":
            $('#resumenFrecuencia').html(($('#repeticionCada').val() === "1") ? "Todos los d&iacute;as" : "Cada " + $('#repeticionCada').val() + " d&iacute;a(s)");
            break;
        case "2":
        case "3":
        case "4":
            break;
        default:
            $('#resumenFrecuencia').html(($('#repeticionCada').val() === "1") ? "Semanalmente" : "Cada " + $('#repeticionCada').val() + " semana(s)");
    }
}

function actualizarRepeticionDias() {
    var dia = {
        L: "lunes",
        M: "martes",
        X: "miercoles",
        J: "jueves",
        V: "viernes",
        S: "sabado",
        D: "domingo"
    };
    var repeticionDias = "";
    $('#grupoRepeticionDias input:checked').each(function () {
        repeticionDias = repeticionDias + ", " + dia[$(this).val()];
    });
    $('#resumenDias').html(repeticionDias);
}

function actualizarFinaliza() {
    if ($('input[name=finRadio]:checked').val() === "1") {
        $('#repeticionFin').val("");
        $('#repeticionFin').prop("disabled", true);
        $('#repeticionVeces').prop("disabled", false);
        actualizarRepeticionVeces();
    } else {
        $('#repeticionVeces').val("");
        $('#repeticionVeces').prop("disabled", true);
        $('#repeticionFin').prop("disabled", false);
        actualizarRepeticionFin();
    }
}

function actualizarRepeticionVeces() {
    $('#resumenFinaliza').html(($('#repeticionVeces').val() !== "" && $('#repeticionVeces').val() !== null) ? ", repite " + $('#repeticionVeces').val() + " veces" : "");
}

function actualizarRepeticionFin() {
    $('#resumenFinaliza').html(($('#repeticionFin').val() !== "" && $('#repeticionFin').val() !== null) ? ", hasta: " + $('#repeticionFin').val() : "");
}

function obtenerDatosRepetirDisponibilidadModal(repeticionDias) {

    actualizarRepeticionTipo($('#repeticionTipo').val(), true);
    if ($('#repeticionTipo').val() === "5") {
        var d = repeticionDias.split(",");
        $('#grupoRepeticionDias input').prop('checked', false);
        $('#grupoRepeticionDias input').each(function () {
            for (var i = 0; i < d.length; i++) {
                if ($(this).val() === d[i]) {
                    $($(this)).prop('checked', true);
                }
            }
        });
    }

    if ($('#repeticionVeces').val() === "" || $('#repeticionVeces').val() === null) {
        $('#finRadio1').prop('checked', false);
        $('#finRadio2').prop('checked', true);
        $('#repeticionVeces').prop("disabled", true);
        $('#repeticionFin').prop("disabled", false);
    } else {
        $('#finRadio1').prop('checked', true);
        $('#finRadio2').prop('checked', false);
        $('#repeticionVeces').prop("disabled", false);
        $('#repeticionFin').prop("disabled", true);
    }
    actualizarFrecuencia();
    actualizarRepeticionDias();
    actualizarFinaliza();
}

function obtenerDiasRepeticion() {
    var diasRepeticion;
    switch ($('#repeticionTipo').val()) {
        case "1":
            diasRepeticion = "";
            break;
        case "2":
            diasRepeticion = "L,M,X,J,V";
            break;
        case "3":
            diasRepeticion = "L,X,V";
            break;
        case "4":
            diasRepeticion = "M,J";
            break;
        default:
            diasRepeticion = "";
            $('#grupoRepeticionDias input:checked').each(function () {
                diasRepeticion = diasRepeticion + $(this).val() + ",";
            });
    }

    return diasRepeticion;
}

function obtenerFechaRedondeada() {
    var coeff = 1000 * 60 * 30;
    var date = new Date();  //or use any other date
    date.setSeconds(0, 0);
    var rounded = new Date(Math.ceil(date.getTime() / coeff) * coeff);
    return rounded;
}