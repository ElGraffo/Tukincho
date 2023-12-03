
package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Propietario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad Propietario.
 * Utiliza Spring Data JPA y extiende de JpaRepository para proporcionar métodos de acceso a datos comunes.
 */
@Repository
public interface PropietarioRepositorio extends JpaRepository<Propietario, String> {
      /**
     * Busca un propietario por su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario del propietario a buscar.
     * @return Lista de propietarios que coinciden con el nombre de usuario proporcionado.
     */
      @Query("select p from Propietario p where p.nombreUsuario = :nombreUsuario")
    List< Propietario> buscarPorPropietario(@Param("nombreUsuario") String nombreUsuario);

}
