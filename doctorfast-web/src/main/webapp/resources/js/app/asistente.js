/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var TABLA;
var TABLA_AGREGAR_EDITAR;
var FORM_CONSULTAR = "#consultarForm";
var FORM_AGREGAR_EDITAR = "#agregarEditarForm";
var LISTA_ESPECIALIDAD_SEDE = [];

$(document).ready(function () {

    TABLA = $('#tabla').DataTable({
        language: DATATABLE_ES,
        dom: 'lrtip',
        "scrollX": true,
        columns: [
            {"data": "personaUsuario.documentoIdentidad"},
            {
                "render": function (data, type, full, meta) {
                    return full.personaUsuario.apellidoPaterno + ' ' + full.personaUsuario.apellidoMaterno + ' ' + full.personaUsuario.nombre;
                }
            },
            {
                "render": function (data, type, full, meta) {
                    var rpta;
                    switch (full.estado) {
                        case "1":
                            rpta = "Activo";
                            break;
                        case "0":
                            rpta = "Inactivo";
                            break;
                    }
                    return rpta;
                }
            },
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="editar" href="javascript:void(0)" onclick="editar(' + full.idAsistente + ');return false;"><i class="fa fa-edit"></i></a>';
                }
            },
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="eliminar" href="javascript:void(0)" onclick="confirmarDesactivar(\'Asistente\',\'el Asistente\',\'desactivar(' + full.idAsistente + ')\');return false;"><i class="fa fa-trash"></a>';
                }
            }
        ]
    });

    bloqueConsultar();
    bloqueAgregarEditar();

});

function bloqueConsultar() {

    $(FORM_CONSULTAR).submit(function (event) {
        var data = {
            personaUsuario: {
                documentoIdentidad: $(FORM_CONSULTAR + ' #personaUsuario\\.documentoIdentidad').val(),
                apellidoPaterno: $(FORM_CONSULTAR + ' #personaUsuario\\.apellidoPaterno').val(),
                apellidoMaterno: $(FORM_CONSULTAR + ' #personaUsuario\\.apellidoMaterno').val(),
                nombre: $(FORM_CONSULTAR + ' #personaUsuario\\.nombre').val()
            },
            estado: $(FORM_CONSULTAR + ' #estado').val()

        };

        consultar(FORM_CONSULTAR, data, TABLA, 'Asistentes');
        event.preventDefault();
    });

    $("#limpiarBtn").click(function () {
        limpiarConsultar();
        TABLA.clear().draw();
    });

    $(FORM_CONSULTAR).submit();
}

function limpiarConsultar() {
    $(FORM_CONSULTAR + ' #personaUsuario\\.documentoIdentidad').val("");
    $(FORM_CONSULTAR + ' #personaUsuario\\.apellidoPaterno').val("");
    $(FORM_CONSULTAR + ' #personaUsuario\\.apellidoMaterno').val("");
    $(FORM_CONSULTAR + ' #personaUsuario\\.nombre').val("");
    $(FORM_CONSULTAR + ' #estado').val("");
    $('#gestionarMensaje').addClass('hidden');
}

function bloqueAgregarEditar() {

    $('#personaUsuario\\.fechaNacimiento').daterangepicker({
        locale: DATERANGEPICKER_ES,
        showDropdowns: true,
        singleDatePicker: true,
        maxDate: new Date()
    });

    $('#agregarEditarModal').on('hidden.bs.modal', function () {
        limpiarAgregarEditar();
        $('.mostrarEditar').addClass('hidden');
        $('.mostrarAgregar').removeClass('hidden');
    });

    $(FORM_AGREGAR_EDITAR).validate({
        rules: {
            "personaUsuario.apellidoPaterno": {
                required: true,
                maxlength: 50
            },
            "personaUsuario.apellidoMaterno": {
                required: true,
                maxlength: 50
            },
            "personaUsuario.nombre": {
                required: true,
                maxlength: 50
            },
            "personaUsuario.fechaNacimiento": {
                required: true
            },
            "personaUsuario.tipoDocumento": {
                notEqual: "0"
            },
            "personaUsuario.documentoIdentidad": {
                required: true,
                maxlength: 13
            },
            "personaUsuario.correo": {
                required: true,
                maxlength: 50
            },
            "personaUsuario.usuario": {
                required: true,
                maxlength: 50
            },
            "personaUsuario.clave": {
                required: true,
                maxlength: 50
            }
        }
    });

    $(FORM_AGREGAR_EDITAR).submit(function (event) {
        if (!$(FORM_AGREGAR_EDITAR).valid()) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
            event.preventDefault();
            return false;
        }

        var data = {
            personaUsuario: {
                idPersonaUsuario: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.idPersonaUsuario').val(),
                apellidoPaterno: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoPaterno').val(),
                apellidoMaterno: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoMaterno').val(),
                nombre: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.nombre').val(),
                fechaNacimiento: moment($(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.fechaNacimiento').val(), FORMATO_FECHA).toDate(),
                correo: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.correo').val(),
                tipoDocumento: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.tipoDocumento').val(),
                documentoIdentidad: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.documentoIdentidad').val(),
                usuario: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.usuario').val(),
                clave: $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.clave').val()
            },
            estado: $(FORM_AGREGAR_EDITAR + ' #estado').val(),
            idAsistente: ($('#idAsistente').val()) ? $('#idAsistente').val() : null
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
                    $(FORM_CONSULTAR).submit();
                    $('#closeBtn').click();
                    if (($('#idAsistente').val())) {
                        mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
                    } else {
                        mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito. Se envío los accesos al correo registrado.');
                    }
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
    $(FORM_AGREGAR_EDITAR + ' #idAsistente').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.idPersonaUsuario').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoPaterno').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoMaterno').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.nombre').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.fechaNacimiento').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.correo').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.tipoDocumento').val("D");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.documentoIdentidad').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.usuario').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.clave').val("");
    $(FORM_AGREGAR_EDITAR + ' #estado').val(1);
    $('#agregarEditarMensaje').addClass('hidden');
    $("input").removeClass('error');
    $("select").removeClass('error');
}

function desactivar(idAsistente) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/asistente/desactivar/{idAsistente}',
                {idAsistente: idAsistente}).toString(),
        method: 'GET',
        success: function () {
            $(FORM_CONSULTAR).submit();
            $('#closeConfirmarDesactivarAsistenteBtn').click();
            mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
        }
    });
}

function editar(idAsistente) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/asistente/obtener/{idAsistente}',
                {idAsistente: idAsistente}).toString(),
        method: 'GET',
        success: function (data) {
            $('#agregarEditarModal').modal('show');
            $('.mostrarEditar').removeClass('hidden');
            $('.mostrarAgregar').addClass('hidden');
            $(FORM_AGREGAR_EDITAR + ' #idAsistente').val(data.idAsistente);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.idPersonaUsuario').val(data.personaUsuario.idPersonaUsuario);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoPaterno').val(data.personaUsuario.apellidoPaterno);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoMaterno').val(data.personaUsuario.apellidoMaterno);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.nombre').val(data.personaUsuario.nombre);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.fechaNacimiento').val(moment(data.personaUsuario.fechaNacimiento).format(FORMATO_FECHA));
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.correo').val(data.personaUsuario.correo);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.tipoDocumento').val(data.personaUsuario.tipoDocumento);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.documentoIdentidad').val(data.personaUsuario.documentoIdentidad);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.usuario').val(data.personaUsuario.usuario);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.clave').val(data.personaUsuario.clave);
            $(FORM_AGREGAR_EDITAR + ' #estado').val(data.estado);
        }
    });
}