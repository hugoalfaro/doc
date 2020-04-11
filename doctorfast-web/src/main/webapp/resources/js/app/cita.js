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
var ESTA_DISPONIBLE = false;

$(document).ready(function () {

    if (POPUP === "AGREGAR_EDITAR") {
        $('#paciente\\.personaUsuario\\.fechaNacimiento').daterangepicker({
            locale: DATERANGEPICKER_ES,
            showDropdowns: true,
            singleDatePicker: true,
            maxDate: new Date()
        });

        $('#pdfBtn').click(function () {
            pdfMake.createPdf(obtenerDocDefinition()).download('Cita_' + EVENTO_SELECCIONADO.cita.codigo + '.pdf');
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
                inicializarAgregarEditarDisponibilidadModal(start, end, EVENTO_SELECCIONADO);
                $(".desabilitarAlMostrar").prop("disabled", false);
                $("#submitBtn").removeClass("hidden");
            },
            selectAllow: function (selectInfo) {
                if (ESTA_DISPONIBLE) {
                    ESTA_DISPONIBLE = false;
                    var fechaMin = new Date();
                    var fechaSel = moment(selectInfo.start.format(FORMATO_FECHA_HORA), FORMATO_FECHA_HORA).toDate();
                    if (fechaSel < fechaMin) {
                        console.log("Fecha seleccionada: " + fechaSel + " es menor a actual: " + fechaMin);
                        return false;
                    }
                    return true;
                }
                return false;
            },
            eventClick: function (event, jsEvent, view) {
                ES_EDITAR_DISPONIBILIDAD = true;
                EVENTO_SELECCIONADO = event;
                inicializarAgregarEditarDisponibilidadModal(null, null, event);
                var fechaMin = new Date();
                var fechaSel = moment(event.start).toDate();
                if (fechaSel < fechaMin) {
                    $(".desabilitarAlMostrar").prop("disabled", true);
                    $("#eliminarBtn").addClass("hidden");
                    $("#submitBtn").addClass("hidden");
                    $("#closeBtn").prop("disabled", false);
                } else {
                    $(".desabilitarAlMostrar").prop("disabled", false);
                    $("#submitBtn").removeClass("hidden");
                }
            },
            selectOverlap: function (event) {
                if (event.rendering === 'background') {
                    ESTA_DISPONIBLE = true;
                    EVENTO_SELECCIONADO = event;
                }
                return event.rendering === 'background';
            },
            eventRender: function (event, element) {
                if (event.rendering === 'background') {
                    element.append(event.title);
                }
            },
            viewRender: function (view, element) {
                $("#gestionarMensaje").addClass('hidden');
            }
        });

        bloqueAgregarEditar();

    } else {

        $('#pdfBtn').click(function () {
            pdfMake.createPdf(obtenerDocDefinition()).download('Cita_' + EVENTO_SELECCIONADO.cita.codigo + '.pdf');
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
            selectable: false,
            selectHelper: true,
            longPressDelay: 350,
            timezone: 'local',
            eventClick: function (event, jsEvent, view) {
                ES_EDITAR_DISPONIBILIDAD = true;
                EVENTO_SELECCIONADO = event;
                inicializarEditarModal(event);
                var fechaMin = new Date();
                var fechaSel = moment(event.start).toDate();
                if (fechaSel < fechaMin) {
                    $("#estadoCita").prop("disabled", true);
                } else {
                    $("#estadoCita").prop("disabled", false);
                }
            },
            eventRender: function (event, element) {
                if (event.rendering === 'background') {
                    element.append(event.title);
                }
            },
            viewRender: function (view, element) {
                $("#gestionarMensaje").addClass('hidden');
            }
        });

        bloqueEditar();
    }

    bloqueConsultar();

});

function bloqueConsultar() {

    if (VISTA === "CITAS") {

        $(FORM_CONSULTAR + " #idSede").change(function () {
            obtenerEspecialidadesDeSede(FORM_CONSULTAR + " #idEspecialidad", $(this).val());
            limpiarSelectConsultar(FORM_CONSULTAR + " #idProfesional", "Todos los Profesionales");
        });

        $(FORM_CONSULTAR + " #idEspecialidad").change(function () {
            obtenerProfesionalesDeSedeEspecialidad(FORM_CONSULTAR + " #idProfesional", $(FORM_CONSULTAR + " #idSede").val(),
                    $(this).val(), null, null, "Todos los Profesionales");
        });

    } else {
        $(FORM_CONSULTAR + " #idClinica").change(function () {
            obtenerSedesDeClinica(FORM_CONSULTAR + " #idSede", $(this).val());
        });
    }

    $(FORM_CONSULTAR).submit(function (event) {
        CALENDARIO.fullCalendar('removeEventSources');
        obtenerDisponibilidad();
        var data = obtenerParametroConsultar();
        consultarCalendario($(FORM_CONSULTAR).attr("action"), data, CALENDARIO, 'Citas');
        event.preventDefault();
    });

    $("#limpiarBtn").click(function () {
        limpiarConsultar();
        CALENDARIO.fullCalendar('removeEventSources');
    });

    $(FORM_CONSULTAR).submit();
}

function limpiarConsultar() {
    if (VISTA === "CITAS") {
        $(FORM_CONSULTAR + ' #idProfesional').val(0);
        limpiarSelectConsultar(FORM_CONSULTAR + " #idEspecialidad", "Todas las Especialidades");
        $(FORM_CONSULTAR + ' #cop').val("");
    } else {
        $(FORM_CONSULTAR + ' #idClinica').val(0);
    }
    limpiarSelectConsultar(FORM_CONSULTAR + " #idSede", "Todas las Sedes");
    $(FORM_CONSULTAR + ' #estado').val("");
    $('#gestionarMensaje').addClass('hidden');
}

function bloqueAgregarEditar() {

    $(FORM_AGREGAR_EDITAR).validate({
        rules: {
            "paciente.personaUsuario.apellidoPaterno": {
                required: true,
                maxlength: 50
            },
            "paciente.personaUsuario.apellidoMaterno": {
                required: true,
                maxlength: 50
            },
            "paciente.personaUsuario.nombre": {
                required: true,
                maxlength: 50
            },
            "paciente.personaUsuario.correo": {
                required: true,
                maxlength: 50
            },
            "paciente.personaUsuario.telefono": {
                required: true,
                maxlength: 12
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
            fechaAtencion: {
                required: true
            },
            horaAtencion: {
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

        if (!emailCorrecto($(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.correo').val())) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'Se ha ingresado el correo de forma incorrecta.');
            event.preventDefault();
            return false;
        }
        
        var fechaAtencion = moment($('#fechaAtencion').val() + " " + $('#horaAtencion').val(), FORMATO_FECHA_HORA).toDate();

        var data = {
            idCita: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.cita.idCita : "",
            codigo: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.cita.codigo : "",
            paciente: {
                idPaciente: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.cita.paciente.idPaciente : "",
                personaUsuario: {
                    idPersonaUsuario: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.cita.paciente.personaUsuario.idPersonaUsuario : "",
                    apellidoPaterno: $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.apellidoPaterno').val(),
                    apellidoMaterno: $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.apellidoMaterno').val(),
                    nombre: $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.nombre').val(),
                    fechaNacimiento: moment($(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.fechaNacimiento').val(), FORMATO_FECHA).toDate(),
                    tipoDocumento: $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.tipoDocumento').val(),
                    documentoIdentidad: $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.documentoIdentidad').val(),
                    correo: $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.correo').val(),
                    telefono: $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.telefono').val(),
                    sexo: $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.sexo').val()
                }
            },
            sedeEspProfesional: {
                id: {
                    idSede: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.cita.sedeEspProfesional.id.idSede : EVENTO_SELECCIONADO.disponibilidad.sedeEspProfesional.id.idSede,
                    idEspecialidad: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.cita.sedeEspProfesional.id.idEspecialidad : EVENTO_SELECCIONADO.disponibilidad.sedeEspProfesional.id.idEspecialidad,
                    idProfesional: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.cita.sedeEspProfesional.id.idProfesional : EVENTO_SELECCIONADO.disponibilidad.sedeEspProfesional.id.idProfesional
                }
            },
            subespecialidad: ($(FORM_AGREGAR_EDITAR + ' #subespecialidad\\.idSubespecialidad').val() !== "0") ? {idSubespecialidad: $(FORM_AGREGAR_EDITAR + ' #subespecialidad\\.idSubespecialidad').val()} : null,
            fechaAtencion: fechaAtencion,
            tiempoCita: (ES_EDITAR_DISPONIBILIDAD || $('#tiempoCitaProfesor').val() === "") ? $('#tiempoCita').val() : $('#tiempoCitaProfesor').val(),
            estadoCita: $('#estadoCita').val(),
            comentario: $('#comentario').val(),
            estado: (ES_EDITAR_DISPONIBILIDAD) ? EVENTO_SELECCIONADO.cita.estado : ""
        };

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

    $("#mostrarMasBtn").click(function () {
        $(".mostrarMenos").addClass('hidden');
        $(".mostrarMas").removeClass('hidden');
    });
    $("#mostrarMenosBtn").click(function () {
        $(".mostrarMas").addClass('hidden');
        $(".mostrarMenos").removeClass('hidden');
    });

    $("#eliminarBtn").click(function () {
        confirmarDesactivar('Cita', 'la Cita', 'desactivar(' + EVENTO_SELECCIONADO.cita.idCita + ')');
    });
}

function bloqueEditar() {

    $(FORM_AGREGAR_EDITAR).submit(function (event) {

        var data = {
            idCita: EVENTO_SELECCIONADO.cita.idCita,
            estadoCita: $('#estadoCita').val(),
            comentario: $('#comentario').val()
        };

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
}

function limpiarAgregarEditar() {
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.apellidoPaterno').val("");
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.apellidoMaterno').val("");
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.nombre').val("");
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.fechaNacimiento').val("");
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.tipoDocumento').val("");
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.documentoIdentidad').val("");
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.correo').val("");
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.telefono').val("");
    $(FORM_AGREGAR_EDITAR + ' #paciente\\.personaUsuario\\.sexo').val("");
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idProfesional').val(0);
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idEspecialidad').val(0);
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idSede').val(0);
    $(FORM_AGREGAR_EDITAR + ' #subespecialidad\\.idSubespecialidad').val(0);
    $(FORM_AGREGAR_EDITAR + ' #estadoCita').val(1);
    $(FORM_AGREGAR_EDITAR + ' #comentario').val("");
    $('#agregarEditarMensaje').addClass('hidden');
}

function desactivar(idCita) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/cita/desactivar/{idCita}',
                {idCita: idCita}).toString(),
        method: 'GET',
        success: function () {
            $(FORM_CONSULTAR).submit();
            $('#closeConfirmarDesactivarCitaBtn').click();
            $('#closeBtn').click();
            mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
        }
    });
}

function inicializarAgregarEditarDisponibilidadModal(start, end, event, view) {
    $("input").removeClass('error');
    $("select").removeClass('error');
    if (ES_EDITAR_DISPONIBILIDAD) {
        $("#agregarForm").attr("action", $("#accionEditarDisponibilidad").val());
        $(".mostrarAgregar").addClass('hidden');
        $(".mostrarEditar").removeClass('hidden');

        $('#paciente\\.personaUsuario\\.apellidoPaterno').val(event.cita.paciente.personaUsuario.apellidoPaterno);
        $('#paciente\\.personaUsuario\\.apellidoMaterno').val(event.cita.paciente.personaUsuario.apellidoMaterno);
        $('#paciente\\.personaUsuario\\.nombre').val(event.cita.paciente.personaUsuario.nombre);
        if (event.cita.paciente.personaUsuario.fechaNacimiento){
            $('#paciente\\.personaUsuario\\.fechaNacimiento').val(moment(event.cita.paciente.personaUsuario.fechaNacimiento).format(FORMATO_FECHA));
        }else{
            $('#paciente\\.personaUsuario\\.fechaNacimiento').val("");
        }
        $('#paciente\\.personaUsuario\\.tipoDocumento').val(event.cita.paciente.personaUsuario.tipoDocumento);
        $('#paciente\\.personaUsuario\\.documentoIdentidad').val(event.cita.paciente.personaUsuario.documentoIdentidad);
        $('#paciente\\.personaUsuario\\.correo').val(event.cita.paciente.personaUsuario.correo);
        $('#paciente\\.personaUsuario\\.telefono').val(event.cita.paciente.personaUsuario.telefono);
        $('#paciente\\.personaUsuario\\.sexo').val(event.cita.paciente.personaUsuario.sexo);
        $("#sedeEspProfesional\\.id\\.idEspecialidad").val(event.cita.sedeEspProfesional.id.idEspecialidad);

        var idSubespecialidad = (event.cita.subespecialidad === null) ? null : event.cita.subespecialidad.idSubespecialidad;
        obtenerSedesDeEspecialidad("#sedeEspProfesional\\.id\\.idSede", event.cita.sedeEspProfesional.id.idEspecialidad, event.cita.sedeEspProfesional.id.idSede,
                "#sedeEspProfesional\\.id\\.idProfesional", event.cita.sedeEspProfesional.id.idProfesional,
                "#subespecialidad\\.idSubespecialidad", idSubespecialidad, "#tiempoCitaProfesor");

        $('#fechaAtencion').val(moment(event.start).format(FORMATO_FECHA));
        $("#horaAtencion").val(moment(event.start).format(FORMATO_HORA));
        $('#tiempoCita').val(event.cita.tiempoCita);
        $("#estadoCita").val(event.cita.estadoCita);
        $("#comentario").val(event.cita.comentario);

    } else {
        $(".mostrarAgregar").removeClass('hidden');
        $(".mostrarEditar").addClass('hidden');

        limpiarAgregarEditar();

        $("#sedeEspProfesional\\.id\\.idEspecialidad").val(event.disponibilidad.sedeEspProfesional.id.idEspecialidad);
        obtenerSedesDeEspecialidad("#sedeEspProfesional\\.id\\.idSede", event.disponibilidad.sedeEspProfesional.id.idEspecialidad, event.disponibilidad.sedeEspProfesional.id.idSede,
                "#sedeEspProfesional\\.id\\.idProfesional", event.disponibilidad.sedeEspProfesional.id.idProfesional, "#subespecialidad\\.idSubespecialidad", null, "#tiempoCitaProfesor");

//        if (view.name === "month") {
//            $("#horaAtencion").val("08:00am");
////            $("#horaFin").val("09:00am");
//        } else {
        $("#horaAtencion").val(start.format(FORMATO_HORA));
//            $("#horaFin").val(end.format(FORMATO_HORA));
//        }
        $('#fechaAtencion').val(start.format(FORMATO_FECHA));
    }
    $(".mostrarMas").addClass('hidden');
    $(".mostrarMenos").removeClass('hidden');
    $('#agregarEditarModal').modal('show');
}

function inicializarEditarModal(event) {
    var html = obtenerDatosDeCitaHtml(event);
    $("#contenidoPdf").html(html);
    $("#estadoCita").val(event.cita.estadoCita);
    $("#comentario").val(event.cita.comentario);

    $('#agregarEditarModal').modal('show');
}

function obtenerDocDefinition() {
    return {
        header: {
            columns: [
                {alignment: 'right',
                    image: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAABRCAYAAABotpveAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAXEQAAFxEByibzPwAAABh0RVh0U29mdHdhcmUAQWRvYmUgRmlyZXdvcmtzT7MfTgAAABZ0RVh0Q3JlYXRpb24gVGltZQAwNS8yNS8xN4aEz3cAACAASURBVHic7Z13nFx1uf/fz+xuOptCyoQWQlkgDL2r2LisIITiBaRcLmADASki4xVRL5cizgWlCFzAUAREpAZUcAOKIoHQfoBDW0MJLUMaZNlsts7z++M5s3va9NndLJ63r5VXTv3OKZ/z/T7tK6pKRERExEggNtwNiIiIiCiVSLAiIiJGDJFgRUREjBgiwYqIiBgxRIIVERExYogEKyIiYsQQCVZERMSIIRKsiIiIEUP9cDcgjEQyI8A4YDrwaWBHYAdgNrARMNrZtAN4F1gMPAc8DzwFrAQ60qn40DY8IiJiUJF1JdLdEalZwDbA3sCewGbANEy8SqEdWAa8BiwCHgdeTafi79a8wREREUPOsAtWIpmZAOwFHAIcDGxY41O8DswHHgAWplPx7hofPyIiYogYNsFKJDOjgP8Ajgb2GYJTZoEHgdvTqfhtQ3C+iIiIGjMsgpVIZo4CTsOGfcXoBt4A3gRWOX+dQM7ONREbNm6BDSnrSjjmI8Bl6VT892U3PiIiYtgYUsFKJDNbAT8F9gfG5NmsD0gDCzAD+otAG2af6ga606m4OseLAQ2YEX49YDKwHTbE/BLQVKA5HwP3AuekU/H3qvphERERQ8KQCJYjLMdiYjUzZBMF3gPuBm4FXgbW5oSpwvONA3bFhp0HAjPybL4YOCudit9fybkiIiKGjkEXLMeofj5wRp5N/gncDFybTsVXDFIbNgJOAo4BNg3ZJAv8N3BxOhXvGYw2REREVM+gClYimZkGzAPmhqz+ELgSuDqdin8waI3wtmcz4BTgZMKHpDcC30mn4muGoj0RERHlMWiC5fRq7gZ2D1m9ADgvnYo/PignL0IimdkXuBDYLWT1vcDR6VS8c2hbFRERUYxBEaxEMjMD+D1mQ/LzU2zo1ZZv/8bm1r2cfSdV2IQM8GRbS9M/CrQxDpwHfCtk9X3AselUvL3C80dERAwCNResRDLTCNwB7OdbtQY4I52K/yrfvo3NrTsCV2PevUmUFqIQRg825HwIOLOtpWlVnrbWA2cCFwCjfKtvAk5Op+JrK2mAiBRcP9wBuxHrFiIyFsvw+AqwJeb9zgCnqOry4WzbukRNBSuRzABcgxm43bQDx6VT8Xvy7dvY3Lo18EcsX5CqmyUWqAXcBpzQ1tKU15ieSGa+BvwSGOtbdUE6Ff9R2acWOYFw476rdXRjaUTvAW8Bq1R1abnnihj5iMiXgFMxwZroWtUB7KKqrw5Lw9ZBai1YJwNX+RZ/jA2v5ufbr7G5NQb8DPieCGT7oL2t1yKyCndU8lI3vo7xYwXNAvD5tpamvxZp+/HAtXh7Wgocnk7F7y7n3CLyHLBTGbv0YPmPfwX+AjyqqivLOWfEyERETsWcT2F0A9ur6mtD2KR1mpoJViKZSQBPABNci3uBr6dT8V8X2rexuXUK8DCwU0+vMnZ0jAM/PYFJE2L09pXXPhHIKjz1UhcvLV7LqDExgHPaWpp+WsJv+B7wv77F7wF7lBNcKiJ/AprLabePJ7BQj+s0Gjt+YhGRf8PMFoVMH9ural5b7L8aNSkvk0hm6oDL8YoVQKqYWLnaMUkEOjuVKY3ClWdMZ+KESk1YcM61K3juqXbGbBgjq8xubG6tb2tp6i20TzoVvySRzMwBTnAt3hC4FDiyjNNXW2dsL+fvcBH5nqo+X+XxRhQiMgUL+M31dgW4WVWXDV+raouIjAPOJihWC7Dg6eXAeOCdIW5a1YjIZOAwLGWuG+u4zFfVN6s9dq3qYX0d+IJv2SOYF64UFBsA2qOp0NaRrUqwOruzbtmYij38BQXL4TSs9tbOrmWHJ5KZ29Kp+AMVNmcxkMLqdNUzkEo0E9gW84huRHAAvA8wX0ROUtUHKzz3SGQD4Bd4hf9vmM3vk8KOwGd9y24BTlXVvB70EcKGwE8YqLyyFrPTVi1YVVccTSQzG2IGQ/fL9i7w3TJKuaj/H9ls5W1SdfYfaFE9JVrDnFCGczAvY44YcHYimRlfYZMywJ2qeo+q/k5Vb1HVq1X1R8AR2MP778CfsMRuN5sAN4rIvhWeeySSJfcB8y77JLEt3uDllcDFnwCxArt37vslBO9nRdSiRPLhWMKxm+vTqfiLZRyjQtP64JBOxf8E/Na3eG/g0AoPOQpzUwdQ1V5VXaWq96rqfthw9A3fZjOAq0VkswrPP9JQgr3hT5pg+eu+LcVyaD8puAUqS43uX1WC5aTefM23+J/Az8s8lOLrZdWYbAXHvxgzuLs5PpHM5KsyUYySRFlVf4tVmnjBt2oL4GIRCRW+khogMllENheRJhHZVET8NseaISINIrKxiGzp/G0iIv5Yt3wsJXi/MjVq1zQR2cLVpkp7zaWebwMRmRNyHn+YzbuV+ldEZJzvWm9cxrUudNzRzjXaynluSn1eluH94GSBmuQJV2vD+iLB3tVl5UaIt7U0rWhsbq1Z0rEIxGLifuQ721qaOso5RjoVfzuRzNwE/NC1+AtYT2tBLdqZD1VdLCKHAo9iQ8IchwHXl3N+Edkd81juAsSBKZihtxtYISLvYuWkf6+qr1fbdhE5CPg3rNR1nIFhTxeQEZGXsd81X1X7XPuNxnrrM7BYJP+z+R0ReQf7yApmA5yvqukS2vQ5zB64I2Y3nOgcowtYKSJvAn8HHlLVksppOy/vUVhVkNz1vF9V33bE4mgseHprLAj6WCfc5T+d3+YvWjnLCXHIfdgaMIP7vaoasL2KyPbA5zD75yaYnTZ3rTuBD0TkNeBhVb23lN/kOvbngYMwW+4MLD6x1znmy06b/uTbJ4aZN6ZjxvbJrtUNwHEisiveTlJaVf9SVtsqVfVEMjMW82Z8xbV4CTAnnYqHikNjc+v22EWYyYDxeTz28B0REyatbs8yc2o9T1w7i1nxijsTfP/q5aSuWcbEDRrIKq9j7uMuBrwygkXfvwk83tbSFOiOJ5KZmcBLeC/+L4Ez06l4XgO+iCzAXtocTwFzy/VyOaJ1N97e2W+BE1S1YK6jiOyEGT73JH9pHTdvYUL436r6fjntdM73FeB07AUqVoO/E+tBXqGqv3H2n4GJxhbONkrxXunpqnpFgTblAjI/hQl1MRYDdwGXFIuDc4bnz+JNH9sb65XfDHwGb/u/CLwNtFL6yCYN7Kmq/cn4InIA8G1MfEspJ96JXddzVXVRoQ1FZCLm7DiYwterDUu9OzP3TDs9/zSFa9D5uVVVjy1j+6p6WLOAA3zLbgkTq8bm1g2wIdYXsZenrPM+/EwHP7ttJZPGx6irK/wMxwQQeHFxF2Om1Oci5jfHqjTk493G5tYbgR+3tQxc73QqvjSRzNyHN8zhECwfsuyXugLmAy3YEDHHQZhHcXHYDiJSh7nLk3iFthibAt8E9hORs1X1jlJ2ctzz/wscT+mThYwB9gDmicg+mKj4U6AqtmuKSD2W3H4ywVCbQmwB/BdwiIicqKp/K7BtFu+wZwUmUodgv81PHyZU5Zhh+k0ZIjILqyayO/aRL5Ux2MczISJH5+vROKEIv8ZqxxWjEetBbiwiR6nqe047y7VTld1bqkawdmFgui2wG3Knf6PG5tZR2IXYJ5uFnt7wNorAmFHhz+hrS7p4+O4PYf06qCt2v50P82hh9Gihs7vwNYnFoKFeNgJyKTg/9m3yG7yCtRHm4Rl0wVLVrIhci1ewxmG9poBgOS/q5diL6qcPi+1Zg12kOuzBW9+33cbA7SISV9XLC7XPscv8DvhyyOpezPO11jnfGGwo5ha1MZgNdLWqfldEVmC975izzv1AdGJ2n9yy3DDM36Zx2PP27yFt6vK1qR7rIfmT7LcGHhCRE1Q1XzqZ3zEwAaupNtq3Xber3TlbzhhnO/cQog9Lxcn9PmGgFDjOtrMJitVq4COsdPha7PpOxXpf7usXB64RkT1V9SP3AZy819MJitW72PR5K7Bh3nZ4U872Bn7upKJ1OL/1Y+zejHWdX7Fr775/uWtTFhUJlhMo6o8heRobVvj5Jk6MVp3A2AYxGXbpSEzszufze44eHWPilDpik+rtICXQv1UBfYsJdOXM/bbDMY3Nrb9ua2lyi8Hz2O/a1LXsU4lkZsEQzXu4CDNiTnct20NEbg0Zzl9AuFg9in1M/oLNItSDvWBzgH0xW8wc1/YCXCYiy3NDNj/OEOAWwsXqD9gsRY9jdpg+7IXZwdm+GRN+nLbkcuV+gInHNOAKvG7/s7Hhe86GNRa7Nu421WPpVWFida/zl2tTL2aK2BkbKRyK9cRzNAI3icgyVf172CXA+/L5nTHvYGaIhZiovIi9tMdhL/R/YjbJHGngXNdxY5hQrIV+u+YFQK54wPNYGMyfgWdUtT/BX0Q2wuyBp2MjoRxbYdfRbZcF+0j5838fAM5Q1X6PtYhsgIX8uEcrRwDXqOqjjg1ugnPOHzNQXbgLuAh4Bq9Iv0WZVNrDGoP1sNw8gqmrn08BsY5O5TM7jCV1ynR6e9XTF2yoF958v4czLlvGx8t68Rc6mPvp8cy+YTZjGqSKgUKQhjrhkWfXcMG8FcTqBRE2wR5gt2Ctxl50dy9rNyxUYSimDFuNvZjuIojbYA90fxdcRA4Evu/bdw02xPlliLh97Bx3kYjMw8TiO75trhSRRXmM8ScSDPNYBZytqjeEbL/Y+bvbMRifi71U72N2OlT1Uee3TAEu8+0/X1WLRX0fh0XIu1mG2VrChLcNE/NHReT/sKH+4a716wE3iMju/l5JER5xzhmWUvNH6LcxugVrqaoWmxTlN5iNcAkmEqvDNnIcB78QkYewj8ds1+ovi8iFquo23WyN1865HDjRn4zv2DZPdT4MJ2Le3OtxPjiq+pjz2+JYFZScYPVixv8nivy+olQqWFvjVW6ARXlqsMcB+vqUqRPr2H3r8KiAjabWUx8DQoaMMybXM2Py4ExSvWJ1H1nt/6zlHAH9pFPxnkQy8yRewdoGsw8NRaXUDqxX5GYa9qXqAhCRRoJZBR8D33LCJAriPJiniUg7Jlw5pmC9tqPc2zsG5//xHaYNOKwUr4+qvigiR2K9kIYQA3duWOgmToE0FRHZEBMcNyuBg1X1yRLa9LqIHIX9jq+7Vm2JZT/4f28+XgCOKqEkjN++6B9KhrVxrYh8u1RHmaq+IiKXYGaC3Au0AZDAHEE5Nvbt+gLewGk/52Ifp9vziPJkvEUEYtgwtWoqjcP6HN4L3kEw2DGH5P6/syv/hW5fm6UvpxxDyOp2T4hWWIQ1BAP61se8NIOOk/zsF8ZJeG1BhxGsDnFZKWLl41zM++NmP8cdDfTbO44h+MJ9vxwXtapmVfUs55yB1SUuc3MEJuRuzi5FrFxt6sOGTAt9q04VEf+x8/GjEutXVeSer8Cr/zDeGLbJeENlwETazRwKFM9U1RWqek6ZSdk1ibOsVLB2wpu0+QZm+AvDNWyp8GwVsCTTw9//sZZFL3fycUf+a+Vrk98ukWM59kXJMYEhEiwHfwhFHc69E1OQA/C2O02w6kRRVDWL9VLcPZ5JeO1UEwgmgj+B2bPKphapKCKyHr5eIJZ7GGp/K9KeD7FSR26P11RK8549B5QskLXACdCdICKNzt8EX9DoMsw0kKOBoOH+Vby/dwPMhlmTXlEtqVSwtvb9eynei+JmyNNusgpX3bmK/Y96g31PeosXFlddnr0d85jkqMOGhUOF/xq6MwO2wBu8q8CDqhpmTyyKqi7E4ovc7OaKsN+K4P2/wR0rNAxsTPADcqOqdlV4vL/jNegLweT+MJ4YquqgIjJbRL6GORlyc3g+hdlbbxKRU5yhew9BW6v/C/4W5iBw81XgERE5xjHirxNUahjyj3k/JJi0O2wI0JUV2nuy0B2rKpHaoZPgmH5IXITOEMw//Gpn4HpvgteeuAYz+lbD37DYndwHbTb21V2C2T/cH7puvPaQ4WAbvN6nTqro6ajqKhF5Eivxk2OOiBQbkr1S6TlLxYmw/y5m9N4gz2a7Yj3OD4DbMedB3kBcVW0XkZ9gYQrruVZtjwWHt4rI7ZgwLhzOGm2V9rD8kdMfsQ4JFjhDvTqBOqnFULQbEwk3Q9VdHk3Q5rASx+BO0MDZgUVTV8NLeL/K0xkQzU192y7BO1weDjb1/ftNChuNS8Ef57Y+xSdFGdTYPKency/mYMknVm5mYPOBblpsQ1V9BrMDhqUmNWFZEw8D94jIwSU2uebUoloDQN8QxSQNF2GCNVQ/eAJBg/qbDDgH/PaIPvIPz0tlFV7nwwQGjPz+862hRqVDqsCfRvIhpdU+K4T/ftcTrPnvphbXPS9OqMc8vClfOZ7HouB/7vzX7zSAEkwzqvqQc/zr8mwyBovkv0NE7hUR/4d00KlUsPyDrFHO9PCfVHIF99yUXDK5SrYkaDN61tUr97+YMYIzAJXLeLwPeBcDPS5/kvoohsFO6cMvLmOpfMalHP5E1j4Ki+BgD5NOIFh2+x/A/s7yE4GznP/OxeIkW8o9iVM//hRsOHwl4aE7ozHhmi8ic0LWDxqViox/dpdGqn9J1mVGERSsoRoGHUnwPrm/oH7v7GgGosgrZVO893MVA65v/wO8IeXl6w0G/tIzG1O4N1QKfjvtx3i9p37yeZirxokx88+f+QKwv6o+pKrLVbVHVXH+u0pVn8OSmO8v93xOjbYnsUj57bBcz+cIfqx2BC4po+xM1VQqWP5x7vqUnvg6EhlLcNixZLBPKiKbY9Hbbp7Ca9xdird08HjCJ7Ath73wOmTeZUAU/DO4TCTYA6yWsPpohXow/jZNBSoudugkkO/gW/ymE/YxHGyOtwpCFvihk3ScF6eix0VUWItKjeWqepWq7oJVifA/97kenmdXyrt/JVOpYPmLy1X9lVWFnh6FbqWnR6ufl7C2jMNbyqMPK1Q4aDjewasIGnqv94UsvIb3hW3Am8ZT7nknY94iN8+74qVewNKF3PhFtVrCqhoUEovFBHtZR1Rx/u2Az/uWhdmFhgp/yZb3sdCLojglZfIFdZeFqs7D4tH8zoUvOPWwcvh7mwNzNlRJpYL1//B6kWZR+bTygNVgX9uZhY4sHWuV7LolWHG8v68NuwaDyWV4qzSAee88FTGc+KfHfNt91qmbVAln4Q2TWIvXFpLBEmPdHCIifpGrhrBc+EIfxBUEhz7Hioi/uGSpnIL3fnfi5DsOE/7hbSflORVKEgsnCLkgTsHE632LZxEcYbmPFaOE1KNSqFSwHsdrwxmDGYcrZsNp9fzq3Jn88spNmPfDmcyYXK3NdIBCvTUt+M9+/A/+BwR7mUWbUcpGIjLFiXk53beqG/hBnoTXW/DaFccAl4rI7JBtC517H8wN7n7YnsLc2UB/asg8364x4LpyvUYi8gUR8Sdsg30Q/BHweY/tDNVuwnuNxwNXSZklkEXkeLx5o2AVNge1R12Et33/nkWJhfKcZ6BoCISInAY8IyL+ogZh+IeifqFbg/f+jWKgMGNVVCpYrxMsDbGHU3bGT0mGyMbxMY7cp5FTDp7EV/dZj/Fja+B0dB7fsaPzN2F0PYj2rw9smEhmRhMsyPYywReqEL0MxE0FEJE6sVrjp2NpLmFzIF5IsGcDgNpU5v75H7fC6loVfVDEmIulsrhf8Hbg0pCI8ceB23zLtsYqMRS1Zzm/90jgPuAbIuKvybWG4PM1t0gP4AnAXyVib+waTA/ZPqxN3wCuxuthbMeu/XDyKt7nrQE4xzcMy8d3CDoQ+hGRWSJyD3AJVqnk/JD74cc/g9PbWPxfjg/wilodpWUKFKVSVejC6l+52ZegJw1cF7q+dp2m4jhxvVIHb2V6eH9FL6+/1+P5e3dZL++t6EVjmlOqsKqJUwjW336KoMekEBOBXUVkOxHZVUR2FpHdRGSuU0PoNiyW5jKCX84e4AJV/Z8iAcYXYC+tmz2Av4jIaWITE/R320UkJiITRSSBlX2+B2/NLYBrVTUgkqraA5xP8Mu/K/BXEfm2iGwkIv2lORxBmCIiu2GxQrdi3uVZmIvczRqCNqPDgDNFZEMRmSQi64tI/1DJuTYXM1BbK8dcYKGIHOvs625TzDnWLpjYXUtw+PUDVX3Jfw2GmLcIDnkPA653PIgBRGS6iJyHlXkp9J6Pw3JRc2Ec+2PTym3hOB/cxxwvIidj3kc3C90OCecD5581q1lEznOew0nOs1e2Gami1Jx0Kt6XSGYew1s/aQfsq+6vG/0P4OCYwOqOLG8s7aanm5rZqESgt08ZOyrGrJkNXlFsECaMjXHypRka6gW/j0cEunuVhob++7mMYGWGPRio65NjUZmBsttSWbrMO8D5quq3GQRw0itOwB5st+hthJUX+SHwmIi8hdlAJmJZ+Z8hPCTlDqzMcr7zveac73d4q5ZOx3opP8bqTL2NfQQmYc/IXr5DNWBf3/5hpqr2ich92LA4d0frsBm4z8B6+OOBW0TkypyQO0Xuvo7VZXffs82xHug7wOOuNk1w2pTP/nYJcE2+azBUqGqXiPwCE3a3Le9rmBDcir1nHZitaCvM6bBtCcd+RUR+jIl9jrnYfbpLRJ7FwjmmYsLmF6sXcJkMXNyFleXJfSAasGfiJMzLncXu6eeKtdFNNUWmnsW6y7kLKFjCpF+w7gROGj82NvXpVzrZ7tg3QYuPEkvN/5MYrF3Vy7ZzxrDgyk2YOcX7k2ICHZ2aJwdMERF31eX72lqa/LYpfxWA1wl+xQPNL631efkYE4wryinh4YjIQZhR1P8STie8EmcYV2ClWQr+DlX9s4gcgkVG+5PB44QPbf08jQmcnyewHo+/gurGDAxxnoXcXOH9bVroDG/nEQxN2LjENmWxyTjOL7CN33Vfbk1z/wNZ7Fo/5wxZb8X73m6EFWksRpaBnpb/XJdi2RRfdS2bSrAKqZ81wCmqGgguVdWnxYoinuFbNZ1gT75kqjEUvYdNkuDmqEQyM9G9oK2l6UWsrOpqMAO4hvwvq/ZHTOnNKp0f9dK5spfOVYX/1q7qhRW9rPiwl2xYt02tDHx9Xdhfv1j1Yg+CxwCcSGZmE5xo4y6KT5leygwtfjoxb985wL6q+s0y6w0B/ZHKB2F1psrNp3sBOEJVT1fVkqqpOuWDv4yJZDn95rVYKsmBToUI/3F7sWtRqKZXd9g5VfVZ7L5dSQHbYR4WAAcUESsw0Rjv+3c5sYj+oWcxuxFqE4McgpUPKpU7sERw97vu8bg61/oErCdeKq3AIar6eIFtfkTQrlgVFfew0ql4VyKZuRMr5pYjjqnyz9zbtrU0Xd/Y3PqqwOEN9bIZA9njub9RwM4CY9d2Zpk2pZ7jj16fyRNi9BZxyArQ3pllw2n1TByf10i2Ehvq9TJQL7sPc4e/DTzS1tL0YMh+SbwPZRfwh3QqXuxLOp/iCcidTrvex3pti4EP1FWbu1Kccr4Xisgd2Nx4c7HhwRQGXpQs1kP+ALPJzQf+Fva1LOF8b4nIScD/YS/Ul7BhmHt+wV4sKv+fwIPAPcVsQ6q62vHa3YdNJ7cLA5NTjKNAsrETVHmaiFyHDWP2d9o0mQEXu2K92XcwR8L9wGMl1uhqw2yPE7HnqZfygokXYt67dmf/Yr12a7DqH8TmNzwU+107YvfVfZ1XYmE3v8Zsk1/Cek912HMfqCqhqmuBM0TkAeBYbKjmnlcS7P694hzz9hICV9sdm9eD2P3bmQE7dw8V5F5WPC8hgNObWoDVOM+xBNgtnYqH1gVqbG4dj10Ed5d6feCPMWHL1R/2MadpNIuu35QJFXoKVeHMK5Zx+Q3LmRhvIKvcj0Xp5qaSytVDb29raQo1nieSmW2wHo/7y/cAcFg6FS/Y+xCreV3MxZAF+oYietoxtm+M2eM2wH5/Fyaqi4CPSu1RlXAusIdya2xKqkZMYNqwl/SfldTqcozl62FDlXrsRV+qReZndO0/AQuNyM3TKNhL8yI2OcJqDZmwtMDxwJtHqUBPqaVXHIP2KExABHsWykrYdkI2tsDskJOwZ+pD7AP0qjp12x3vqttO2VPouRMrABjHJpppwj7aK7Dh+1OVFF0Umyg3d/9GYeLXpuXVyq9u5ud0Kr46kczMwytYs7Ceydlh+7S1NK3Bp6yNza29QDYXH5vNWsnkSgUrhG4g09bSVI44nItXrPqAG4qJFfR3sautFlAznAfXHxE/WOcC67U8TdCTXM1xO7FeaUUF8lS1HetlBybMrfB4UMUkJE45Zv9cjOUeYw02jC8YE+iIaMlDY+fj9TZmJqkJjuewiyqnrK+FItxFsIDbcYlkppzI5zH4eiQ1KLrnxl0epSiJZOYIgq72BQRtdhEREUNI1YKVTsVXYsZTd1d4GpBKJDOlisRgJ+JMpsTeZCKZmY5NiOluexeQyjMrUERExBBRkzFXOhW/g2Bg257AL0o8RM1Lc1RimnNqes0j6KK/MZ2KlzwjTERExOBQy6J7pxF0938rkcycVcK+A2NsVWIiNNRXrl8iMLpB3NEmHZQWI3MRwdlRXiV8KqqIiIghpmazk6ZT8bcTyczpWNF7NxcmkpkV6VT85gK7dwJLVJlDTOjpUzIre4nFnJIzZRCLmdC1dfRBneTGmu9RxOiYSGbOIRjZ3Ql82xn2RkREDDNVhTWEkUhmLsI7ezDYi39qOhX3Z/kD0NjcirPPRVm1oM7J69VRFys6S0kINunE6jV9rO1UYtaHPBO4rK0lmOCeSGbA0lbOIxiKcHo6Fb+izAZEREQMEoMhWGOxVI3/8K3qxnKJfp5OxQOxT43NrdtgcTqTVC3Hr5qmNdT3R7FngM+2tTQFyoM4ToHzsBpQ/jHopcB/pVPxdSY8ISLiX52aCxZAIplpBG4mGBoAZtT+SToVD0TJNja3HoLlseUth1EmbwOntbU0BcIREsnMFlhE/ldC9rsBOCWdiq9TU5dFRPyrMyiCBZBIZsZgqQthgvA8JlqBAvmNza0J4GgswrYeM5aXaoFXBtIkWoHb2lqaAukf5OsFCAAAAgNJREFUiWTmGKw8SliBu3mY3aqc8jERERFDwKAJFvQPuS4HvhGyOgv8CrginYqH5pQ1NrcSZncqRKF9EsnMnlj2+FdDN4AUcE46FR/uefYiIiJCGFTBAkgkM/VYms55BOd6A5uR5W7gunQqXpO0iZA27IblEh5AeGmLduB76VT82sE4f0RERG0YdMHKkUhmDsCKhCXybLISeBQr0/tnYE2lw7JEMjMKi25vxqpJ7Ill1YexCBOrkmYhiYiIGD6GTLAAEsnMNCw48zAKz7LzPvBXbCqjlzHj+RosWbQP8zgK1mPLTSE+DpuLbjssy/zTWIpQPpZjpXrPS6fiHQW2i4iIWEcYUsHKkUhm9gG+i/WASgleXY2J1lJMuFZjxvVGLLF5JuZZbCzhWG1YIvPF6VT8mbIbHxERMWwMi2DlSCQzh2JDtv3wFsobDD4E/gDclE7FK6mvHhERMcwMq2BBf6T5nphoHYhVlawlC7HE7IfTqfizNT52RETEEDLsguXGKe0yG6uMuQtmj5qJt6xtProww/17WEGzZ7ACckuiXMCIiE8G65RguXE8fWOwANKdsCmpNsPEayYWx7UUG+rlqmk+g5VoXhsFfkZEfPJYZwUrIiIiwk8t62FFREREDCqRYEVERIwYIsGKiIgYMUSCFRERMWKIBCsiImLEEAlWRETEiOH/AxFHUEu+0vzAAAAAAElFTkSuQmCC',
                    width: 100},
                {text: 'Fecha : ' + moment().format(FORMATO_FECHA), alignment: 'right'}

            ],
            margin: [20, 10]
        },
        content: obtenerDatosDeCita(EVENTO_SELECCIONADO),
        footer: function (currentPage, pageCount) {
            return {
                columns: [
                    {text: 'Página : ' + currentPage.toString() + '/' + pageCount, alignment: 'right'}
                ],
                margin: [20, 0]
            };
        }
    };
}

function obtenerDatosDeCita(event) {
    var clinica = obtenerClinica(event.cita.sedeEspProfesional.profesional.idProfesional).toUpperCase();
    return [
        {text: clinica, alignment: 'right', bold: true, fontSize: 13, margin: [0, 10, 0, 10]},
        {text: 'CITA', alignment: 'center', bold: true, fontSize: 18, margin: [0, 10, 0, 5]},
        {text: event.cita.codigo, alignment: 'center', bold: true, fontSize: 13, margin: [0, 0, 0, 10]},
        {text: 'INFORMACIÓN DE CITA', alignment: 'left', bold: true, fontSize: 13, margin: [0, 10, 0, 5]},
        {
            columns: [
                [
                    {text: [{text: 'Clinica : ', bold: true}, obtenerClinica(event.cita.sedeEspProfesional.profesional.idProfesional)]},
                    {text: [{text: 'Sede : ', bold: true}, event.cita.sedeEspProfesional.sedeEspecialidad.sede.nombre]},
                    {text: [{text: 'Especialidad : ', bold: true}, event.cita.sedeEspProfesional.sedeEspecialidad.especialidad.nombre]},
                    {text: [{text: 'Fecha : ', bold: true}, moment(event.start).format(FORMATO_FECHA_HORA)]},
                    {text: [{text: 'Profesional : ', bold: true}, event.cita.sedeEspProfesional.profesional.personaUsuario.apellidoPaterno
                                    + " " + event.cita.sedeEspProfesional.profesional.personaUsuario.apellidoMaterno
                                    + " " + event.cita.sedeEspProfesional.profesional.personaUsuario.nombre]}
                ],
                [
                    {text: [{text: 'Departamento : ', bold: true}, event.cita.sedeEspProfesional.sedeEspecialidad.sede.ubigeo.noDepartamento]},
                    {text: [{text: 'Provincia : ', bold: true}, event.cita.sedeEspProfesional.sedeEspecialidad.sede.ubigeo.noProvincia]},
                    {text: [{text: 'Distrito : ', bold: true}, event.cita.sedeEspProfesional.sedeEspecialidad.sede.ubigeo.noDistrito]}
                ]
            ]
        },
        {text: 'INFORMACIÓN DE PACIENTE', alignment: 'left', bold: true, fontSize: 13, margin: [0, 20, 0, 5]},
        {
            columns: [
                [
                    {text: [{text: 'Apellido Paterno : ', bold: true}, event.cita.paciente.personaUsuario.apellidoPaterno]},
                    {text: [{text: 'Apellido Materno : ', bold: true}, event.cita.paciente.personaUsuario.apellidoMaterno]},
                    {text: [{text: 'Nombre : ', bold: true}, event.cita.paciente.personaUsuario.nombre]},
                    {text: [{text: 'Tipo de Documento : ', bold: true}, obtenerTipoDocumento(event.cita.paciente.personaUsuario.tipoDocumento)]},
                    {text: [{text: 'Número de Documento : ', bold: true}, event.cita.paciente.personaUsuario.documentoIdentidad]}
                ],
                [
                    {text: [{text: 'Sexo : ', bold: true}, obtenerSexo(event.cita.paciente.personaUsuario.sexo)]},
                    {text: [{text: 'Fecha Nacimiento : ', bold: true}, moment(event.cita.paciente.personaUsuario.fechaNacimiento).format(FORMATO_FECHA)]},
                    {text: [{text: 'Teléfono : ', bold: true}, ((event.cita.paciente.personaUsuario.telefono) ? event.cita.paciente.personaUsuario.telefono : "")]},
                    {text: [{text: 'Correo : ', bold: true}, ((event.cita.paciente.personaUsuario.correo) ? event.cita.paciente.personaUsuario.correo : "")]}
                ]
            ]
        }
    ];
}

function obtenerDatosDeCitaHtml(event) {
    var html = "<h3 class='m-0'>Información de Cita</h3><br>";
    html += agregarDato("Especialidad", event.cita.sedeEspProfesional.sedeEspecialidad.especialidad.nombre);
    html += agregarDato("Profesional", event.cita.sedeEspProfesional.profesional.personaUsuario.apellidoPaterno
            + " " + event.cita.sedeEspProfesional.profesional.personaUsuario.apellidoMaterno
            + " " + event.cita.sedeEspProfesional.profesional.personaUsuario.nombre);
    html += agregarDato("Clinica", obtenerClinica(event.cita.sedeEspProfesional.profesional.idProfesional));
    html += agregarDato("Sede", event.cita.sedeEspProfesional.sedeEspecialidad.sede.nombre);
    html += agregarDato("Fecha", moment(event.start).format(FORMATO_FECHA));
    html += agregarDato("Hora", moment(event.start).format(FORMATO_HORA));

    html += "<br><br><h3 class='m-0'>Información de Paciente</h3><br>";
    html += agregarDato("Apellido Paterno", event.cita.paciente.personaUsuario.apellidoPaterno);
    html += agregarDato("Apellido Materno", event.cita.paciente.personaUsuario.apellidoMaterno);
    html += agregarDato("Nombre", event.cita.paciente.personaUsuario.nombre);
    html += agregarDato("Fecha Nacimiento", moment(event.cita.paciente.personaUsuario.fechaNacimiento).format(FORMATO_FECHA));
    html += agregarDato("Tipo de Documento", obtenerTipoDocumento(event.cita.paciente.personaUsuario.tipoDocumento));
    html += agregarDato("Número de Documento", event.cita.paciente.personaUsuario.documentoIdentidad);
    html += agregarDato("Sexo", obtenerSexo(event.cita.paciente.personaUsuario.sexo));
    html += agregarDato("Correo", event.cita.paciente.personaUsuario.correo);
    html += agregarDato("Teléfono", event.cita.paciente.personaUsuario.telefono);
    html += agregarDato("Estado de Cita", obtenerEstado(event.cita.estadoCita));
    html += agregarDato("Comentario", event.cita.comentario);

    return html;
}

function agregarDato(etiqueta, valor) {
    valor = (valor) ? valor : '-';
    return "<p class='col-sm-6'><b>" + etiqueta + " : </b>" + valor + "</p>";
}

function obtenerTipoDocumento(valor) {
    switch (valor) {
        case 'D':
            return 'DNI';
        case 'R':
            return 'RUC';
        case 'L':
            return 'Libreta Electoral';
        case 'C':
            return 'Carnet de extranjería';
        default:
            return '';
    }
}

function obtenerSexo(valor) {
    switch (valor) {
        case 2:
            return 'Femenino';
        case 1:
            return 'Masculino';
        default:
            return '';
    }
}

function obtenerEstado(valor) {
    switch (valor) {
        case '1':
            return 'Registrado';
        case '2':
            return 'Confirmado';
        case '3':
            return 'En Atención';
        case '4':
            return 'Finalizado';
        default:
            return '';
    }
}

function obtenerClinica(valor) {
    if (VISTA === "CITAS") {
        return $(FORM_AGREGAR_EDITAR + " #nombreClinica").val();
    }
    return $(FORM_CONSULTAR + " #idClinica").find("[data-profesional='" + valor + "']").text();
}

function obtenerDisponibilidad() {
    var data = obtenerParametroDisponibilidad();
    consultarCalendario(BASE_URL + 'rest/disponibilidad/obtenerDisponibilidades', data, CALENDARIO, 'horarios disponibles');
}

function obtenerParametroDisponibilidad() {
    if (VISTA === "CITAS") {
        return {
            idProfesional: $(FORM_CONSULTAR + ' #idProfesional').val(),
            idEspecialidad: $(FORM_CONSULTAR + ' #idEspecialidad').val(),
            idSede: $(FORM_CONSULTAR + ' #idSede').val(),
            cop: $(FORM_CONSULTAR + ' #cop').val(),
            rendering: "background"
        };
    }
    return {
        idClinica: $(FORM_CONSULTAR + ' #idClinica').val(),
        idSede: $(FORM_CONSULTAR + ' #idSede').val(),
        idProfesional: ($(FORM_CONSULTAR + ' #idClinica').val() === "0") ? "" : $(FORM_CONSULTAR + ' #idClinica option:selected').data('profesional'),
        rendering: "background"
    };
}

function obtenerParametroConsultar() {
    if (VISTA === "CITAS") {
        return {
            idProfesional: $(FORM_CONSULTAR + ' #idProfesional').val(),
            idEspecialidad: $(FORM_CONSULTAR + ' #idEspecialidad').val(),
            idSede: $(FORM_CONSULTAR + ' #idSede').val(),
            cop: $(FORM_CONSULTAR + ' #cop').val(),
            estado: $(FORM_CONSULTAR + ' #estado').val()
        };
    }
    return {
        idClinica: $(FORM_CONSULTAR + ' #idClinica').val(),
        idSede: $(FORM_CONSULTAR + ' #idSede').val(),
        idProfesional: ($(FORM_CONSULTAR + ' #idClinica').val() === "0") ? "" : $(FORM_CONSULTAR + ' #idClinica option:selected').data('profesional'),
        estado: $(FORM_CONSULTAR + ' #estado').val()
    };
}

function limpiarFechaNacimiento(){
    $('#paciente\\.personaUsuario\\.fechaNacimiento').val('');
}