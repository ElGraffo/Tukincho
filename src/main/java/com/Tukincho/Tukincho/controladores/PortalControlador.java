package com.Tukincho.Tukincho.controladores;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.enums.Rol;
import com.Tukincho.Tukincho.excepciones.MiException;
import com.Tukincho.Tukincho.repositorios.PropietarioRepositorio;
import com.Tukincho.Tukincho.repositorios.UsuarioRepositorio;
import com.Tukincho.Tukincho.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
    @Autowired
    PropietarioRepositorio propietarioRepositorio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    @GetMapping("/index")
    public String mostrarIndex() {
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            String password2, ModelMap modelo) throws Exception {
        try {
            System.out.println(nombre);
            usuarioServicio.registrar(nombre, email, password, password2);
            System.out.println("registrando ok");
            modelo.put("exito", "Usuario registrado correctamente!!!!");
            return "index.html";
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            modelo.put("error", e.getMessage());
            return "registro.html";
        }
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin.html";
    }
    
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error == null) {
            modelo.put("exito", "se ha iniciado sesion correctamente");
            return "login.html";
        }else {
            modelo.put("error", "Usuario o contraseña inválidos");
            return null;
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")

    public String inicio(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "index.html";
    }
    
     @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_PROPIETARIO')")
    @GetMapping("/perfil")
     
     public String perfil(ModelMap modelo, HttpSession session){
         
         Usuario usuario =(Usuario) session.getAttribute("usuariosession");
         
         modelo.put("usuario", usuario);
         
         return "usuario_modificar.html";
     }
     
     
      @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
      
      public String actualizar( MultipartFile archivo, @PathVariable String id, @RequestParam String nombre,
                                @RequestParam String email, @RequestParam String password, @RequestParam String password2,
                                ModelMap modelo)throws MiException, Exception{
       
          try{
        usuarioServicio.editarUsuario(password2, Rol.ADMIN, id, email, nombre, password, password2);
        modelo.put("exito", "El usuario ha sido actualizado con exito");
          
          return "index.html";
      }catch(MiException ex){
          
        modelo.put("error", ex.getMessage());
        modelo.put("password", password);
        modelo.put("email", email);
        
        return "usuario_modificar.html";
      }

    }
}
