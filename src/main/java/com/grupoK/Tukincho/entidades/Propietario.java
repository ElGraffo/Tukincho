<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Propietario.java
package com.GrupoK.Tukincho.entidades;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Propietario extends  Usuario{
   
    private String idPropietario;
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
=======
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
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/entidades/Propietario.java
    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

<<<<<<< HEAD:src/main/java/com/grupoK/Tukincho/entidades/Propietario.java
}
=======
}
>>>>>>> developer:src/main/java/com/Tukincho/Tukincho/entidades/Propietario.java
