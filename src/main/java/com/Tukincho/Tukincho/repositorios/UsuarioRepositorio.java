package com.Tukincho.Tukincho.repositorios;
import com.Tukincho.Tukincho.entidades.Reserva;
import com.Tukincho.Tukincho.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorNombre(@Param("nombreUsuario") String nombreUsuario);
    
    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario")
    List<Usuario>ListarUsuarios(@Param("nombreUsuario") String nombreUsuario);
    
    @Query("select u from Usuario u where u.activo = :activo")
    List<Usuario> filtrarUsuariosActivos(@Param("activo") Boolean activo);
    
   @Query("select u from Usuario u where u.reserva = :reserva")
   public Usuario filtrarPorReserva(@Param("reserva") List<Reserva> reserva);
    
    
}



