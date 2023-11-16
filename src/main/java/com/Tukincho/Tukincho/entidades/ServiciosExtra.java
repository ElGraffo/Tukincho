package com.Tukincho.Tukincho.entidades;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class ServiciosExtra {
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String id;
    String nombreDelServicioExtra;
    int precioDelServicioExtra;
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    Inmueble inmueble;
}
