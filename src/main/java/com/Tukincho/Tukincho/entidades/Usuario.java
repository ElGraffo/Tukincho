package com.Tukincho.Tukincho.entidades;

import com.Tukincho.Tukincho.enums.Rol;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario{
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String id;
    private String nombre;
    private String email;
    private String password;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;
}