package com.Tukincho.Tukincho.repositorios;
import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.enums.Provincia;
import com.Tukincho.Tukincho.entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad Inmueble.
 * Utiliza Spring Data JPA y extiende de JpaRepository para proporcionar métodos de acceso a datos comunes.
 */
@Repository
public interface InmuebleRepositorio extends JpaRepository<Inmueble, String> {

¡Por supuesto! Aquí está la documentación para el repositorio que acabas de proporcionar:

java
Copy code
package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Inmueble;
import com.Tukincho.Tukincho.enums.Provincia;
import com.Tukincho.Tukincho.entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad Inmueble.
 * Utiliza Spring Data JPA y extiende de JpaRepository para proporcionar métodos de acceso a datos comunes.
 */
@Repository
public interface InmuebleRepositorio extends JpaRepository<Inmueble, String> {

    /**
     * Busca inmuebles por precio por noche.
     *
     * @param precioPorNoche El precio por noche a buscar.
     * @return Lista de inmuebles que coinciden con el precio por noche proporcionado.
     */
    @Query("SELECT i FROM Inmueble i where i.precioPorNoche = :precioPorNoche")
    public List<Inmueble> buscarInmueblePorPrecio(@Param("precioPorNoche") Long precioPorNoche);

    /**
     * Busca inmuebles por provincia.
     *
     * @param provincia La provincia a buscar.
     * @return Lista de inmuebles que se encuentran en la provincia proporcionada.
     */
    @Query("SELECT i FROM Inmueble i where i.provincia = :provincia")
    public List<Inmueble> buscarInmueblePorProvincia(@Param("provincia") Provincia provincia);

    /**
     * Busca inmuebles por dirección, utilizando coincidencia parcial.
     *
     * @param direccion La dirección a buscar de forma parcial.
     * @return Lista de inmuebles cuyas direcciones contienen la cadena proporcionada.
     */
    @Query("SELECT i FROM Inmueble i where i.direccion LIKE CONCAT('%',:direccion,'%')")
    public List<Inmueble> buscarInmueblePorDireccion(@Param("direccion") String direccion);

    /**
     * Busca inmuebles propiedad de un propietario específico.
     *
     * @param propietarioId El ID del propietario.
     * @return Lista de inmuebles propiedad del propietario con el ID proporcionado.
     */
    @Query("SELECT p.inmuebles FROM Propietario p WHERE p.id = :propietarioId")
    List<Inmueble> buscarPropietarioPorInmueble(@Param("propietarioId") String propietarioId);

    // todo -> hacer query de buscar por localidad

}

