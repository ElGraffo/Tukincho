package com.grupoK.Tukincho.repositorios;

import com.grupoK.Tukincho.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    @Query("select u from Usuario u where u.nombre = :nombre")
    public Usuario buscarPorNombre(@Param("nombre") String nombre);

}