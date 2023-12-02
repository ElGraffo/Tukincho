document.addEventListener("DOMContentLoaded", function() {
    var listaUsuario = document.querySelectorAll('#listaUsuario');
    var listaReserva = document.querySelectorAll('#listaReserva');
    var propiedad = document.querySelectorAll('#listaInmueble');

    listaUsuario.forEach(function(listaUsuario) {
        listaUsuario.addEventListener('click', function() {
            var divListaUsuario = document.querySelectorAll('#divListaUsuario');

            divListaUsuario.forEach(function(divListaUsuario) {
                var clase = divListaUsuario.getAttribute("class");

                if(clase.includes("novisible")) {
                    divListaUsuario.removeAttribute("class");
                    divListaUsuario.setAttribute("class","visible");
                } else {
                    divListaUsuario.removeAttribute("class");
                    divListaUsuario.setAttribute("class","novisible");
                }
            });
        });
    });

    listaReserva.forEach(function(listaReserva) {
        listaReserva.addEventListener('click', function() {
            var divListarReservas = document.querySelectorAll('#divListarReservas');

            divListarReservas.forEach(function(divListarReservas) {
                var clase = divListarReservas.getAttribute("class");

                if(clase.includes("novisible")) {
                    divListarReservas.removeAttribute("class");
                    divListarReservas.setAttribute("class","visible");
                } else {
                    divListarReservas.removeAttribute("class");
                    divListarReservas.setAttribute("class","novisible");
                }
            });

        });
    });

    listaInmueble.forEach(function(listaInmueble) {
        listaInmueble.addEventListener('click', function() {
            var divListaInmuebles = document.querySelectorAll('#divListaInmuebles');

            divListaInmuebles.forEach(function(divListaInmuebles) {
                var clase = divListaInmuebles.getAttribute("class");

                if(clase.includes("novisible")) {
                    divListaInmuebles.removeAttribute("class");
                    divListaInmuebles.setAttribute("class","visible");
                } else {
                    divListaInmuebles.removeAttribute("class");
                    divListaInmuebles.setAttribute("class","novisible");
                }
            });

        });
    });
});