package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepositorio extends JpaRepository<Feedback, String> {

}