var Usuario = {
    inicioSesion: function (usuario, pass) {
        var ref = new Firebase($urlApp);
        ref.authWithPassword({
            email: usuario
            , password: pass
        }, function (error, authData) {
            if (error) {
                alert("Login Failed!", error);
            } else {
                alert("Authenticated successfully with payload:", authData);
            }
        })
    }
    , crearUsuario: function (email, password) {
        var ref = new Firebase($urlApp);
        ref.createUser({
            email: "ivan_archer93@hotmail.com"
            , password: "newyork1209"
        }, function (error, userData) {
            if (error) {
                alert("Error creating user:", error);
            } else {
                alert("Successfully created user account with uid:", userData.uid);
            }
        });
    }
    , recuperarContrasena : function (email) {
        var ref = new Firebase($urlApp);
        ref.resetPassword({
            email: email
        }, function (error) {
            if (error === null) {
                console.log("Password reset email sent successfully");
            } else {
                console.log("Error sending password reset email:", error);
            }
        });
    }
}

var $urlApp = "https://blinding-inferno-2140.firebaseio.com";
$(document).ready(function ($) {
    $('#bt-inicio').click(function () {
        var user=$('input#user').val();
        var contrasena = $('input#password').val();
        Usuario.inicioSesion(user, contrasena);
    });
});
