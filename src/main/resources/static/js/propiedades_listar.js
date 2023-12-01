


document.addEventListener("DOMContentLoaded", function() {
    var botones = document.querySelectorAll('.boton');
  
    botones.forEach(function(boton) {
      boton.addEventListener('mouseover', function() {
        contenedor = document.getElementById('contenedor');
        contenedor.classList.add('containerBlur');
        
      });
    });
  });
  


  document.addEventListener("DOMContentLoaded", function() {
    var botones = document.querySelectorAll('.boton');
  
    botones.forEach(function(boton) {
      boton.addEventListener('mouseleave', function() {
        contenedor = document.getElementById('contenedor');
        contenedor.classList.remove('containerBlur');
        
      });
    });
  });
//    document.getElementsByClassName('boton').addEventListener('mouseenter', function() {
//        contenedor = document.getElementById('contenedor');
//        contenedor.classList.add('containerBlur');

//        card=document.getElementById('card1');
//        card.classList.add('cardAumentada');
       
    
//    });





//  document.getElementById('botonId').addEventListener('mouseleave', function() {
//      //document.body.classList.remove('fondo');
//      contenedor = document.getElementById('contenedor');
//      contenedor.classList.remove('containerBlur');


//      card=document.getElementById('card1');
//      card.classList.remove('cardAumentada');
    
//  });