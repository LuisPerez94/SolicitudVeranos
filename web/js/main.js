var Usuario = {
        inicioSesion: function (usuario, pass, url) {
            $("#loading").css({
                "background-image":"url('img/loading.gif')",
                "background-size":"50px 50px",
                "height":"50px",
                "width":"50px",
                "display":"block",
                "margin":"10px auto"
            });

            console.log("HOLA: " + $("#carrera option:selected").val());

            var carrera = $("#carrera option:selected").val();

            var ref = new Firebase($urlApp);
            ref.authWithPassword({
                email: usuario
                , password: pass
            }, function (error, authData) {
                if (error) {
                    switch (error.code) {
                    case "INVALID_EMAIL":
                        alert("Correo inválido.");
                        break;
                    case "INVALID_PASSWORD":
                        alert("Contraseña incorrecta.");
                        break;
                    case "INVALID_USER":
                        alert("Usuario incorrecto.");
                        break;
                    default:
                        alert("Error al iniciar sesión: ", error);
                    }

                    $("#loading").css({
                        "display":"none"
                    });
                } else {
                    if(carrera != 0){
                        $cookie('sesion','1');
                        window.location.href=url+"?carrera="+carrera;
                        window.location.reload;
                    }else{
                        alert("Escoja una carrera");
                        $("#loading").css({
                            "display":"none"
                        });
                    }
                }
            })
        }
        , crearUsuario: function (correo, pass) {
        var ref = new Firebase($urlApp);
        ref.createUser({
            email : correo
            , password : pass
        }, function (error, userData) {
            if (error) {
                alert("Error al crear el nuevo usuario: "+ error.code);
            } else {
                alert("Usuario creado correctamente");
            }
        });
    }
    , recuperarContrasena : function (email) {
        var ref = new Firebase($urlApp);
        ref.resetPassword({
            email: email
        }, function (error) {
            if (error === null) {
                alert("Restablecimiento de contraseña de correo electrónico enviado con éxito");
            } else {
                Alert("Error al restablecer su contraseña al correo");
            }
        });
    }
}

var $urlApp = "https://blinding-inferno-2140.firebaseio.com";
$(document).ready(function ($) {
    $('#bt-inicio').click(function () {
        var user=$('input#user').val();
        var contrasena = $('input#password').val();
        Usuario.inicioSesion(user, contrasena, "admin.html");
    });
    $('#bt_Aceptar').click(function(){
        var email=$('input#email').val();
        var contrasena = $('input#password2').val();
        Usuario.crearUsuario(email, contrasena);
        $('#modalRegistro').modal('hide');
    });
    $('#bt_Aceptar2').click(function(){
        var email=$('input#email2').val();
        Usuario.recuperarContrasena(email);
        $('#modalRecuperacion').modal('hide');
    });
});
