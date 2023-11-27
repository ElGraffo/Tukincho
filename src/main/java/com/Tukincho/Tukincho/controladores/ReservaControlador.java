package com.Tukincho.Tukincho.controladores;

import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.entidades.InmuebleServicioExtra;
import com.Tukincho.Tukincho.entidades.Reserva;
import com.Tukincho.Tukincho.entidades.ServiciosExtra;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.repositorios.ServiciosExtraRepositorio;
import com.Tukincho.Tukincho.servicios.InmuebleServicio;
import com.Tukincho.Tukincho.servicios.ReservaServicio;
import com.Tukincho.Tukincho.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reserva")
public class ReservaControlador {
    @Autowired
    ReservaServicio reservaServicio;
  
    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    InmuebleServicio inmuebleServicio;
    
    @Autowired
    ServiciosExtraRepositorio serviciosExtrasRepositorio;

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
    public String reservado(
            @RequestParam String fechaInicioReserva,
            @RequestParam String fechaFinReserva, 
            @RequestParam Long costoReserva,
            @RequestParam Double costoServiciosSeleccionados, 
            @RequestParam String inmuebleId, 
            @RequestParam String usuarioId, 
            HttpServletRequest request,
            ModelMap model){

        System.out.println(costoReserva);
        System.out.println(costoServiciosSeleccionados);
        Reserva reserva;
        try {

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date inicioReserva = formato.parse(fechaInicioReserva);
            Date finReserva = formato.parse(fechaFinReserva);


            Inmueble inmueble = inmuebleServicio.buscarInmueblePorId(inmuebleId);
            //mejor buscar por nombreUsuario
            Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
            
            if(costoReserva!=null && costoReserva==0){
                costoReserva= inmueble.getPrecioPorNoche();
            }
             // Obtener los valores de servicios extras y sus precios
            Map<String, Long> preciosServiciosExtras = new HashMap<>();
            //recorro cada input del formulario y consulto si el nombre comienza por servicio_
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("servicio_")) {
                    //paramName viene asi servicio_34rf5t0r2rhk8xf32010 y le quito servicio_
                    //y asi obtengo el id del servicio que fue tildado en el checkbox, el precio
                    //viene igual a servicio solo que empieza con precio_
                    String servicioId = paramName.replace("servicio_", "");
                    String precioId = paramName.replace("servicio_", "precio_");
                    Long precioServicio = Long.parseLong(request.getParameter(precioId));
                    costoReserva+=precioServicio;//le sumo el precio de todos los servicios pagos
                    preciosServiciosExtras.put(servicioId, precioServicio);
                }
            }
            //TODO
            //deberia hacer una tabla extra que guarde los servicios extras seleccionado por el usuario
            
            List<InmuebleServicioExtra> inmuebleServiciosExtra = crearInmuebleServiciosExtras(preciosServiciosExtras, inmueble);
           
            
            
            
            System.out.println("INICIO RESERVA: "+inicioReserva);
            System.out.println("INF RESERVA: "+finReserva);

            reserva = reservaServicio.crearReserva(inmueble, usuario, inicioReserva,
                   finReserva, costoReserva, costoServiciosSeleccionados);

            List<Reserva> reservas= inmueble.getReservas();
            reservas.add(reserva);




            inmueble.setReservas(reservas);

            model.put("exito","La reserva se ha generado exitosamente");
        } catch (Exception e) {
            model.put("error","Ha habido un error, vuelva a intentarlo nuevamente");
            System.out.println("##############################ERROR RESERVA-----------------------------------------------------");
            System.out.println(e.getMessage());
        }
        return "index.html";
    }
    
    
     private List<InmuebleServicioExtra> crearInmuebleServiciosExtras(Map<String, Long> preciosServiciosExtras, Inmueble inmueble) {
        List<InmuebleServicioExtra> inmuebleServiciosExtra = new ArrayList<>();

        if (preciosServiciosExtras != null && !preciosServiciosExtras.isEmpty()) {
            for (Map.Entry<String, Long> entry : preciosServiciosExtras.entrySet()) {
                String servicioExtraId = entry.getKey();
                Long precio = entry.getValue();

                ServiciosExtra servicioExtra = serviciosExtrasRepositorio.findById(servicioExtraId).orElse(null);

                if (servicioExtra != null) {
                    InmuebleServicioExtra inmuebleServicioExtra = new InmuebleServicioExtra();
                    inmuebleServicioExtra.setServicioExtra(servicioExtra);
                    inmuebleServicioExtra.setPrecio(precio); // Establecer el precio asociado al servicio
                    inmuebleServicioExtra.setInmueble(inmueble);

                    inmuebleServiciosExtra.add(inmuebleServicioExtra);
                }
            }
        }

        return inmuebleServiciosExtra;
    }
}
