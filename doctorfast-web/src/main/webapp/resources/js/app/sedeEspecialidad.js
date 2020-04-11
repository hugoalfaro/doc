/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var TABLA;
var TABLA_SUBESPECIALIDADES;
var TABLA_AGREGAR_EDITAR;
var FORM_CONSULTAR = "#consultarForm";
var FORM_AGREGAR_EDITAR = "#agregarEditarForm";
var FORM_AGREGAR_SUBESPECIALIDAD = "#agregarSubespecialidadForm";
var MODAL_AGREGAR_EDITAR = "#agregarEditarModal";
var MODAL_SUBESPECIALIDAD = "#subespecialidadesModal";
var DATA_SUBESPECIALIDADES;

$(document).ready(function () {

    TABLA = $('#tabla').DataTable({
        language: DATATABLE_ES,
        dom: 'lrtip',
        "scrollX": true,
        columns: [
            {"data": "especialidad.codigo"},
            {"data": "especialidad.nombre"},
            {"data": "sede.nombre"},
            {"data": "estado"},
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="editar" href="javascript:void(0)" onclick="editar(' + full.id.idSede + ',' + full.id.idEspecialidad + ');return false;"><i class="fa fa-edit"></i></a>';
                }
            },
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="eliminar" href="javascript:void(0)" onclick="confirmarDesactivar(\'Especialidad\',\'la Especialidad\',\'desactivar(' + full.id.idSede + ',' + full.id.idEspecialidad + ')\');return false;"><i class="fa fa-trash"></a>';
                }
            },
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="subespecialidades" href="javascript:void(0)" onclick="subespecialidades(' + full.id.idSede + ',\'' + full.sede.nombre + '\',' + full.id.idEspecialidad + ',\'' + full.especialidad.nombre + '\');return false;"><i class="fa fa-search"></a>';
                }
            }
        ],
        columnDefs: [{"targets": 3, "data": "estado", "render": function (data) {
                    var rpta;
                    switch (data) {
                        case "1":
                            rpta = "Activo";
                            break;
                        case "0":
                            rpta = "Inactivo";
                            break;
                    }
                    return rpta;
                }
            }]
    });

    bloqueConsultar();
    bloqueAgregarEditar();
    bloqueSubespecialidades();

});

function bloqueConsultar() {

    $(FORM_CONSULTAR).submit(function (event) {
        var data = {
            especialidad: {
                codigo: $(FORM_CONSULTAR + ' #especialidad\\.codigo').val(),
                nombre: $(FORM_CONSULTAR + ' #especialidad\\.nombre').val()
            },
            estado: $(FORM_CONSULTAR + ' #estado').val(),
            sede: {
                "idSede": $(FORM_CONSULTAR + ' #sede\\.idSede').val()
            }
        };

        consultar(FORM_CONSULTAR, data, TABLA, 'Especialidades');
        event.preventDefault();
    });

    $(FORM_CONSULTAR + " #limpiarBtn").click(function () {
        limpiarConsultar();
        TABLA.clear().draw();
    });

    $(FORM_CONSULTAR).submit();
}

function limpiarConsultar() {
    $(FORM_CONSULTAR + ' #especialidad\\.codigo').val("");
    $(FORM_CONSULTAR + ' #especialidad\\.nombre').val("");
    $(FORM_CONSULTAR + ' #sede\\.idSede').val(0);
    $(FORM_CONSULTAR + ' #estado').val("");
    $('#gestionarMensaje').addClass('hidden');
}

function bloqueAgregarEditar() {

    TABLA_AGREGAR_EDITAR = $('#agregarEditarTabla').DataTable({
        dom: 'tr',
        autoWidth: false,
        paging: false,
        columns: [
            {
                sortable: false,
                data: "estado",
                render: function (data, type, row, meta) {
                    if (type === 'display') {
                        if (data === "1") {
                            return '<input type="checkbox" onclick="actualizarEstado(this, ' + meta.row + ');" checked>';
                        } else {
                            return '<input type="checkbox" onclick="actualizarEstado(this, ' + meta.row + ');">';
                        }
                    }
                    return data;
                },
                className: "dt-body-center"
            },
            {data: "sede.nombre"},
            {
                sortable: false,
                data: "costoConsulta",
                render: function (data, type, row, meta) {
                    if (type === 'display') {
                        if (data === null) {
                            return '<input type="input" onchange="actualizarCosto(this, ' + meta.row + ');" maxlength="5" onkeypress="return validaNumeros(event, true)">';
                        } else {
                            return '<input type="input" onchange="actualizarCosto(this, ' + meta.row + ');" maxlength="5" onkeypress="return validaNumeros(event, true)" value="' + data + '">';
                        }
                    }
                    return data;
                },
                className: "dt-body-center"
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

    $(FORM_AGREGAR_EDITAR + " #especialidad\\.idEspecialidad").change(function () {
        actualizarValoresDeEspecialidad(this);
    });

    $(MODAL_AGREGAR_EDITAR).on('hidden.bs.modal', function () {
        limpiarAgregarEditar();
        $(FORM_AGREGAR_EDITAR + ' #idEspecialidad').remove();
        TABLA_AGREGAR_EDITAR.clear().draw();
        $('.mostrarEditar').addClass('hidden');
        $('.mostrarAgregar').removeClass('hidden');
    });

    $(FORM_AGREGAR_EDITAR).validate({
        rules: {
            "especialidad.idEspecialidad": {
                required: true,
                notEqual: "0"
            }
        }
    });

    $(FORM_AGREGAR_EDITAR).submit(function (event) {
        if (!$(FORM_AGREGAR_EDITAR).valid()) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
            event.preventDefault();
            return false;
        }
        var lista = TABLA_AGREGAR_EDITAR.rows().data();
        var sedeEspecialidades = [];
        for (var i = 0; i < lista.length; i++) {
            sedeEspecialidades[ i ] = lista[ i ];
        }

        if (sedeEspecialidades.length === 0) {
            mostrarMensaje('#agregarEditarMensaje', 'danger', 'Las especialidades se relacionan a las sede, primero debe agregar una sede');
            event.preventDefault();
            return false;
        }

        $.ajax({
            url: $(FORM_AGREGAR_EDITAR).attr("action"),
            data: JSON.stringify(sedeEspecialidades),
            dataType: 'json',
            type: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function () {
                $(MODAL_AGREGAR_EDITAR + ' #closeBtn').click();
                limpiarAgregarEditar();
                $(FORM_CONSULTAR).submit();
                mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
            }
        });
        event.preventDefault();
    });

}

function actualizarValoresDeEspecialidad(elemento) {
    $(FORM_AGREGAR_EDITAR + ' #especialidad\\.codigo').val($("option:selected", elemento).data('codigo'));

    var idEspecialidad = $(elemento).val();
    if (idEspecialidad === "0") {
        TABLA_AGREGAR_EDITAR.clear().draw();
    } else {
        $.ajax({
            url: URI.expand(BASE_URL + 'rest/sedeEspecialidad/obtenerSedeEspecialidadesNuevos/{idEspecialidad}',
                    {idEspecialidad: idEspecialidad}).toString(),
            method: 'GET',
            success: function (data) {
                TABLA_AGREGAR_EDITAR.clear().draw();
                TABLA_AGREGAR_EDITAR.rows.add(data).draw();
            }
        });
    }
}

function actualizarEstado(evento, id) {
    var fila = TABLA_AGREGAR_EDITAR.row(id).data();
    if (evento.checked) {
        fila.estado = "1";
    } else {
        fila.estado = "0";
    }
    TABLA_AGREGAR_EDITAR.row(id).data(fila);
}

function actualizarCosto(evento, id) {
    var fila = TABLA_AGREGAR_EDITAR.row(id).data();
    fila.costoConsulta = evento.value;
    TABLA_AGREGAR_EDITAR.row(id).data(fila);
}

function limpiarAgregarEditar() {
    $(FORM_AGREGAR_EDITAR + ' #especialidad\\.idEspecialidad').val("0");
    $(FORM_AGREGAR_EDITAR + ' #especialidad\\.codigo').val("");
    $(FORM_AGREGAR_EDITAR + ' #estado').val(1);
    $('#agregarEditarMensaje').addClass('hidden');
    $("input").removeClass('error');
    $("select").removeClass('error');
}

function desactivar(idSede, idEspecialidad) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/sedeEspecialidad/desactivar/{idSede}/{idEspecialidad}',
                {idSede: idSede, idEspecialidad: idEspecialidad}).toString(),
        method: 'GET',
        success: function (data) {
            $(FORM_CONSULTAR).submit();
            $('#closeConfirmarDesactivarEspecialidadBtn').click();
            mostrarMensaje('#gestionarMensaje', 'success', 'La operación se realizó con éxito.');
        }
    });
}

function editar(idSede, idEspecialidad) {
    $(MODAL_AGREGAR_EDITAR).modal('show');
    $('.mostrarEditar').removeClass('hidden');
    $('.mostrarAgregar').addClass('hidden');
    $(FORM_AGREGAR_EDITAR + " #especialidad\\.idEspecialidad").val(idEspecialidad);
    actualizarValoresDeEspecialidad(FORM_AGREGAR_EDITAR + " #especialidad\\.idEspecialidad");
}

function subespecialidades(idSede, nombreSede, idEspecialidad, nombreEspecialidad) {
    $(MODAL_SUBESPECIALIDAD).modal('show');
    $(FORM_AGREGAR_SUBESPECIALIDAD + ' #id\\.idSede').val(idSede);
    $(FORM_AGREGAR_SUBESPECIALIDAD + ' #id\\.idEspecialidad').val(idEspecialidad);
    $(FORM_AGREGAR_SUBESPECIALIDAD + ' #sedeEspecialidad\\.sede\\.nombre').val(nombreSede);
    $(FORM_AGREGAR_SUBESPECIALIDAD + ' #sedeEspecialidad\\.especialidad\\.nombre').val(nombreEspecialidad);
    obtenerSubespecialidadesPorEspecialidad(idEspecialidad, FORM_AGREGAR_SUBESPECIALIDAD + ' #id\\.idSubespecialidad');
    obtenerSubespecialidades(idSede, idEspecialidad);
}

function bloqueSubespecialidades() {

    TABLA_SUBESPECIALIDADES = $('#subespecialidadesTabla').DataTable({
        dom: 'tr',
        "autoWidth": false,
        columns: [
            {"data": "subespecialidad.nombre"},
            {"data": "costo"},
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="eliminar" href="javascript:void(0)" onclick="confirmarDesactivar(\'Subespecialidad\',\'la Subespecialidad\',\'desactivarSubespecialidad(' + full.id.idSede + ',' + full.id.idEspecialidad + ',' + full.id.idSubespecialidad + ')\');return false;"><i class="fa fa-trash"></a>';
                }
            }
        ],
        columnDefs: [
            {width: 150, targets: 0},
            {width: 150, targets: 1},
            {width: 50, targets: 2}],
        select: {
            style: 'os',
            selector: 'td:not(:last-child)' // no row selection on last column
        },
        fixedColumns: true
    });

    $(MODAL_SUBESPECIALIDAD).on('hidden.bs.modal', function () {
        limpiarAgregarSubespecialidad();
        $('#subespecialidadMensaje').addClass("hidden");
    });

    $(FORM_AGREGAR_SUBESPECIALIDAD).validate({
        rules: {
            "id.idSubespecialidad": {
                required: true,
                notEqual: "0"
            },
            costo: {
                required: true,
                maxlength: 5
            }
        }
    });

    $(FORM_AGREGAR_SUBESPECIALIDAD).submit(function (event) {
        if (!$(FORM_AGREGAR_SUBESPECIALIDAD).valid()) {
            mostrarMensaje('#subespecialidadMensaje', 'danger', 'Los campos obligatorios deben de ser llenados.');
            event.preventDefault();
            return false;
        }
        var flag = true;
        var i = 0;
        while (flag && i < DATA_SUBESPECIALIDADES.length) {
            if (DATA_SUBESPECIALIDADES[i].id.idSubespecialidad == $(FORM_AGREGAR_SUBESPECIALIDAD + ' #id\\.idSubespecialidad').val()) {
                confirmar('La subespecialidad ya existe, ¿Desea reemplazar el costo?', 'submitAgregarSubespecialidad(true)');
                event.preventDefault();
                return;
            } else {
                i++;
            }
        }
        submitAgregarSubespecialidad(false);
        event.preventDefault();
    });

    $(FORM_AGREGAR_SUBESPECIALIDAD + " #limpiarBtn").click(function () {
        limpiarAgregarSubespecialidad();
    });
}

function submitAgregarSubespecialidad(pidioConfirmacion) {
    var data = {
        id: {
            idSede: $(FORM_AGREGAR_SUBESPECIALIDAD + ' #id\\.idSede').val(),
            idEspecialidad: $(FORM_AGREGAR_SUBESPECIALIDAD + ' #id\\.idEspecialidad').val(),
            idSubespecialidad: $(FORM_AGREGAR_SUBESPECIALIDAD + ' #id\\.idSubespecialidad').val()
        },
        costo: $(FORM_AGREGAR_SUBESPECIALIDAD + ' #costo').val()
    };

    $.ajax({
        url: $(FORM_AGREGAR_SUBESPECIALIDAD).attr("action"),
        data: JSON.stringify(data),
        dataType: 'json',
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function () {
            obtenerSubespecialidades(data.id.idSede, data.id.idEspecialidad);
            limpiarAgregarSubespecialidad();
            mostrarMensaje('#subespecialidadMensaje', 'success', 'La operación se realizó con éxito.');
            if (pidioConfirmacion) {
                $('#closeConfirmarBtn').click();
            }
        }
    });
}

function limpiarAgregarSubespecialidad() {
    $(FORM_AGREGAR_SUBESPECIALIDAD + ' #id\\.idSubespecialidad').val("0");
    $(FORM_AGREGAR_SUBESPECIALIDAD + ' #costo').val("");
    $('#subespecialidadMensaje').addClass('hidden');
    $("input").removeClass('error');
    $("select").removeClass('error');
}

function desactivarSubespecialidad(idSede, idEspecialidad, idSubespecialidad) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/sedeEspSubesp/desactivar/{idSede}/{idEspecialidad}/{idSubespecialidad}',
                {idSede: idSede, idEspecialidad: idEspecialidad, idSubespecialidad: idSubespecialidad}).toString(),
        method: 'GET',
        success: function () {
            obtenerSubespecialidades(idSede, idEspecialidad);
            mostrarMensaje('#subespecialidadMensaje', 'success', 'La operación se realizó con éxito.');
            $('#closeConfirmarDesactivarSubespecialidadBtn').click();
        }
    });
}

function obtenerSubespecialidades(idSede, idEspecialidad) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/sedeEspSubesp/obtenerSedeEspSubesps/{idSede}/{idEspecialidad}',
                {idSede: idSede, idEspecialidad: idEspecialidad}).toString(),
        method: 'GET',
        success: function (data) {
            TABLA_SUBESPECIALIDADES.clear().draw();
            TABLA_SUBESPECIALIDADES.rows.add(data).draw();
            DATA_SUBESPECIALIDADES = data;
        }
    });
}

function obtenerSubespecialidadesPorEspecialidad(idEspecialidad, elemento) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/subespecialidad/obtenerSubespecialidades/{idEspecialidad}',
                {idEspecialidad: idEspecialidad}).toString(),
        method: 'GET',
        success: function (data) {
            var html = '<option value="0">Elegir Subespecialidad</option>';
            $.each(data, function (key, value) {
                html += '<option value="' + value.idSubespecialidad + '">' + value.nombre + '</option>';
            });
            $(elemento).html(html);
        },
        error: function () {
            $(elemento).html("");
        }
    });
}