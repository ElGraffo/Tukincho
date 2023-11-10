package com.grupoK.Tukincho.repositorios;

import com.grupoK.Tukincho.entidades.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepositorio extends JpaRepository<Propietario, String> {

}
