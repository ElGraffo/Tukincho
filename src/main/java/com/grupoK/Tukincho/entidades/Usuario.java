<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Usuario.java
package com.grupoK.Tukincho.entidades;
import com.Tukincho.Tukincho.enums.Rol;
=======
package entidades;
import enums.Rol;
>>>>>>> 6932bdf116a9242629f4ed4b2c5b1b2b3fc811cb:src/main/java/entidades/Usuario.java

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Data
<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Usuario.java
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
=======
public abstract class Usuario { // PREGUNTAR A JONA COMO HACER EL JOIN
>>>>>>> 6932bdf116a9242629f4ed4b2c5b1b2b3fc811cb:src/main/java/entidades/Usuario.java
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
    private Imagen imagen;
}