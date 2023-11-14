package com.GrupoK.Tukincho.servicios;
import com.GrupoK.Tukincho.entidades.Usuario;
import com.GrupoK.Tukincho.enums.Rol;
import com.GrupoK.Tukincho.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String id, String nombre, Rol rol, Boolean alta, String email, String password, String password2) throws Exception {
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario() {};
        usuario.setId(id);
        usuario.setActivo(alta);
        usuario.setEmail(email);
        usuario.setRol(rol);
        usuario.setPassword(password);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void actualizar( String idUsuario, Rol rol, String id,String email, String nombreUsuario,  String password, String password2) throws Exception{
        validar(nombreUsuario, email, password, password2);
        Optional<Usuario> respuesta= usuarioRepositorio.findById(idUsuario);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setId(id);
            usuario.setEmail(email);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setActivo(true);
            usuario.setRol(Rol.USUARIO);
            usuarioRepositorio.save(usuario);
        }
    }

    public Usuario buscarUsuarioPorId(String id){
        try{
            return usuarioRepositorio.getOne(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> listarUsuarios(){
        try{
            return usuarioRepositorio.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void cambiarRol(String id){
        Optional <Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            Usuario usuario =respuesta.get();
            if(usuario.getRol().equals(Rol.USUARIO)){
                usuario.setRol(Rol.ADMIN);
            }else if(usuario.getRol().equals(Rol.ADMIN))
                usuario.setRol(Rol.USUARIO);
        }
    }

    public void borrarUsuario(String id) {
        Optional<Usuario> validacion = usuarioRepositorio.findById(id);
        if (validacion.isPresent()) {
            Usuario usuario = validacion.get();
            usuarioRepositorio.delete(usuario);
        }
    }

    private void validar(String nombre, String email, String password, String password2) throws Exception {
        if (nombre == null|| nombre.isEmpty()) throw new Exception("El nombre no puede ser nulo o estar vacio");
        if (email == null || email.isEmpty()) throw new Exception("El email no puede estar vacio o ser nulo");
        if (password.isEmpty() || password == null || password.length() <= 5) throw new Exception("La contraseña no puede estar vacio o tener menos de 5 digitos");
        if (!password.equals(password2)) throw new Exception("Las contraseñas ingresadas deben ser iguales");
        }
    }
