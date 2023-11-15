package com.GrupoK.Tukincho.repositorios;


import com.GrupoK.Tukincho.entidades.Usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/repositorios/UsuarioRepositorio.java
    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorNombre(@Param("nombreUsuario") String nombreUsuario);
}
=======
    @Query("select u from Usuario u where u.nombre = :nombre")
    public Usuario buscarPorNombre(@Param("nombre") String nombre);
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/repositorios/UsuarioRepositorio.java

