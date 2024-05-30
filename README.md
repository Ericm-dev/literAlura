# LiterAlura 📚

Bienvenido a **LiterAlura**, un proyecto de Spring Boot diseñado para mostrar y probar tus habilidades adquiridas en los diferentes niveles de cursos. Este proyecto se conecta a una base de datos PostgreSQL y realiza diversas operaciones con datos de libros y autores obtenidos de una API externa.

## Tabla de Contenidos
- [Acerca del Proyecto](#acerca-del-proyecto)
- [Comenzando](#comenzando)
  - [Prerequisitos](#prerequisitos)
  - [Instalación](#instalación)
  - [Configuración](#configuración)
- [Uso](#uso)
- [Arquitectura](#arquitectura)
  - [Capa de Servicio](#capa-de-servicio)
  - [Capa de Modelo](#capa-de-modelo)
  - [Capa de Repositorio](#capa-de-repositorio)
- [Esquema de la Base de Datos](#esquema-de-la-base-de-datos)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Acerca del Proyecto

LiterAlura es una aplicación que se conecta a una API externa para obtener datos de libros, procesa estos datos y los almacena en una base de datos PostgreSQL. El proyecto demuestra el uso de diversas funcionalidades de Spring Boot, incluyendo persistencia de datos, servicios RESTful y runners de línea de comando.

## Comenzando

### Prerequisitos

Asegúrate de tener instalados en tu máquina de desarrollo local:
- Java 17
- Maven
- PostgreSQL

### Instalación

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/tu-usuario/literAlura.git
   cd literAlura

2. **Construye el proyecto:**
   ```bash
   mvn clean install

## Configuración
1. **Configuración de la Base de Datos:**
   Configura tu base de datos PostgreSQL con las credenciales necesarias. Actualiza el archivo application.properties con los detalles de tu base de datos:
   ```bash
   spring.datasource.url=jdbc:postgresql://{DB_HOST}/liter_alura
   spring.datasource.username={DB_USER}
   spring.datasource.password={DB_PASSWORD}
   spring.datasource.driver-class-name=org.postgresql.Driver
   hibernate.dialect=org.hibernate.dialect.HSQLDialect
   spring.jpa.hibernate.ddl-auto=update

3. **Ejecuta la aplicación:**
   ```bash
     mvn spring-boot:run

## Uso
Al ejecutar la aplicación, la interfaz de línea de comando presentará un menú para interactuar con los datos de los libros. Puedes obtener datos de la API externa, mostrar los 10 libros más descargados y realizar otras operaciones        definidas en la clase Principal.

## Arquitectura
## Capa de Servicio
- ConsumoAPI: Responsable de realizar solicitudes HTTP a la API externa y obtener los datos.
- ConvierteDatos: Maneja la conversión de datos JSON a objetos Java utilizando Jackson.
  
## Capa de Modelo
- Datos, DatosLibros, DatosAutor: Objetos de transferencia de datos (DTOs) que mapean la estructura de los datos JSON obtenidos de la API.
- Libros, Autores: Entidades JPA que representan los datos de libros y autores en la base de datos PostgreSQL.
  
## Capa de Repositorio
- LibrosRepository, AutoresRepository: Repositorios de Spring Data JPA para realizar operaciones CRUD en las entidades Libros y Autores.

## Esquema de la Base de Datos
La base de datos consta de dos tablas principales:
- libros: Contiene detalles sobre los libros, incluyendo título, idioma y número de descargas.
- autores: Contiene detalles sobre los autores, incluyendo nombre, año de nacimiento y año de fallecimiento. Cada autor está vinculado a un libro.
  
## Contribuciones
Las contribuciones son lo que hace que la comunidad de código abierto sea un lugar increíble para aprender, inspirarse y crear. Cualquier contribución que hagas será muy apreciada.

1. Haz un Fork del Proyecto.
2. Crea tu Rama de Característica (git checkout -b feature/AmazingFeature).
3. Realiza tu Commit con los Cambios (git commit -m 'Add some AmazingFeature').
4. Haz Push a la Rama (git push origin feature/AmazingFeature).
5. Abre una Pull Request.
   
## Licencia
Distribuido bajo la Licencia MIT. Consulta el archivo LICENSE para más información.
  
