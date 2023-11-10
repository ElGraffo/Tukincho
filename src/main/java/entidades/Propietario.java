package entidades;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Propietario extends Usuario{
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name ="uuid", strategy= "uuid2")
    private String idPropietario;
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

}
