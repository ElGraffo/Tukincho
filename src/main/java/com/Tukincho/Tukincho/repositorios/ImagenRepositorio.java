
package com.Tukincho.Tukincho.repositorios;


import com.Tukincho.Tukincho.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad Imagen.
 * Utiliza Spring Data JPA y extiende de JpaRepository para proporcionar métodos de acceso a datos comunes.
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
}
