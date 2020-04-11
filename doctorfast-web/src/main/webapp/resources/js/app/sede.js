/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var TABLA;
var FORM_CONSULTAR = "#consultarForm";
var FORM_AGREGAR_EDITAR = "#agregarEditarForm";
var DEPARTAMENTOS, PROVINCIAS, DISTRITOS;

$(document).ready(function () {

    TABLA = $('#tabla').DataTable({
        language: DATATABLE_ES,
        dom: 'lrtip',
        "scrollX": true,
        columns: [
            {"data": "codigoLargo"},
            {"data": "nombre"},
            {"data": "ubigeo.noDepartamento"},
            {"data": "ubigeo.noProvincia"},
            {"data": "ubigeo.noDistrito"},
            {"data": "estado"},
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="editar" href="javascript:void(0)" onclick="editar(' + full.idSede + ');return false;"><i class="fa fa-edit"></i></a>';
                }
            },
            {
                sortable: false,
                className: "center-text",
                "render": function (data, type, full, meta) {
                    return '<a id="eliminar" href="javascript:void(0)" onclick="confirmarDesactivar(\'Sede\',\'la Sede\',\'desactivar(' + full.idSede + ')\');return false;"><i class="fa fa-trash"></a>';
                }
            }
        ],
        columnDefs: [{"targets": 5, "data": "estado", "render": function (data) {
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

});

//function obtenerDepartamentos() {
//    $.ajax({
//        url: URI.expand(BASE_URL + 'rest/ubigeo/obtenerDepartamentos').toString(),
//        method: 'GET',
//        success: function (data) {
//            DEPARTAMENTOS = [];
//            $.each(data, function (key, value) {
//                DEPARTAMENTOS.push({
//                    data: value.id.coDepartamento,
//                    value: value.noDepartamento
//                });
//            });
//            $('#autocomplete').autocomplete({
//                autoSelectFirst: true,
//                lookup: DEPARTAMENTOS,
//                onSelect: function (suggestion) {
//                    $(FORM_CONSULTAR + " #ubigeo\\.id\\.coDepartamento").val(suggestion.data);
//                    obtenerProvincias1(suggestion.data);
//                },
//                onInvalidateSelection: function () {
//                    $("#autocomplete").val("");
//                }
//            });
//        }
//    });
//}
//
//function obtenerProvincias1(idDepartamento, elemento, seleccion) {
//    $.ajax({
//        url: URI.expand(BASE_URL + 'rest/ubigeo/obtenerProvincias/{idDepartamento}',
//                {idDepartamento: idDepartamento}).toString(),
//        method: 'GET',
//        success: function (data) {
//            PROVINCIAS = [];
//            $.each(data, function (key, value) {
//                PROVINCIAS.push({
//                    data: value.id.coProvincia,
//                    value: value.noProvincia
//                });
//            });
//            $('#autocomplete2').val("");
//            $(FORM_CONSULTAR + " #ubigeo\\.id\\.coProvincia").val("");
//            $('#autocomplete3').val("");
//            $(FORM_CONSULTAR + " #ubigeo\\.id\\.coDistrito").val("");
//            $('#autocomplete2').autocomplete({
//                autoSelectFirst: true,
//                lookup: PROVINCIAS,
//                onSelect: function (suggestion) {
//                    $(FORM_CONSULTAR + " #ubigeo\\.id\\.coProvincia").val(suggestion.data);
//                    obtenerDistritos1($("#ubigeo\\.id\\.coDepartamento").val(), suggestion.data);
//                }
//            });
//            $('#autocomplete3').autocomplete({
//                lookup: [],
//                onSelect: function (suggestion) {
//                    $(FORM_CONSULTAR + " #ubigeo\\.id\\.coDistrito").val(suggestion.data);
//                }
//            });
//        }
//    });
//}
//
//function obtenerDistritos1(idDepartamento, idProvincia, elemento, seleccion) {
//    $.ajax({
//        url: URI.expand(BASE_URL + 'rest/ubigeo/obtenerDistritos/{idDepartamento}/{idProvincia}',
//                {idDepartamento: idDepartamento, idProvincia: idProvincia}).toString(),
//        method: 'GET',
//        success: function (data) {
//            DISTRITOS = [];
//            $.each(data, function (key, value) {
//                DISTRITOS.push({
//                    data: value.id.coDistrito,
//                    value: value.noDistrito
//                });
//            });
//            $('#autocomplete3').val("");
//            $(FORM_CONSULTAR + " #ubigeo\\.id\\.coDistrito").val("");
//            $('#autocomplete3').autocomplete({
//                autoSelectFirst: true,
//                lookup: DISTRITOS,
//                onSelect: function (suggestion) {
//                    $(FORM_CONSULTAR + " #ubigeo\\.id\\.coDistrito").val(suggestion.data);
//                }
//            });
//        }
//    });
//}

function obtenerProvincias(idDepartamento, elemento, seleccion, seleccionDistrito) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/ubigeo/obtenerProvincias/{idDepartamento}',
                {idDepartamento: idDepartamento}).toString(),
        method: 'GET',
        success: function (data) {
            var html = '<option value="0">Elegir Provincia</option>';
            $.each(data, function (key, value) {
                html += '<option value="' + value.id.coProvincia + '">' + value.noProvincia + '</option>';
            });
            $(elemento).html(html);
            $(FORM_AGREGAR_EDITAR + " #ubigeo\\.id\\.coDistrito").html('<option value="0">Elegir Distrito</option>');
            if (seleccion) {
                $(elemento).val(seleccion);
                obtenerDistritos(idDepartamento, seleccion, FORM_AGREGAR_EDITAR + " #ubigeo\\.id\\.coDistrito", seleccionDistrito);
            }
        },
        error: function () {
            $(elemento).html("");
        }
    });
}

function obtenerDistritos(idDepartamento, idProvincia, elemento, seleccion) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/ubigeo/obtenerDistritos/{idDepartamento}/{idProvincia}',
                {idDepartamento: idDepartamento, idProvincia: idProvincia}).toString(),
        method: 'GET',
        success: function (data) {
            var html = '<option value="0">Elegir Distrito</option>';
            $.each(data, function (key, value) {
                html += '<option value="' + value.id.coDistrito + '">' + value.noDistrito + '</option>';
            });
            $(elemento).html(html);
            if (seleccion)
                $(elemento).val(seleccion);
        },
        error: function () {
            $(elemento).html("");
        }
    });
}

function bloqueConsultar() {

//    obtenerDepartamentos();

    $(FORM_CONSULTAR + " #ubigeo\\.id\\.coDepartamento").change(function () {
        obtenerProvincias($(this).val(), FORM_CONSULTAR + " #ubigeo\\.id\\.coProvincia");
        $(FORM_CONSULTAR + " #ubigeo\\.id\\.coDistrito").html('<option value="0">Elegir Distrito</option>');
    });

    $(FORM_CONSULTAR + " #ubigeo\\.id\\.coProvincia").change(function () {
        obtenerDistritos($("#ubigeo\\.id\\.coDepartamento").val(), $(this).val(), FORM_CONSULTAR + " #ubigeo\\.id\\.coDistrito");
    });

    $(FORM_CONSULTAR).submit(function (event) {
        
        var codigoNumero = $(FORM_CONSULTAR + ' #codigoLargo').val().replace("sed", "");
        if(isNaN(codigoNumero)){
            mostrarMensaje('#gestionarMensaje', 'success', 'El formato del codigo no es correcto. (Ejm: sed0001)');
            event.preventDefault();
            return;
        }
        
        var data = {
            codigoLargo: $(FORM_CONSULTAR + ' #codigoLargo').val(),
            nombre: $(FORM_CONSULTAR + ' #nombre').val(),
            estado: $(FORM_CONSULTAR + ' #estado').val(),
            ubigeo: {
                id: {
                    "coDepartamento": $(FORM_CONSULTAR + ' #ubigeo\\.id\\.coDepartamento').val(),
                    "coProvincia": $(FORM_CONSULTAR + ' #ubigeo\\.id\\.coProvincia').val(),
                    "coDistrito": $(FORM_CONSULTAR + ' #ubigeo\\.id\\.coDistrito').val()
                }
            }

        };

        consultar(FORM_CONSULTAR, data, TABLA, 'Sedes');
        event.preventDefault();
    });

    $("#limpiarBtn").click(function () {
        limpiarConsultar();
        TABLA.clear().draw();
    });

    $(FORM_CONSULTAR).submit();
}

function limpiarConsultar() {
    $(FORM_CONSULTAR + ' #codigoLargo').val("");
    $(FORM_CONSULTAR + ' #nombre').val("");
    $(FORM_CONSULTAR + ' #ubigeo\\.id\\.coDepartamento').val(0);
    $(FORM_CONSULTAR + ' #ubigeo\\.id\\.coProvincia').val(0);
    $(FORM_CONSULTAR + ' #ubigeo\\.id\\.coDistrito').val(0);
    limpiarSelectConsultar(FORM_CONSULTAR + " #ubigeo\\.id\\.coProvincia", "Elegir Provincia");
    limpiarSelectConsultar(FORM_CONSULTAR + " #ubigeo\\.id\\.coDistrito", "Elegir Distrito");
    $(FORM_CONSULTAR + ' #estado').val("");
    $("#autocomplete").val("");
    $("#autocomplete2").val("");
    $("#autocomplete3").val("");
    $('#gestionarMensaje').addClass('hidden');
}

function bloqueAgregarEditar() {

    $(FORM_AGREGAR_EDITAR + " #ubigeo\\.id\\.coDepartamento").change(function () {
        obtenerProvincias($(this).val(), FORM_AGREGAR_EDITAR + " #ubigeo\\.id\\.coProvincia");
    });

    $(FORM_AGREGAR_EDITAR + " #ubigeo\\.id\\.coProvincia").change(function () {
        obtenerDistritos($(FORM_AGREGAR_EDITAR + " #ubigeo\\.id\\.coDepartamento").val(), $(this).val(), FORM_AGREGAR_EDITAR + " #ubigeo\\.id\\.coDistrito");
    });

    $('#agregarEditarModal').on('hidden.bs.modal', function () {
        limpiarAgregarEditar();
        $(FORM_AGREGAR_EDITAR + ' #idSede').remove();
        $('.mostrarEditar').addClass('hidden');
        $('.mostrarAgregar').removeClass('hidden');
    });

    $(FORM_AGREGAR_EDITAR).validate({
        rules: {
            nombre: {
                required: true,
                maxlength: 50
            },
            "ubigeo.id.coDepartamento": {
                required: true,
                notEqual: "0"
            },
            "ubigeo.id.coProvincia": {
                required: true,
                notEqual: "0"
            },
            "ubigeo.id.coDistrito": {
                required: true,
                notEqual: "0"
            },
            direccion: {
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
            codigoLargo: $(FORM_AGREGAR_EDITAR + ' #codigoLargo').val(),
            nombre: $(FORM_AGREGAR_EDITAR + ' #nombre').val(),
            ubigeo: {
                id: {
                    "coDepartamento": $(FORM_AGREGAR_EDITAR + ' #ubigeo\\.id\\.coDepartamento').val(),
                    "coProvincia": $(FORM_AGREGAR_EDITAR + ' #ubigeo\\.id\\.coProvincia').val(),
                    "coDistrito": $(FORM_AGREGAR_EDITAR + ' #ubigeo\\.id\\.coDistrito').val()
                }
            },
            direccion: $(FORM_AGREGAR_EDITAR + ' #direccion').val(),
            estado: $(FORM_AGREGAR_EDITAR + ' #estado').val(),
            idSede: ($('#idSede').val()) ? $('#idSede').val() : null
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
    $(FORM_AGREGAR_EDITAR + ' #codigoLargo').val("");
    $(FORM_AGREGAR_EDITAR + ' #nombre').val("");
    $(FORM_AGREGAR_EDITAR + ' #ubigeo\\.id\\.coDepartamento').val(0);
    $(FORM_AGREGAR_EDITAR + ' #ubigeo\\.id\\.coProvincia').val(0);
    $(FORM_AGREGAR_EDITAR + ' #ubigeo\\.id\\.coDistrito').val(0);
    $(FORM_AGREGAR_EDITAR + ' #direccion').val("");
    $(FORM_AGREGAR_EDITAR + ' #estado').val(1);
    $('#agregarEditarMensaje').addClass('hidden');
    $("input").removeClass('error');
    $("select").removeClass('error');
}

function desactivar(idSede) {
    $.ajax({
        url: URI.expand(BASE_URL + 'rest/sede/desactivar/{idSede}',
                {idSede: idSede}).toString(),
        method: 'GET',
        success: function () {
            $(FORM_CONSULTAR).submit();
            $('#closeConfirmarDesactivarSedeBtn').click();
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