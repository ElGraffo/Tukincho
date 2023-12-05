package com.Tukincho.Tukincho.entidades;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Propietario extends Usuario {
    @OneToMany(mappedBy = "propietario", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Inmueble> inmuebles;
    @OneToMany(mappedBy = "propietario", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Reserva> reserva;
    }


