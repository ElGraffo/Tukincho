package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Propietario;
import com.Tukincho.Tukincho.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad Usuario.
 * Utiliza Spring Data JPA y extiende de JpaRepository para proporcionar métodos de acceso a datos comunes.
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    /**
     * Busca un usuario por su nombre.
     * @param nombreUsuario El nombre de usuario a buscar.
     * @return El usuario encontrado, o null si no se encuentra.
     */
    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorNombre(@Param("nombreUsuario") String nombreUsuario);
    
    /**
     * Busca usuarios por su nombre de usuario.
     * @param nombreUsuario El nombre de usuario a buscar.
     * @return Lista de usuarios encontrados.
     */
    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario")
    public List<Usuario> buscarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
        /**
     * Busca un usuario por su nombre de usuario y rol 'USUARIO'.
     * @param nombreUsuario El nombre de usuario a buscar.
     * @return El usuario encontrado, o null si no se encuentra.
     */
    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario and u.rol='USUARIO'")
    public Usuario buscarUsuarioRolUsuario(@Param("nombreUsuario") String nombreUsuario);
    /**
     * Busca un propietario por su nombre de usuario y rol 'PROPIETARIO'.
     * @param nombreUsuario El nombre de usuario a buscar.
     * @return El propietario encontrado, o null si no se encuentra.
     */    
    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario and u.rol='PROPIETARIO'")
    public Propietario buscarUsuarioRolPropietario(@Param("nombreUsuario") String nombreUsuario);
        /**
     * Busca un usuario por su nombre de usuario y rol 'ADMIN'.
     * @param nombreUsuario El nombre de usuario a buscar.
     * @return El usuario encontrado, o null si no se encuentra.
     */

    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario and u.rol='ADMIN'")
    public Usuario buscarUsuarioRolAdmin(@Param("nombreUsuario") String nombreUsuario);
   
}



