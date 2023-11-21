package com.Tukincho.Tukincho.controladores;

import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.entidades.Reserva;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.servicios.InmuebleServicio;
import com.Tukincho.Tukincho.servicios.ReservaServicio;
import com.Tukincho.Tukincho.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ReservaControlador {
    @Autowired
    ReservaServicio reservaServicio;
  
    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    InmuebleServicio inmuebleServicio;

    @GetMapping("/crear/{id}")
    public String reserva(@PathVariable String id, ModelMap model) {
        try {
            Inmueble inmueble = inmuebleServicio.buscarInmueblePorId(id);
            System.out.println(inmueble.getDireccion());
            model.put("inmueble", inmueble);
            return "reserva.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "reserva.html";
        }
    }

    @PostMapping("/reservado")
    public String reservado(@RequestParam String fechaInicioReserva,
                          @RequestParam String fechaFinReserva, @RequestParam Double costoReserva,
                          @RequestParam Double costoServiciosSeleccionados, @RequestParam
                          String inmuebleId, String usuarioId, ModelMap model){

        System.out.println(costoReserva);
        System.out.println(costoServiciosSeleccionados);
        Reserva reserva;
        try {

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date inicioReserva = formato.parse(fechaInicioReserva);
            Date finReserva = formato.parse(fechaFinReserva);


            Inmueble inmueble = inmuebleServicio.buscarInmueblePorId(inmuebleId);
            Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);

            reserva = reservaServicio.crearReserva(inmueble, usuario, inicioReserva,
                    finReserva, costoReserva, costoServiciosSeleccionados);

            List<Reserva> reservas= inmueble.getReserva();
            reservas.add(reserva);




            inmueble.setReserva(reservas);

            model.put("exito","La reserva se ha generado exitosamente");
        } catch (Exception e) {
            model.put("error","Ha habido un error, vuelva a intentarlo nuevamente");

        }
        return "index.html";
    }
}
