# Book store challenge
Este es un proyecto básico de una book store compuesto por una aplicación `Angular 15` en el frontend y una aplicación `Spring Boot 2.7.5` con `Java 17` en el backend, ambos contenerizados con `Docker`.

## Instalación
Después de clonar el proyecto abrir una terminal de comandos en la ruta del mismo y ejecutar el comando `docker-compose up -d --no-deps --build` para empezar a construir los contenedores y una vez que haya terminado el proceso, se podrá probar el frontend desde la dirección `https://localhost:8001` y el backend en la dirección `https://localhost:8000`.

**NOTA:** Ambas aplicaciones utilizan certificados **SSL autofirmados**, por lo que navegadores como **Firefox** pueden bloquear la comunicación al ser más restrictivo por defecto por temas de seguridad, por lo que se recomienda utilizar navegadores basados en Chromium como **Google Chrome** o **Microsoft Edge**.