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

@Service
public class UsuarioServicio implements UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param nombre     El nombre del nuevo usuario.
     * @param email      El correo electrónico del nuevo usuario.
     * @param password   La contraseña del nuevo usuario.
     * @param password2  La confirmación de la contraseña del nuevo usuario.
     * @throws Exception Si se encuentran errores en los parámetros proporcionados.
     */
    @Transactional
    public void registrar(String nombre, String email, String password, String password2) throws Exception {
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario();
        //cuando hago un new usuario se supone que tiene id?
        usuario = usuarioRepositorio.save(usuario);
        //establesco la imagen por defecto para el usuario
        Imagen imagen = new Imagen();
        byte[] defaultImageBytes = obtenerBytesDeImagenPorDefecto();
        imagen.setMime("image/jpeg"); // Establecer el tipo MIME según el tipo de la imagen predeterminada
        imagen.setNombre("default-user-profile.jpg");
        imagen.setContenido(defaultImageBytes);
        imagen = imagenRepositorio.save(imagen);
        usuario.setImagen(imagen);

        usuario.setActivo(true);
        usuario.setNombreUsuario(nombre);
        usuario.setEmail(email);
        usuario.setRol(Rol.USUARIO);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        System.out.println(usuario.toString());
        usuarioRepositorio.save(usuario);
    }
    /**
     * Edita los atributos de un usuario existente en el sistema.
     *
     * @param idUsuario  El ID del usuario a editar.
     * @param rol        El nuevo rol del usuario.
     * @param id         El nuevo ID del usuario.
     * @param email      El nuevo correo electrónico del usuario.
     * @param nombre     El nuevo nombre del usuario.
     * @param password   La nueva contraseña del usuario.
     * @param password2  La confirmación de la nueva contraseña del usuario.
     * @throws Exception Si se encuentran errores en los parámetros proporcionados.
     */
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

    
    /**
     * Edita los atributos de un usuario existente en el sistema. Este método se
     * utiliza para realizar modificaciones automáticas del perfil de un usuario,
     * excluyendo cambios en roles y activación.
     *
     * @param idUsuario  El ID del usuario a editar.
     * @param email      El nuevo correo electrónico del usuario.
     * @param nombre     El nuevo nombre del usuario.
     * @param password   La nueva contraseña del usuario.
     * @param password2  La confirmación de la nueva contraseña del usuario.
     * @throws Exception Si se encuentran errores en los parámetros proporcionados.
     */
    @Transactional
    public void autoEditarUsuario(String idUsuario, String email, String nombre, String password,
            String password2) throws Exception {

        validar(nombre, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuario.setEmail(email);
            usuario.setNombreUsuario(nombre);
//            usuario.setActivo(true);
//            usuario.setRol(Rol.USUARIO); ////y esto por que?
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuarioRepositorio.save(usuario);
        }
    }




    /**
     * Busca un usuario por su ID en el sistema.
     *
     * @param id El ID del usuario que se desea buscar.
     * @return El objeto Usuario si se encuentra, de lo contrario, devuelve null.
     */
    public Usuario buscarUsuarioPorId(String id) {
        try {
            return usuarioRepositorio.getReferenceById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     *
     * @return Lista de usuarios, o null si hay algún problema al acceder a la base de datos.
     */
    public List<Usuario> listarUsuarios() {
        try {
            return usuarioRepositorio.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Cambia el rol de un usuario entre "USUARIO" y "ADMIN".
     *
     * @param id ID del usuario cuyo rol se va a cambiar.
     */
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
    
    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     */
    @Transactional
    public void borrarUsuario(String id) {
        Optional<Usuario> validacion = usuarioRepositorio.findById(id);
        if (validacion.isPresent()) {
            Usuario usuario = validacion.get();
            usuarioRepositorio.delete(usuario);
        }
    }
/**
 * Valida los datos proporcionados durante el registro o edición de un usuario.
 *
 * @param nombre     Nombre del usuario.
 * @param email      Correo electrónico del usuario.
 * @param password   Contraseña del usuario.
 * @param password2  Confirmación de la contraseña del usuario.
 * @throws Exception Si algún dato es nulo, vacío o no cumple con los requisitos.
 */
private void validar(String nombre, String email, String password, String password2) throws Exception {
    if (nombre == null || nombre.isEmpty()) {
        throw new Exception("El nombre no puede ser nulo o estar vacío");
    }
    if (email == null || email.isEmpty()) {
        throw new Exception("El correo electrónico no puede estar vacío o ser nulo");
    }
    if (password.isEmpty() || password == null || password.length() <= 5) {
        throw new Exception("La contraseña no puede estar vacía o tener menos de 6 caracteres");
    }
    if (!password.equals(password2)) {
        throw new Exception("Las contraseñas ingresadas deben ser iguales");
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
    /**
     * Carga los detalles del usuario por nombre de usuario para la autenticación.
     *
     * @param nombreUsuario Nombre de usuario para buscar en la base de datos.
     * @return Detalles del usuario para la autenticación.
     * @throws UsernameNotFoundException Si el nombre de usuario no se encuentra en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorNombre(nombreUsuario);
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

    /**
     * Método que obtiene los bytes de la imagen por defecto del usuario.
     *
     * @return Bytes de la imagen por defecto en formato de array de bytes.
     * @throws IOException Si ocurre un error al leer la imagen por defecto.
     */
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
