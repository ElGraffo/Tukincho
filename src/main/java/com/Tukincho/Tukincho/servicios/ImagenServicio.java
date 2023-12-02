package com.Tukincho.Tukincho.servicios;
import com.Tukincho.Tukincho.entidades.Imagen;
import java.util.Optional;
import com.Tukincho.Tukincho.repositorios.ImagenRepositorio;
import java.io.InputStream;
import javax.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
/**
 * Servicio que gestiona las operaciones relacionadas con las imágenes en el sistema.
 *
 * @Service Indica que esta clase es un servicio de Spring que puede ser inyectado en otras clases.
 */
@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;
    /**
     * Guarda una nueva imagen en la base de datos a partir de un archivo proporcionado.
     *
     * @param archivo Archivo de imagen a guardar.
     * @return Imagen guardada en la base de datos, o null si no se pudo guardar.
     * @throws Exception Si ocurre un error durante la operación.
     */
    @Transactional
    public Imagen guardar(MultipartFile archivo) throws Exception {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getOriginalFilename());
                InputStream inputStream = archivo.getInputStream();
                imagen.setContenido(IOUtils.toByteArray(inputStream));
                if (inputStream != null) {
                    inputStream.close();
                }
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    /**
     * Actualiza una imagen existente en la base de datos a partir de un archivo proporcionado.
     *
     * @param archivo Archivo de imagen para la actualización.
     * @param idImagen Identificador único de la imagen a actualizar.
     * @return Imagen actualizada en la base de datos, o null si no se pudo actualizar.
     * @throws Exception Si ocurre un error durante la operación.
     */
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws Exception {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
