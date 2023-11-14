package repositorios;

<<<<<<< HEAD:src/main/java/com/Tukincho/Tukincho/repositorios/UsuarioRepositorio.java
import com.grupoK.Tukincho.entidades.Usuario;
=======
import entidades.Usuario;
>>>>>>> 6932bdf116a9242629f4ed4b2c5b1b2b3fc811cb:src/main/java/repositorios/UsuarioRepositorio.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    @Query("select u from Usuario u where u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorNombre(@Param("nombreUsuario") String nombreUsuario);

}