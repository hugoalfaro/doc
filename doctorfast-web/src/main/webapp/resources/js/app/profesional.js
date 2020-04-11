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
            {"data": "cop"},
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
                    return '<a id="editar" href="javascript:void(0)" onclick="editar(' + full.idProfesional + ');return false;"><i class="fa fa-edit"></i></a>';
                }
            },
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="eliminar" href="javascript:void(0)" onclick="confirmarDesactivar(\'Profesional\',\'el Profesional\',\'desactivar(' + full.idProfesional + ')\');return false;"><i class="fa fa-trash"></a>';
                }
            },
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    if (full.estado === "1") {
                        return '<a id="disponibilidad" href="' + BASE_URL + 'disponibilidades?idProfesional=' + full.idProfesional + '"><i class="fa fa-calendar"></i></a>';
                    }
                    return '';
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
            cop: $(FORM_CONSULTAR + ' #cop').val(),
            sedeEspProfesional: {
                id: {
                    idEspecialidad: $(FORM_CONSULTAR + ' #sedeEspProfesional\\.id\\.idEspecialidad').val()
                }
            },
            estado: $(FORM_CONSULTAR + ' #estado').val()

        };

        consultar(FORM_CONSULTAR, data, TABLA, 'Profesionales');
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
    $(FORM_CONSULTAR + ' #cop').val("");
    $(FORM_CONSULTAR + ' #sedeEspProfesional\\.id\\.idEspecialidad').val(0);
    $(FORM_CONSULTAR + ' #estado').val("");
    $('#gestionarMensaje').addClass('hidden');
}

function bloqueAgregarEditar() {

    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.documentoIdentidad').blur(function () {
        if ($(this).val() && !$(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.idPersonaUsuario').val()) {
            $.ajax({
                url: URI.expand(BASE_URL + 'rest/profesional/obtenerPorDocumentoIdentidad/{documentoIdentidad}',
                        {documentoIdentidad: $(this).val()}).toString(),
                method: 'GET',
                success: function (data) {
                    if (data === "") {
                        return;
                    }
                    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoPaterno').val(data.personaUsuario.apellidoPaterno);
                    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoMaterno').val(data.personaUsuario.apellidoMaterno);
                    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.nombre').val(data.personaUsuario.nombre);
                    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.fechaNacimiento').val(moment(data.personaUsuario.fechaNacimiento).format(FORMATO_FECHA));
                    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.correo').val(data.personaUsuario.correo);
                    $(FORM_AGREGAR_EDITAR + ' #cop').val(data.cop);
                }
            });
        }
    });

    $('#personaUsuario\\.fechaNacimiento').daterangepicker({
        locale: DATERANGEPICKER_ES,
        showDropdowns: true,
        singleDatePicker: true,
        maxDate: new Date()
    });

    $(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idEspecialidad").change(function () {
        obtenerSedesDeEspecialidad(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idSede", $(this).val());
    });

    TABLA_AGREGAR_EDITAR = $('#agregarEditarTabla').DataTable({
        dom: 'tr',
        autoWidth: false,
        paging: false,
        columns: [
            {"data": "sedeEspecialidad.especialidad.nombre"},
            {"data": "sedeEspecialidad.sede.nombre"},
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="eliminar" href="javascript:void(0)" onclick="confirmarDesactivar(\'EspecialidadSede\',\'la Especialidad y Sede\',\'desactivarEspecialidadSede(' + meta.row + ')\');return false;"><i class="fa fa-trash"></a>';
                }
            }
        ],
        columnDefs: [
            {width: 50, targets: 0},
            {width: 150, targets: 1},
            {width: 150, targets: 2}],
        select: {
            style: 'os',
            selector: 'td:not(:last-child)' // no row selection on last column
        },
        fixedColumns: true
    });

    $('#agregarEditarModal').on('hidden.bs.modal', function () {
        limpiarAgregarEditar();
        TABLA_AGREGAR_EDITAR.clear().draw();
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
            cop: {
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
            },
            "sedeEspProfesional.id.idEspecialidad": {
                notEqual: "0"
            },
            "sedeEspProfesional.id.idSede": {
                notEqual: "0"
            }
        },
        ignore: ".ignore"
    });

    $(FORM_AGREGAR_EDITAR).submit(function (event) {
        if (!$(FORM_AGREGAR_EDITAR).valid()) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
            event.preventDefault();
            return false;
        }

        var lista = TABLA_AGREGAR_EDITAR.rows().data();
        var sedeEspProfesionals = [];
        for (var i = 0; i < lista.length; i++) {
            sedeEspProfesionals[ i ] = lista[ i ];
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
            cop: $(FORM_AGREGAR_EDITAR + ' #cop').val(),
            colorDisponibilidad: $(FORM_AGREGAR_EDITAR + ' #colorDisponibilidad').val(),
            colorCita: $(FORM_AGREGAR_EDITAR + ' #colorCita').val(),
            tiempoCita: $(FORM_AGREGAR_EDITAR + ' #tiempoCita').val(),
            sedeEspProfesionals: sedeEspProfesionals,
            estado: $(FORM_AGREGAR_EDITAR + ' #estado').val(),
            idProfesional: ($('#idProfesional').val()) ? $('#idProfesional').val() : null
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
                    if (($('#idProfesional').val())) {
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

    $("#agregarEspecialidadSedeBtn").click(function () {
        if (!$("#sedeEspProfesional\\.id\\.idEspecialidad").valid()) {
            mostrarMensaje('#agregarEspecialidadSedeMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
            $("#sedeEspProfesional\\.id\\.idSede").addClass('error');
            return false;
        }

        if ($("#sedeEspProfesional\\.id\\.idSede").val() === "0") {
            mostrarMensaje('#agregarEspecialidadSedeMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
            $("#sedeEspProfesional\\.id\\.idSede").addClass('error');
            return false;
        } else {
            $("#sedeEspProfesional\\.id\\.idSede").removeClass('error');
        }

        var lista = TABLA_AGREGAR_EDITAR.rows().data();
        for (var i = 0; i < lista.length; i++) {
            if (lista[ i ].id.idEspecialidad == $(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idEspecialidad").val() && lista[ i ].id.idSede == $(FORM_AGREGAR_EDITAR + " #sedeEspProfesional\\.id\\.idSede").val()) {
                mostrarMensaje('#agregarEspecialidadSedeMensaje', 'danger', 'La sede y especialidad ya han sido agregados');
                return false;
            }
        }

        var data = {
            id: {
                idSede: $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idSede').val(),
                idEspecialidad: $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idEspecialidad').val(),
                idProfesional: ($('#idProfesional').val()) ? $('#idProfesional').val() : null
            },
            sedeEspecialidad: {
                sede: {
                    nombre: $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idSede option:selected').text()
                },
                especialidad: {
                    nombre: $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idEspecialidad option:selected').text()
                }
            }
        };
        TABLA_AGREGAR_EDITAR.row.add(data).draw();
        limpiarAgregarEspecialidadSede();
    });

    $("#limpiarEspecialidadSedeBtn").click(function () {
        limpiarAgregarEspecialidadSede();
    });
}

function limpiarAgregarEditar() {
    $(FORM_AGREGAR_EDITAR + ' #idProfesional').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.idPersonaUsuario').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoPaterno').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoMaterno').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.nombre').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.fechaNacimiento').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.correo').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.tipoDocumento').val("D");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.documentoIdentidad').val("");
    $(FORM_AGREGAR_EDITAR + ' #cop').val("");
    $(FORM_AGREGAR_EDITAR + ' #colorDisponibilidad').val($('#colorDisponibilidadPorDefecto').val());
    $(FORM_AGREGAR_EDITAR + ' #colorCita').val($('#colorCitaPorDefecto').val());
    $(FORM_AGREGAR_EDITAR + ' #tiempoCita').val($(FORM_AGREGAR_EDITAR + ' #tiempoCitaClinica').val());
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.usuario').val("");
    $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.clave').val("");
    $(FORM_AGREGAR_EDITAR + ' #estado').val(1);
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idEspecialidad').val(0);
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idSede').val(0);
    $('#agregarEditarMensaje').addClass('hidden');
    $("input").removeClass('error');
    $("select").removeClass('error');
}

function limpiarAgregarEspecialidadSede() {
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idEspecialidad').val(0);
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idSede').val(0);
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idEspecialidad').removeClass('error');
    $(FORM_AGREGAR_EDITAR + ' #sedeEspProfesional\\.id\\.idSede').removeClass('error');
    $('#agregarEspecialidadSedeMensaje').addClass('hidden');
}

function desactivar(idProfesional) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/profesional/desactivar/{idProfesional}',
                {idProfesional: idProfesional}).toString(),
        method: 'GET',
        success: function (data) {
            if (data === "error_citas") {
                $('#closeConfirmarDesactivarProfesionalBtn').click();
                mostrarMensaje('#gestionarMensaje', 'danger', 'Elimine las citas pendientes del profesional antes de eliminarlo.');
                return;
            }
            $(FORM_CONSULTAR).submit();
            $('#closeConfirmarDesactivarProfesionalBtn').click();
            mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
        }
    });
}

function editar(idProfesional) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/profesional/obtener/{idProfesional}',
                {idProfesional: idProfesional}).toString(),
        method: 'GET',
        success: function (data) {
            $('#agregarEditarModal').modal('show');
            $('.mostrarEditar').removeClass('hidden');
            $('.mostrarAgregar').addClass('hidden');
            $(FORM_AGREGAR_EDITAR + ' #idProfesional').val(data.idProfesional);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.idPersonaUsuario').val(data.personaUsuario.idPersonaUsuario);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoPaterno').val(data.personaUsuario.apellidoPaterno);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.apellidoMaterno').val(data.personaUsuario.apellidoMaterno);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.nombre').val(data.personaUsuario.nombre);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.fechaNacimiento').val(moment(data.personaUsuario.fechaNacimiento).format(FORMATO_FECHA));
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.correo').val(data.personaUsuario.correo);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.tipoDocumento').val(data.personaUsuario.tipoDocumento);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.documentoIdentidad').val(data.personaUsuario.documentoIdentidad);
            $(FORM_AGREGAR_EDITAR + ' #cop').val(data.cop);
            $(FORM_AGREGAR_EDITAR + ' #colorDisponibilidad').val(data.colorDisponibilidad);
            $(FORM_AGREGAR_EDITAR + ' #colorCita').val(data.colorCita);
            $(FORM_AGREGAR_EDITAR + ' #tiempoCita').val((data.tiempoCita) ? data.tiempoCita : $(FORM_AGREGAR_EDITAR + ' #tiempoCitaClinica').val());
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.usuario').val(data.personaUsuario.usuario);
            $(FORM_AGREGAR_EDITAR + ' #personaUsuario\\.clave').val(data.personaUsuario.clave);
            $(FORM_AGREGAR_EDITAR + ' #estado').val(data.estado);

            var lista = [];
            for (var i = 0; i < data.sedeEspProfesionals.length; i++) {
                if (data.sedeEspProfesionals[ i ].estado === '1' &&
                        data.sedeEspProfesionals[ i ].sedeEspecialidad.estado === '1' &&
                        data.sedeEspProfesionals[ i ].sedeEspecialidad.sede.estado === '1' &&
                        data.sedeEspProfesionals[ i ].sedeEspecialidad.especialidad.estado === '1') {
                    lista.push(data.sedeEspProfesionals[ i ]);
                }
            }
            TABLA_AGREGAR_EDITAR.rows.add(lista).draw();
        }
    });
}

function desactivarEspecialidadSede(id) {
    TABLA_AGREGAR_EDITAR.row(id).remove().draw();
    $('#closeConfirmarDesactivarEspecialidadSedeBtn').click();
    mostrarMensaje('#agregarEditarMensaje', 'success', 'La operación se realizó con éxito.');
}