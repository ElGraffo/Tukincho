// Lista de reservas con fechas de inicio y fin
var reservas = /*[[${inmueble.reservas}]]*/ []; 
  
  // Obtener el elemento del input de fecha
  var inputFecha = document.getElementById('fechaInicioReserva-id');

  var inputFechaFin = document.getElementById('fechaFinReserva-id');
  
  // Función para verificar si una fecha está dentro del rango de alguna reserva
  function estaEnRango(fecha, reserva) {
    return fecha >= new Date(reserva.fechaInicioReserva) && fecha <= new Date(reserva.fechaFinReserva);
  }
  
  // Función para inhabilitar fechas dentro de los rangos de las reservas
  function inhabilitarFechas() {
    var fechasInhabilitadas = [];
  
    // Obtener todas las fechas inhabilitadas
    for (var i = 0; i < reservas.length; i++) {
      var inicio = new Date(reservas[i].fechaInicioReserva);
      var fin = new Date(reservas[i].fechaFinReserva);
  
      for (var fecha = inicio; fecha <= fin; fecha.setDate(fecha.getDate() + 1)) {
        fechasInhabilitadas.push(new Date(fecha));
      }
    }
  
    // Convertir las fechas inhabilitadas a cadenas en formato 'YYYY-MM-DD'
    var fechasInhabilitadasStr = fechasInhabilitadas.map(function(date) {
      return date.toISOString().split('T')[0];
    });
  
    // Deshabilitar las fechas inhabilitadas
/*     inputFecha.addEventListener('input', function() {
      var fechaSeleccionada = inputFecha.value;
      if (fechasInhabilitadasStr.includes(fechaSeleccionada)) {
        inputFecha.value = '';
        alert('Esta fecha está inhabilitada');
      }
    }); */
  
    // Aplicar el estilo para deshabilitar visualmente las fechas inhabilitadas
    var style = document.createElement('style');
    style.innerHTML = `
      input[type="date"]::-webkit-calendar-picker-indicator {
        filter: ${fechasInhabilitadasStr.map(date => `input[type="date"][value="${date}"]`).join(',')} {
          opacity: 0.5;
          pointer-events: none;
        }
      }
    `;
    document.head.appendChild(style);
  }
  
  // Llamar a la función para inhabilitar fechas
  inhabilitarFechas();
  