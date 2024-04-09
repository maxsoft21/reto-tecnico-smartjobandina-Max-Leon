# reto-tecnico-max-leon
# Descripción

Este proyecto es una aplicación Java basada en la arquitectura hexagonal, code first, programación funcional, desarrollada en Java 11 y utiliza diversas tecnologías para crear una solución robusta y eficiente. A continuación, se describen los aspectos clave del proyecto.

## Requisitos

Asegúrate de tener instalados los siguientes componentes antes de ejecutar el proyecto:

- Java 11
- Lombok
- Apache Maven 3.8.7
- Docker (opcional para ejecutar en contenedor)

## Detalles del Proyecto

- **Arquitectura Hexagonal**

- **Seguridad jwt Spring Security**

- **Programación Funcional**

- **Lombok para Mayor Claridad del Código**

- **Pruebas de aceptación con Junit/Mockito**

- **Base de Datos H2**

- **Documentación con Swagger**

  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **Docker para la Portabilidad**: El proyecto incluye un archivo Docker para ejecutar la aplicación en un contenedor Docker. Los comandos para construir y ejecutar la imagen del contenedor son los siguientes:

Nota: para el correcto funcionamiento se debe copiar el porperties en la ruta: /data/mcs/reto-tecnico-max-leon/reto-tecnico-max-leon-dev.yml

    ```bash
	docker build -t retotecnico:latest .
	docker run -d -p 8080:8080 retotecnico:latest
    ```
	
	Tambien se incluye un archivo docker-compose.yml:
	 ```bash
    docker-compose up -d --build
    ```

## Instalación

Sigue estos pasos para ejecutar la aplicación en tu entorno local:
1. tener instalado lombok
2. importar en postman la collection con nombre de archivo "Reto Tecnico smartjobandina.postman_collection.json"
3. importar el proyecto springboot maven en un ide (intelliJ fue el que se utilizó para su desarrollo)
4. ejecutar el servicio
5. probar desde postman localhost:8080/user/register

Nota: No Fue necesario script para bd, se está utilizadon dll hibernate para creación automatica de tablas

Diagrama solución:


![Image text](Diagrama%20solucion.jpg)