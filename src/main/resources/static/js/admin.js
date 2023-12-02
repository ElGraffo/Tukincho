document.addEventListener("DOMContentLoaded", function() {
    var listaUsuario = document.querySelectorAll('#listaUsuario');
    var listaReserva = document.querySelectorAll('#listaReserva');
    var listaInmuebles = document.querySelectorAll('#listaInmueble');

    listaUsuario.forEach(function(listaUsuario) {
        listaUsuario.addEventListener('click', function() {
            var divListaUsuario = document.querySelectorAll('#divListaUsuario');
            var divListarReservas = document.querySelectorAll('#divListarReservas');
            var divListaInmuebles = document.querySelectorAll('#divListaInmuebles');

            divListaUsuario.forEach(function(divListaUsuario) {
                var clase = divListaUsuario.getAttribute("class");

                divListaUsuario.setAttribute("class","visible");
            });
            divListarReservas.forEach(function(divListarReservas) {
                var clase = divListarReservas.getAttribute("class");

                divListarReservas.setAttribute("class","novisible");
            });
            divListaInmuebles.forEach(function(divListaInmuebles) {
                var clase = divListaInmuebles.getAttribute("class");

                divListaInmuebles.setAttribute("class","novisible");
            });
        });
    });

    listaReserva.forEach(function(listaReserva) {
        listaReserva.addEventListener('click', function() {
            var divListaUsuario = document.querySelectorAll('#divListaUsuario');
            var divListarReservas = document.querySelectorAll('#divListarReservas');
            var divListaInmuebles = document.querySelectorAll('#divListaInmuebles');

            divListaUsuario.forEach(function(divListaUsuario) {
                var clase = divListaUsuario.getAttribute("class");

                divListaUsuario.setAttribute("class","novisible");
            });
            divListarReservas.forEach(function(divListarReservas) {
                var clase = divListarReservas.getAttribute("class");

                divListarReservas.setAttribute("class","visible");
            });
            divListaInmuebles.forEach(function(divListaInmuebles) {
                var clase = divListaInmuebles.getAttribute("class");

                divListaInmuebles.setAttribute("class","novisible");
            });
        });
    });

    listaInmuebles.forEach(function(listaUsuario) {
        listaInmuebles.addEventListener('click', function() {
            var divListaUsuario = document.querySelectorAll('#divListaUsuario');
            var divListarReservas = document.querySelectorAll('#divListarReservas');
            var divListaInmuebles = document.querySelectorAll('#divListaInmuebles');

            divListaUsuario.forEach(function(divListaUsuario) {
                var clase = divListaUsuario.getAttribute("class");

                divListaUsuario.setAttribute("class","novisible");
            });
            divListarReservas.forEach(function(divListarReservas) {
                var clase = divListarReservas.getAttribute("class");

                divListarReservas.setAttribute("class","novisible");
            });
            divListaInmuebles.forEach(function(divListaInmuebles) {
                var clase = divListaInmuebles.getAttribute("class");

                divListaInmuebles.setAttribute("class","visible");
            });
        });
    });
});
