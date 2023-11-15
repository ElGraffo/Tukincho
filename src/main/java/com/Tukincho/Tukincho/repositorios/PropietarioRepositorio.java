<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/repositorios/PropietarioRepositorio.java
package com.GrupoK.Tukincho.repositorios;

import com.GrupoK.Tukincho.entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
=======
package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/repositorios/PropietarioRepositorio.java
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepositorio extends JpaRepository<Propietario, String> {

}
