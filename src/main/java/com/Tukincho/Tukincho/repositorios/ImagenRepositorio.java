
package com.Tukincho.Tukincho.repositorios;


import com.Tukincho.Tukincho.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
}
