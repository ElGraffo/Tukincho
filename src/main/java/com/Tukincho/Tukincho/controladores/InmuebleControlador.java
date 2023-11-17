package com.Tukincho.Tukincho.controladores;

import com.Tukincho.Tukincho.entidades.Imagen;
import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.entidades.Propietario;
import com.Tukincho.Tukincho.entidades.Reserva;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.enums.Provincia;
import com.Tukincho.Tukincho.servicios.InmuebleServicio;
import com.Tukincho.Tukincho.servicios.PropietarioServicio;
import com.Tukincho.Tukincho.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    
    @GetMapping("/registrar")
    public String registrarInmueble(ModelMap modelo){
        modelo.addAttribute("provincias", Provincia.values());
        return "propiedad_nueva.html";
    }
    
    
    
    @PostMapping("/registro")
    public String registro(@RequestPart("imagenes[]") List<MultipartFile> imagenes,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam Long precio,@RequestParam String otrosDetalles,
            @RequestParam String direccion,
            @RequestParam Provincia provincia, 
            @RequestParam boolean activa,
            ModelMap modelo){
        
        //session.usuariosession.getId();
        Propietario propietario = propietarioServicio.buscarPropietario("id");
        if(propietario== null){
            Usuario usuario = usuarioServicio.buscarUsuarioPorId("bcdc2cf4-1f1c-44eb-89bc-62692654f864");
            propietario = propietarioServicio.crearPropietario(usuario);
          
        }
        List<Reserva> reservas= null;
        try {
            inmuebleServicio.crearInmueble(propietario, nombre,descripcion, precio, otrosDetalles, direccion, provincia, activa, reservas,imagenes);
            modelo.put("exito", "El inmueble se guardo correctamente!");
        } catch (Exception ex) {
            modelo.put("error",ex.getMessage());
            return "propiedad_nueva.html";
        }
        return "propiedades_listar.html";
    }
    
    
    
    
    @GetMapping("/listar")
    public String listarPropiedades(ModelMap modelo) {
        List<Inmueble> propiedades = inmuebleServicio.listaDeInmuebles();
        modelo.put("propiedades", propiedades);
        return "propiedades_listar.html";
    }
    
    
    
    
    @GetMapping("/detalle/{id}")
    public String detalleDelInmueble(@PathVariable("id") String id, ModelMap modelo){
        System.out.println("ingresa a detalle");
        Inmueble inmueble;
        try {
            inmueble = inmuebleServicio.buscarInmueblePorId(id);
            modelo.put("propiedad",inmueble);
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
            inmuebleServicio.editarInmueble(id, nombre, descripcion, precio, otrosDetalles, direccion, provincia, reserva, nuevasImagenes);
            
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
