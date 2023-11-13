package com.Tukincho.Tukincho.servicios;

import com.Tukincho.Tukincho.entidades.Imagen;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.enums.Rol;
import com.Tukincho.Tukincho.repositorios.UsuarioRepositorio;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws Exception {
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        // String defaultImagePath = "src//main//resources//static//imagenes//Default-Profile.jpg";
        // InputStream defaultImageStream = getClass().getResourceAsStream(defaultImagePath);
        // Imagen imagen = new Imagen();
        // byte[] defaultImageBytes = IOUtils.toByteArray(defaultImageStream);
        // imagen.setMime("image/jpeg"); // Establecer el tipo MIME según el tipo de la imagen predeterminada
        // imagen.setNombre("default-user-profile.jpg");
        // imagen.setContenido(defaultImageBytes);
        // usuario.setImagen(imagen);
        usuario.setActivo(true);
        usuario.setEmail(email);
        usuario.setRol(Rol.USUARIO);
        usuario.setPassword(password);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void editarUsuario(String idUsuario, Rol rol, String id, String email, String nombre, String password,
            String password2) throws Exception {
        validar(nombre, email, password, password2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setId(id);
            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setActivo(true);
            usuario.setRol(Rol.USUARIO);
            usuarioRepositorio.save(usuario);
        }
    }

    public Usuario buscarUsuarioPorId(String id) {
        try {
            return usuarioRepositorio.getReferenceById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> listarUsuarios() {
        try {
            return usuarioRepositorio.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRol().equals(Rol.USUARIO)) {
                usuario.setRol(Rol.ADMIN);
            } else if (usuario.getRol().equals(Rol.ADMIN))
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
        if (nombre == null || nombre.isEmpty())
            throw new Exception("El nombre no puede ser nulo o estar vacio");
        if (email == null || email.isEmpty())
            throw new Exception("El email no puede estar vacio o ser nulo");
        if (password.isEmpty() || password == null || password.length() <= 5)
            throw new Exception("La contraseña no puede estar vacio o tener menos de 5 digitos");
        if (!password.equals(password2))
            throw new Exception("Las contraseñas ingresadas deben ser iguales");
    }
}
