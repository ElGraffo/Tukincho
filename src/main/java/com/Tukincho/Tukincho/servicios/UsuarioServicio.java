package com.Tukincho.Tukincho.servicios;
import com.Tukincho.Tukincho.entidades.Imagen;
import com.Tukincho.Tukincho.entidades.Usuario;
import com.Tukincho.Tukincho.enums.Rol;
import com.Tukincho.Tukincho.repositorios.ImagenRepositorio;
import com.Tukincho.Tukincho.repositorios.UsuarioRepositorio;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public void registrar(String nombre, String email, String password, String password2, MultipartFile imagen) throws Exception {
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario();
        usuario = usuarioRepositorio.save(usuario);
        //Si no pasa imagen establezco la imagen por defecto para el usuario
        if(imagen == null || (imagen != null && imagen.getBytes().length == 0)) {
            Imagen imagenDefault = new Imagen();
            byte[] defaultImageBytes = obtenerBytesDeImagenPorDefecto();
            imagenDefault.setMime("image/jpeg"); // Establecer el tipo MIME según el tipo de la imagen predeterminada
            imagenDefault.setNombre("default-user-profile.jpg");
            imagenDefault.setContenido(defaultImageBytes);
            imagenDefault = imagenRepositorio.save(imagenDefault);
            usuario.setImagen(imagenDefault);
        } else {
            Imagen imagenUsuario = new Imagen();
            byte[] bytesImagen = imagen.getBytes();
            imagenUsuario.setMime(imagen.getContentType());
            imagenUsuario.setNombre(imagen.getOriginalFilename());
            imagenUsuario.setContenido(bytesImagen);
            imagenUsuario = imagenRepositorio.save(imagenUsuario);
            usuario.setImagen(imagenUsuario);
        }
        usuario.setActivo(true);
        usuario.setNombreUsuario(nombre);
        usuario.setEmail(email);
        usuario.setRol(Rol.USUARIO);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        System.out.println(usuario.toString());
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
            usuario.setNombreUsuario(nombre);
            usuario.setActivo(true);
            usuario.setRol(Rol.USUARIO);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
       
            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void autoEditarUsuario(String idUsuario, String email, String nombre, String password,
            String password2, MultipartFile imagen) throws Exception {

        validar(nombre, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setEmail(email);
            usuario.setNombreUsuario(nombre);
//            usuario.setActivo(true);
//            usuario.setRol(Rol.USUARIO); ////y esto por que?

            if(imagen != null && imagen.getBytes().length > 0) {
                Imagen imagenUsuario = new Imagen();
                byte[] bytesImagen = imagen.getBytes();
                imagenUsuario.setMime(imagen.getContentType());
                imagenUsuario.setNombre(imagen.getOriginalFilename());
                imagenUsuario.setContenido(bytesImagen);
                imagenUsuario = imagenRepositorio.save(imagenUsuario);
                usuario.setImagen(imagenUsuario);
            }

            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuarioRepositorio.save(usuario);
            loadUserByUsername(usuario.getNombreUsuario());
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


    public Usuario buscarUsuarioPorRolUsuario(String nombreUsuario)throws Exception{
        return usuarioRepositorio.buscarUsuarioRolUsuario(nombreUsuario);
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
            } else if (usuario.getRol().equals(Rol.ADMIN)) {
                usuario.setRol(Rol.USUARIO);
            }
        }
    }

    @Transactional
    public void borrarUsuario(String id) {
        Optional<Usuario> validacion = usuarioRepositorio.findById(id);
        if (validacion.isPresent()) {
            Usuario usuario = validacion.get();
            usuarioRepositorio.delete(usuario);
        }
    }

    private void validar(String nombre, String email, String password, String password2) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre no puede ser nulo o estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new Exception("El email no puede estar vacio o ser nulo");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new Exception("La contraseña no puede estar vacio o tener menos de 5 digitos");
        }
        if (!password.equals(password2)) {
            throw new Exception("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        //Usuario usuario = usuarioRepositorio.buscarPorNombre(nombreUsuario);
        //buscar por nombre de usuario podria devolver una lista, recorrer y obtener el rol user, o propietario, etc
        List<Usuario> listUsuario= usuarioRepositorio.buscarPorNombreUsuario(nombreUsuario);
        Usuario usuario;
        //if(listUsuario!=null && listUsuario.size()>0)
        usuario= listUsuario.get(0);
//         else
//            usuario = usuarioRepositorio.buscarPorNombre(nombreUsuario);
//

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getNombreUsuario(), usuario.getPassword(), permisos);

        } else {
            return null;
        }
    }

    public byte[] obtenerBytesDeImagenPorDefecto() throws IOException {
        // Ruta relativa del archivo de imagen por defecto
        String rutaImagenPorDefecto = "static/imagenes/default-profile.jpg";

        // Cargar el recurso de la imagen por defecto desde el classpath
        Resource resource = new ClassPathResource(rutaImagenPorDefecto);

        // Leer el contenido del InputStream y convertirlo a un array de bytes
        try (InputStream inputStream = resource.getInputStream()) {
            return IOUtils.toByteArray(inputStream);
        }
    }
}
