package com.Tukincho.Tukincho.repositorios;

import com.Tukincho.Tukincho.entidades.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepositorio extends JpaRepository<Feedback, String> {

}