package com.Tukincho.Tukincho.controladores;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String registro(@RequestPart(required = false) List<MultipartFile> imagenes,@RequestParam String nombre,
            @RequestParam String descripcion,@RequestParam Long precio,@RequestParam String otrosDetalles
    ,@RequestParam String direccion, @RequestParam Provincia provincia, @RequestParam boolean activa
    ,ModelMap modelo){
        
        System.out.println("ingreso al controlador de registro");
        //session.usuariosession.getId();
        Propietario propietario = propietarioServicio.buscarPropietario("id");
        if(propietario== null){
            Usuario usuario = usuarioServicio.buscarUsuarioPorId("ad6a1980-bd41-421e-9603-507cd9dc8437");
            propietarioServicio.crearPropietario(usuario);
            propietario = propietarioServicio.buscarPropietario(usuario.getId());
        }
        List<Reserva> reservas= null;
        try {
            inmuebleServicio.crearImueble(propietario, descripcion, precio, otrosDetalles, direccion, provincia, activa, reservas,imagenes);
            modelo.put("exito", "El inmueble se guardo correctamente!");
        } catch (Exception ex) {
            modelo.put("error",ex.getMessage());
            System.out.println(ex.getMessage());
            return "propiedad_nueva.html";
        }
        return "propiedades_listar.html";
    }
    
    
    @GetMapping("/listar")
    @ResponseBody
    public String listarPropiedades(){
        return "TODO_listarPropiedes";
    }
    
    
}
