package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * Repositorio que gestiona las operaciones de persistencia para la entidad Feedback.
 * Utiliza Spring Data JPA y extiende de JpaRepository para proporcionar métodos de acceso a datos comunes.
 */
@Repository
public interface FeedbackRepositorio extends JpaRepository<Feedback, String> {

}
