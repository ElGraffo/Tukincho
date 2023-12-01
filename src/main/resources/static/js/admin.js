


document.addEventListener("DOMContentLoaded", function() {
    var listaUsuario = document.querySelectorAll('#listaUsuario');
    var listaPropietario = document.querySelectorAll('#listaPropietario');
    var listaInmueble = document.querySelectorAll('#listaInmueble');

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

      listaPropietario.forEach(function(listaPropietario) {
          listaPropietario.addEventListener('click', function() {
            var divListaPropietarios = document.querySelectorAll('#divListaPropietarios');

                divListaPropietarios.forEach(function(divListaPropietarios) {
                     var clase = divListaPropietarios.getAttribute("class");

                      if(clase.includes("novisible")) {
                        divListaPropietarios.removeAttribute("class");
                        divListaPropietarios.setAttribute("class","visible");
                      } else {
                        divListaPropietarios.removeAttribute("class");
                        divListaPropietarios.setAttribute("class","novisible");
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