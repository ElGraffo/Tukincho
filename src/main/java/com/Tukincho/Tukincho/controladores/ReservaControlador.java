package com.Tukincho.Tukincho.controladores;

import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.entidades.Reserva;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.servicios.InmuebleServicio;
import com.Tukincho.Tukincho.servicios.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ReservaControlador {
    @Autowired
    ReservaServicio reservaServicio;
    @Autowired
    InmuebleServicio inmuebleServicio;
    
    @GetMapping("/crear/{id}")
    public String reserva(@PathVariable String id, ModelMap model){
        try{
            Inmueble inmueble = inmuebleServicio.buscarInmueblePorId(id);
            model.put("inmueble", inmueble);
            return "reserva.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "reserva.html";
        }
    }

    @PostMapping("/reservado")
    public String reservado(@RequestParam Date fechaInicioReserva,
                          @RequestParam Date fechaFinReserva, @RequestParam Double costoReserva,
                          @RequestParam Double costoServiciosSeleccionados, @RequestParam
                          Inmueble inmueble, Usuario usuario, ModelMap model){
        Reserva reserva;
        try {
            reserva = reservaServicio.crearReserva(inmueble, usuario, fechaInicioReserva,
                    fechaFinReserva, costoReserva, costoServiciosSeleccionados);
            inmueble.setReserva((List<Reserva>) reserva);
            model.put("exito","La reserva se ha generado exitosamente");
        } catch (Exception e) {
            model.put("error","Ha habido un error, vuelva a intentarlo nuevamente");
            throw new RuntimeException(e);
        }
        return "index.html";
    }
}
