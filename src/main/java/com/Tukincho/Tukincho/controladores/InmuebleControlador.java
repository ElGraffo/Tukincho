package com.Tukincho.Tukincho.controladores;

import com.Tukincho.Tukincho.entidades.*;
import com.Tukincho.Tukincho.enums.Provincia;
import com.Tukincho.Tukincho.enums.Rol;
import com.Tukincho.Tukincho.repositorios.ServiciosExtraRepositorio;
import com.Tukincho.Tukincho.servicios.InmuebleServicio;
import com.Tukincho.Tukincho.servicios.PropietarioServicio;
import com.Tukincho.Tukincho.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Jonatan Atencio
 * @version 1.0
 * @data 13/11/2023
 */
@Controller
@RequestMapping("/propiedad")
public class InmuebleControlador {

    @Autowired
    PropietarioServicio propietarioServicio;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    InmuebleServicio inmuebleServicio;
    @Autowired
    ServiciosExtraRepositorio serviciosExtrasRepositorio;

    
    
    
    @GetMapping("/registrar")
    @PreAuthorize("hasAnyRole('ROLE_PROPIETARIO', 'ROLE_USUARIO')")
    public String registrarInmueble(ModelMap modelo) {
        modelo.addAttribute("provincias", Provincia.values());
        modelo.put("Inmueble", new Inmueble());
        modelo.put("servicios", serviciosExtrasRepositorio.findAll());
        return "propiedad_nueva.html";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestPart("imagenes[]") List<MultipartFile> imagenes,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam Long precio,
            @RequestParam String otrosDetalles,
            @RequestParam String direccion,
            @RequestParam Provincia provincia,
            @RequestParam(required = false) List<Reserva> reservas,
            @RequestParam(value = "serviciosExtras", required = false) List<String> serviciosExtras,
            HttpSession session,
            HttpServletRequest request,
            ModelMap modelo) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        Propietario propietario = propietarioServicio.buscarPropietario(logueado.getId());
        if (propietario == null) {
            Usuario usuario = usuarioServicio.buscarUsuarioPorId(logueado.getId());
            //propietario = propietarioServicio.crearPropietario(usuario);
            usuario.setRol(Rol.PROPIETARIO);
            propietario = propietarioServicio.buscarPropietario(logueado.getId());

        }
        try {
            // Obtener los valores de servicios extras y sus precios
            Map<String, Long> preciosServiciosExtras = new HashMap<>();

            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("precio_")) {
                    String servicioId = paramName.replace("precio_", "");
                    Long precioServicio = Long.parseLong(request.getParameter(paramName));
                    preciosServiciosExtras.put(servicioId, precioServicio);
                }
            }
            
            Inmueble inmueble= new Inmueble();//creo un inmueble para obtener su id;
            List<InmuebleServicioExtra> inmuebleServiciosExtra = crearInmuebleServiciosExtras(preciosServiciosExtras,inmueble);
            List<Reserva> reserva = null;
            inmuebleServicio.crearInmueble(propietario,inmueble, nombre, descripcion, precio, otrosDetalles, direccion, provincia, reservas, imagenes, inmuebleServiciosExtra);
            modelo.put("exito", "El inmueble se guardó correctamente!");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "propiedad_nueva.html";
        }

        return "propiedades_listar.html";
    }

    
    
    private List<InmuebleServicioExtra> crearInmuebleServiciosExtras(Map<String, Long> preciosServiciosExtras,Inmueble inmueble) {
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
    public String listarPropiedades(ModelMap modelo) {
        List<Inmueble> propiedades = inmuebleServicio.listaDeInmuebles();
        modelo.put("propiedades", propiedades);
        return "propiedades_listar.html";
    }

    @GetMapping("/detalle/{id}")
    public String detalleDelInmueble(@PathVariable("id") String id, ModelMap modelo) {
        System.out.println("ingresa a detalle");
        Inmueble inmueble;
        try {
            inmueble = inmuebleServicio.buscarInmueblePorId(id);
            modelo.put("propiedad", inmueble);
        } catch (Exception ex) {
            return "propiedad_listar.html";
        }

        return "inmueble_detalle.html";
    }

    @PostMapping("/editar/{id}")
    public String editarInmueble(@PathVariable String id,
            @RequestPart(name = "imagenes", required = false) List<MultipartFile> nuevasImagenes,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam Long precio,
            @RequestParam String otrosDetalles,
            @RequestParam String direccion,
            @RequestParam Provincia provincia,
            @RequestParam boolean activa,
            @RequestParam List<Reserva> reserva,
            @RequestParam List<InmuebleServicioExtra> inmuebleServiciosExtra,
            ModelMap modelo) {

        try {
            Inmueble inmueble = inmuebleServicio.buscarInmueblePorId(id);

            if (inmueble != null) {
                // Actualizar los campos del inmueble con los nuevos valores
                inmueble.setNombre(nombre);
                inmueble.setDescripcionDelInmueble(descripcion);
                inmueble.setPrecioPorNoche(precio);
                inmueble.setOtrosDetallesDelInmueble(otrosDetalles);
                inmueble.setDireccion(direccion);
                inmueble.setProvincia(provincia);
                inmueble.setActiva(activa);

                // Guardar el inmueble actualizado en la base de datos
                inmuebleServicio.editarInmueble(id, nombre, descripcion, precio, otrosDetalles, direccion, provincia, reserva, inmuebleServiciosExtra, activa, nuevasImagenes);

                modelo.put("exito", "El inmueble se actualizó correctamente!");
            } else {
                modelo.put("error", "No se encontró el inmueble con el ID proporcionado.");
                return "error.html"; // Puedes redirigir a una página de error o hacer lo que consideres adecuado.
            }
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            System.out.println(ex.getMessage());
            return "error.html"; // Puedes redirigir a una página de error o hacer lo que consideres adecuado.
        }

        return "redirect:/propiedades/listar"; // Redirigir a la página de listar propiedades después de la edición.
    }
}
