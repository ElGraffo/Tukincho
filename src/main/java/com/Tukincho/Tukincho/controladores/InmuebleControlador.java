package com.Tukincho.Tukincho.controladores;

import com.Tukincho.Tukincho.entidades.*;
import com.Tukincho.Tukincho.enums.Provincia;
import com.Tukincho.Tukincho.enums.Rol;
import com.Tukincho.Tukincho.repositorios.ServiciosExtraRepositorio;
import com.Tukincho.Tukincho.servicios.InmuebleServicio;
import com.Tukincho.Tukincho.servicios.PropietarioServicio;
import com.Tukincho.Tukincho.servicios.UsuarioServicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Propietario propietario=null;
        System.out.println(logueado.getClass().getSimpleName());
        //Propietario propietario = propietarioServicio.buscarPropietario(logueado.getId());
        //if (propietario == null) {
        
        if(!logueado.getClass().getSimpleName().equals("Propietario")){
            System.out.println("NO ESTA COMO PROPIETARIO SE CREA PROPIETARIO");
           // Usuario usuario = usuarioServicio.buscarUsuarioPorId(logueado.getId());
            propietario = propietarioServicio.crearPropietario(logueado);
            //usuarioServicio.borrarUsuario(logueado.getId());
        }else{
            propietario=(Propietario) logueado;
        }
        try {
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
                    
                    preciosServiciosExtras.put(servicioId, precioServicio);
                }
            }
            
            Inmueble inmueble = new Inmueble();//creo un inmueble para obtener su id;
            List<InmuebleServicioExtra> inmuebleServiciosExtra = crearInmuebleServiciosExtras(preciosServiciosExtras, inmueble);
            List<Reserva> reserva = null;
            inmuebleServicio.crearInmueble(propietario, inmueble, nombre, descripcion, precio, otrosDetalles, direccion, provincia, reservas, imagenes, inmuebleServiciosExtra);
            modelo.put("exito", "El inmueble se guardó correctamente!");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "propiedad_nueva.html";
        }

        return "redirect:/propiedad/listar";
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

    @GetMapping("/listar")
    public String listarPropiedades(ModelMap modelo) {
        List<Inmueble> propiedades = inmuebleServicio.listaDeInmuebles();
        modelo.put("propiedades", propiedades);
        return "propiedades_listar.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROPIETARIO', 'ROLE_USUARIO')")
     @GetMapping("/mispropiedades/listar")
    public String listarPropiedadesPorPropietrio(HttpSession session, ModelMap modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        System.out.println(logueado.getRol());
        Propietario propietario= propietarioServicio.buscarPropietarioPorNombreUsuario(logueado.getNombreUsuario());
        System.out.println("Propietario ID: "+propietario.getId());
        List<Inmueble> propiedades = propietarioServicio.buscarPropietarioPorInmueble(propietario.getId());
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

    @GetMapping("/buscar")
    public String buscarInmueblesConFiltros(
            @RequestParam(value = "provincia", required = false) Provincia provincia,
            @RequestParam(value = "fechaEntrada", required = false) String fechaEntradaStr,
            @RequestParam(value = "fechaSalida", required = false) String fechaSalidaStr,
            Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaEntrada = parseFecha(fechaEntradaStr, dateFormat);
        Date fechaSalida = parseFecha(fechaSalidaStr, dateFormat);

        List<Inmueble> propiedades = inmuebleServicio.buscarInmueblesDisponibles(provincia, fechaEntrada, fechaSalida);

        // Puedes hacer algo con la lista de inmuebles, por ejemplo, pasarla al modelo para mostrarla en la vista.
        model.addAttribute("propiedades", propiedades);

        return "propiedades_listar.html";
    }

    private Date parseFecha(String fechaStr, SimpleDateFormat dateFormat) {
        try {
            if (fechaStr != null && !fechaStr.isEmpty()) {
                return dateFormat.parse(fechaStr);
            }
        } catch (ParseException e) {
            // Manejar la excepción según tus necesidades
            e.printStackTrace();
        }
        return null;
    }


}
