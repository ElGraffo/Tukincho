package com.Tukincho.Tukincho.entidades;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Propietario extends Usuario {

    private String idPropietario;
    @OneToMany(mappedBy = "propietario", fetch = FetchType.EAGER)
    private List<Inmueble> inmuebles;
    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

}