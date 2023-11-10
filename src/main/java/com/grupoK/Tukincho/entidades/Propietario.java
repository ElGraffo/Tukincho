package com.grupoK.Tukincho.entidades;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Propietario extends Usuario{
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String idPropietario;
    @OneToMany(mappedBy = "propietario", fetch = FetchType.EAGER)
    private List<Inmueble> inmuebles;
    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
    @OneToOne
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;
}
