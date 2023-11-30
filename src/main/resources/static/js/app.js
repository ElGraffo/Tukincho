/**
 * 
 * @author Jonatan Atencio
 * @version 1.0
 * toma la estrella clicada y linta todas las anteriores hasta donde se clico
 */
const stars = document.querySelectorAll(".star");
const calificacion = document.getElementById('calificacion');
// Evento clic para cada estrella
stars.forEach(function (star, index) {
  star.addEventListener("click", function () {
    // obtener la calificación actual
    const rating = index + 1;

    // Almacenar la calificación en local storage
    localStorage.setItem("rating", rating);
    calificacion.value= localStorage.getItem('rating');

    // Actualizar la interfaz de usuario
    updateStarRating(rating);
  });
});

// Función para actualizar la interfaz de usuario
function updateStarRating(rating) {
  // Recorrer todas las estrellas y eliminar checked
  for (let i = 0; i < stars.length; i++) {
    stars[i].classList.remove("checked");
  }

  // Agregar checked a las estrellas
  for (let i = 0; i < rating; i++) {
    stars[i].classList.add("checked");
  }
}

// Obtener la calificación almacenada en local storage
const storedRating = localStorage.getItem("rating");

// Si hay una calificación almacenada, actualizar la interfaz de usuario
if (storedRating) {
  updateStarRating(storedRating);
  calificacion.value= storedRating;
}
