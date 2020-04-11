/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    $('#form-login').validate({
        rules: {
            usuario: {
                required: true
            },
            clave: {
                required: true
            }
        }
    });

    $('#form-recuperar-contrasena').validate({
        rules: {
            usuario: {
                required: true
            }
        }
    });

    $('#form-recuperar-usuario').validate({
        rules: {
            correo: {
                required: true,
                email: true
            }
        }
    });

    $('#form-recuperar-contrasena').submit(function (e) {
        e.preventDefault();
        $("#loader-overflow").fadeIn();
        this.submit();
    });

    $('#form-recuperar-usuario').submit(function (e) {
        e.preventDefault();
        $("#loader-overflow").fadeIn();
        this.submit();
    });

});