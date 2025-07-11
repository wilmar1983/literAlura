# LiterAlura: Catálogo de Libros Interactivo

Este proyecto es un catálogo de libros interactivo desarrollado como parte de un desafío de lógica de programación y manejo de APIs en el programa de ONE (Oracle Next Education) y Alura. Permite a los usuarios buscar libros a través de la API de Gutendex, almacenar la información relevante en una base de datos PostgreSQL, y luego consultar y filtrar los libros y autores guardados.

## Características Principales

* **Búsqueda de Libros:** Permite buscar libros por título (o palabras clave) a través de la API de Gutendex.
* **Persistencia de Datos:** Guarda los libros y autores consultados en una base de datos PostgreSQL.
* **Listado de Libros:** Muestra todos los libros almacenados en la base de datos local.
* **Listado de Autores:** Muestra todos los autores almacenados en la base de datos local.
* **Búsqueda de Autores Vivos:** Permite consultar autores que estuvieron vivos en un año específico.
* **Búsqueda por Idioma:** Filtra y lista libros por su idioma.

## Tecnologías Utilizadas

* **Java 21+:** Lenguaje de programación principal.
* **Spring Boot 3.x:** Framework para facilitar el desarrollo de aplicaciones Java.
* **Spring Data JPA:** Para la interacción con la base de datos relacional.
* **Hibernate:** Implementación de JPA.
* **PostgreSQL:** Base de datos relacional para el almacenamiento de datos.
* **Maven:** Herramienta de gestión de dependencias y construcción de proyectos.
* **API Gutendex:** Fuente de datos para la búsqueda de libros (Project Gutenberg).
* **Jackson:** Librería para el procesamiento de JSON.
* **Lombok:** Para reducir el boilerplate code (getters, setters, constructores).

## Cómo Ejecutar el Proyecto

Sigue estos pasos para levantar y ejecutar la aplicación LiterAlura en tu entorno local:

### 1. Requisitos Previos

* **Java Development Kit (JDK) 17 o superior** instalado.
* **Maven** instalado.
* **PostgreSQL** instalado y corriendo.
* **pgAdmin** o cualquier otro cliente PostgreSQL (opcional, para gestionar la BD).

### 2. Configuración de la Base de Datos PostgreSQL

1.  **Crea una nueva base de datos** en tu servidor PostgreSQL. El nombre de la base de datos debe ser `literAlura_db`.
    ```sql
    CREATE DATABASE "literAlura_db";
    ```
2.  **Configura las credenciales de la base de datos** en el archivo `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literAlura_db
    spring.datasource.username=${DB_USER:tu_usuario_postgres_aqui}
    spring.datasource.password=${DB_PASSWORD:tu_contrasena_postgres_aqui}
    spring.datasource.driver-class-name=org.postgresql.Driver

    spring.jpa.hibernate.ddl-auto=update # Hibernate creará las tablas automáticamente
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.properties.hibernate.format_sql=true
    ```
    **Importante:** Reemplaza `tu_usuario_postgres_aqui` y `tu_contrasena_postgres_aqui` con tus credenciales reales de PostgreSQL.

### 3. Compilación y Ejecución

1.  **Clona este repositorio** (o descarga el código) a tu máquina local.
2.  **Navega hasta el directorio raíz del proyecto** (`literAlura`) en tu terminal.
3.  **Compila el proyecto usando Maven:**
    ```bash
    mvn clean install
    ```
4.  **Ejecuta la aplicación Spring Boot:**
    ```bash
    mvn spring-boot:run
    ```
    Alternativamente, puedes abrir el proyecto en tu IDE (IntelliJ IDEA, Eclipse, VS Code con plugins Java) y ejecutar la clase `LiterAluraApplication.java` directamente.

### 4. Interacción con la Aplicación

Una vez que la aplicación se inicie, verás un menú en la consola. Sigue las instrucciones para interactuar con el catálogo de libros:

## LiterAlura - Catálogo de Libros
#### 1 - Buscar libro por título 
#### 2 - Listar libros registrados 
#### 3 - Listar autores registrados 
#### 4 - Listar autores vivos en un determinado año 
#### 5 - Listar libros por idioma 
#### 0 - Salir
#### Elija una opción:

**Notas sobre la búsqueda de libros:**
* La API de Gutendex puede ser sensible a los términos de búsqueda. Para encontrar libros específicos como "Cien años de soledad" o "El viejo y el mar", intente usar palabras clave más generales (ej. "soledad", "mar") o sus títulos en inglés ("One Hundred Years of Solitude", "The Old Man and the Sea").
* Algunos libros pueden no estar disponibles en la API de Gutendex debido a su enfoque en obras de dominio público.

## Contribuciones

¡Las contribuciones son bienvenidas! Si encuentras un error o tienes una mejora, no dudes en abrir un *issue* o enviar un *pull request*.

##  Autor
Wilmar Rivera Gómez
- Proyecto creado como parte de un desafío práctico de programación dentro del aula de Java y Spring Framework G8 - ONE.

## Licencia
- Este proyecto está bajo la Licencia MIT.
- Puedes usarlo, modificarlo y distribuirlo libremente con fines educativos o personales.
- Consulta el archivo [LICENSE](LICENSE) para más detalles

## Licencia

Este proyecto está bajo la Licencia MIT.

