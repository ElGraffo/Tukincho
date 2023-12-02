package com.Tukincho.Tukincho.controladores;
import java.util.List;

import com.Tukincho.Tukincho.repositorios.InmuebleRepositorio;
import com.Tukincho.Tukincho.repositorios.ReservaRepositorio;
import com.Tukincho.Tukincho.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Tukincho.Tukincho.entidades.ServiciosExtra;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.repositorios.ServiciosExtraRepositorio;
import com.Tukincho.Tukincho.servicios.ServiciosExtraServicio;
import com.Tukincho.Tukincho.servicios.UsuarioServicio;
/**
 * Controlador que gestiona las operaciones administrativas en el sistema.
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ServiciosExtraServicio serviciosExtraServicio;
    @Autowired
    private ServiciosExtraRepositorio serviciosExtraRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private InmuebleRepositorio inmuebleRepositorio;
    
    /**
     * Método que redirige a la página de panel administrativo (dashboard).
     *
     * @param modelo Modelo para agregar atributos a la vista.
     * @return Vista del panel administrativo (admin.html).
     */
    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo){
        modelo.put("usuarios", usuarioRepositorio.findAll());
        modelo.put("reservas", reservaRepositorio.findAll());
        modelo.put("propiedades", inmuebleRepositorio.findAll());
        return"admin.html";
    }
    
    /**
     * Método que muestra la lista de usuarios en el sistema.
     *
     * @param modelo Modelo para agregar atributos a la vista.
     * @return Vista de la lista de usuarios (usuario_list.html).
     */
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario>usuarios =usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_list.html";
    }
    
    /**
     * Método que cambia el rol de un usuario.
     *
     * @param id ID del usuario a modificar.
     * @return Redirección a la lista de usuarios.
     */
    @GetMapping("/modificarRol/{id}")
   public String cambiarRol(@PathVariable String id) {
       usuarioServicio.cambiarRol(id);
       return "redirect:/admin/usuarios";
   }
   
   /**
    * 
    * @param modelo envia un objeto del tipo ServicioExtra para poder ser llenado
    * desde el formulario, es equivalente a recibir un string nombre y asignarlo como
    * servico.setNombreDelServicioExtra(nombre).
    * @return devuelve al usuario el formulario que va a rellenar para crear un nuevo 
    * servicio.
    * @authot Jonatan Atencio
    */
   @GetMapping("/servicios/nuevo")
   public String formCrearNuevoServicio(ModelMap modelo){
       modelo.put("servicio",new ServiciosExtra());
       return "servicios_crear.html";
   }
   
   /**
    * 
    * Trae los datos del formulario de nuevo servicio y lo guarda en la base de datos
    * @param servicio Recibe un Objeto ServiciosExtra desde el formulario
    * @param modelo Para pasar los resultado de la persistencia a la base de Datos.
    * @return devuelve una vista para exito y otra por si hay error.
    * @author Jonatan Atencio.
    */
   @PostMapping("/servicios/registro")
    public String crearNuevoServicio(@ModelAttribute ServiciosExtra servicio,ModelMap modelo,BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()){
                return "servicios_crear.html";
            }
            serviciosExtraServicio.crear(servicio);
            modelo.put("exito","el servicio se creao correctamente");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "servicios_crear.html";
        }
        
        return "redirect:/admin/servicios/listar";
    }
    /**
     * Método que muestra la lista de servicios extras.
     *
     * @param modelo Modelo para agregar atributos a la vista.
     * @return Vista de la lista de servicios extras (serviciosExtras_listar.html).
     */
    @GetMapping("/servicios/listar")
    public String serviciosListar(ModelMap modelo){
        modelo.put("servicios",serviciosExtraRepositorio.findAll());
        return "serviciosExtras_listar.html";
    }
}
