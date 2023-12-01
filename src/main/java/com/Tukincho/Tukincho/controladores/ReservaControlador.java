package com.Tukincho.Tukincho.controladores;

import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.entidades.InmuebleServicioExtra;
import com.Tukincho.Tukincho.entidades.Propietario;
import com.Tukincho.Tukincho.entidades.Reserva;
import com.Tukincho.Tukincho.entidades.ServiciosExtra;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.repositorios.ServiciosExtraRepositorio;
import com.Tukincho.Tukincho.repositorios.UsuarioRepositorio;
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
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PreAuthorize("hasAnyRole('ROLE_PROPIETARIO', 'ROLE_USUARIO')")
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
            ModelMap model,
            RedirectAttributes redirectModel) {

        System.out.println(costoReserva);
        System.out.println(costoServiciosSeleccionados);
        Reserva reserva;
        try {

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date inicioReserva = formato.parse(fechaInicioReserva);
            Date finReserva = formato.parse(fechaFinReserva);

            Inmueble inmueble = inmuebleServicio.buscarInmueblePorId(inmuebleId);
            //ANTES BUSCABA POR ID DE USUARIO, pero si es un propietario, no va a encontrar 
            //un id de usuario, por eso busco con rol de usuario
            Usuario usuario = usuarioServicio.buscarUsuarioPorId(usuarioId);
            Propietario propietario = inmueble.getPropietario();
            System.out.println("PROPIETARIO ID: "+propietario.getId());
            System.out.println("USUARIO ID:"+usuario.getId());
            if (costoServiciosSeleccionados != null) {
                costoServiciosSeleccionados =0.0;
            }
            if(costoReserva==null || costoReserva==0){
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
                    System.out.println("############-SERVICIOS-SELECCIONADOS###############3");
                    String precioId = "precio_"+servicioId;
                    System.out.println("precio:"+precioId);
                    Long precioServicio = Long.parseLong(request.getParameter(precioId));
                    costoServiciosSeleccionados += precioServicio;//le sumo el precio de todos los servicios pagos
                    System.out.println("COSTOSERVICIOS EXTRAS: "+costoServiciosSeleccionados);
                    preciosServiciosExtras.put(servicioId, precioServicio);
                }
            }
            //TODO
            //deberia hacer una tabla extra que guarde los servicios extras seleccionado por el usuario

            List<InmuebleServicioExtra> inmuebleServiciosExtra = crearReservaServiciosExtras(preciosServiciosExtras, inmueble);

            System.out.println("INICIO RESERVA: " + inicioReserva);
            System.out.println("FIN RESERVA: " + finReserva);

            reserva = reservaServicio.crearReserva(inmueble, usuario, propietario, inicioReserva,
                    finReserva, costoReserva, costoServiciosSeleccionados);

            List<Reserva> reservas = inmueble.getReservas();
            reservas.add(reserva);

            inmueble.setReservas(reservas);

            model.put("exito", "La reserva se ha generado exitosamente");
        } catch (Exception e) {
            redirectModel.addFlashAttribute("error", e.getMessage());
            System.out.println("##############################ERROR RESERVA-----------------------------------------------------");
            System.out.println(e.getMessage());
            System.out.println(e.initCause(e));
            System.out.println(e.getStackTrace());
            return "redirect:/reserva/crear/"+inmuebleId;
        }
        return "index.html";
    }

    private List<InmuebleServicioExtra> crearReservaServiciosExtras(Map<String, Long> preciosServiciosExtras, Inmueble inmueble) {
       //se debe crear la entidad ReservaServicioExtra en ves de InmuebleServicioExtra
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

    @GetMapping("/listar")
    public String listarReserva(HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        usuario = usuarioServicio.buscarUsuarioPorId(usuario.getId());
        List<Reserva> reservas = usuario.getReserva();
        modelo.put("reservas", reservas);
        return "reservas_listar";
    }

}
