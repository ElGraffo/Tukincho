<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Usuario.java
package com.GrupoK.Tukincho.entidades;


import com.GrupoK.Tukincho.enums.Rol;
=======
package com.Tukincho.Tukincho.entidades;

import com.Tukincho.Tukincho.enums.Rol;
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/entidades/Usuario.java
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Usuario.java
public class Usuario {   


=======
public class Usuario{
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/entidades/Usuario.java
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String id;
    private String nombreUsuario;
    private String email;
    private String password;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Rol rol;
<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Usuario.java
     @OneToOne
=======
    @OneToOne
    @JoinColumn(name = "imagen_id")
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/entidades/Usuario.java
    private Imagen imagen;
}