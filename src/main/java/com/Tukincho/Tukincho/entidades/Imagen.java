package com.Tukincho.Tukincho.entidades;

import java.util.Base64;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Imagen {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    @JoinColumn(name = "inmueble_id")
    private Inmueble inmueble;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String mime;
    private String nombre;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] contenido;
    private String contenidoBase64;
    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;
    public String generateBase64Image() {
        return Base64.getEncoder().encodeToString(this.contenido);
    }
    @Override
    public String toString(){
        return "Imagen["+
                "nombre"+nombre+"]";
                
    }
}
