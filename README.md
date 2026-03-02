# 📚 Literatura API - Proyecto Spring Boot

Aplicación de consola desarrollada con **Spring Boot**, **JPA** y
**Hibernate** que permite consultar libros desde la API pública de
**Gutendex** (Project Gutenberg), almacenarlos en base de datos y
realizar consultas sobre los libros registrados.

------------------------------------------------------------------------

## 🚀 Funcionalidades

La aplicación permite:

1.  🔎 Buscar libro por título (consulta a API externa).
2.  💾 Registrar libro y autor en base de datos si no existen.
3.  📖 Listar libros registrados.
4.  👤 Listar autores registrados.
5.  📅 Listar autores vivos en un determinado año.
6.  🌎 Listar libros por idioma.

------------------------------------------------------------------------

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una estructura basada en capas:

    com.alura.literatura
    │
    ├── model        → Entidades JPA (Libro, Autor, Idioma)
    ├── repository   → Repositorios JPA
    ├── service      → Consumo de API y conversión de datos
    ├── principal    → Lógica del menú de consola
    └── LiteraturaApplication.java

------------------------------------------------------------------------

## 🧠 Tecnologías Utilizadas

-   Java 17+
-   Spring Boot
-   Spring Data JPA
-   Hibernate
-   PostgreSQL (o base de datos configurada)
-   API Gutendex (https://gutendex.com/)

------------------------------------------------------------------------

## 🗄️ Modelo de Datos

### 📘 Libro

-   id (Long)
-   titulo (String)
-   idioma (Enum `Idioma`)
-   totalDescargas (Double)
-   autor (ManyToOne)

### 👤 Autor

-   id (Long)
-   nombre (String)
-   anioNacimiento (Integer)
-   anioMuerte (Integer)

### 🌎 Enum Idioma

-   INGLES ("en")
-   ESPANOL ("es")
-   FRANCES ("fr")
-   PORTUGUES ("pt")

Los idiomas se almacenan como `EnumType.STRING` en la base de datos.

------------------------------------------------------------------------

## 🔄 Flujo de Registro de Libro

1.  El usuario ingresa el título.
2.  Se consulta la API Gutendex.
3.  Se obtiene el primer resultado coincidente.
4.  Se verifica si el libro ya existe en la base de datos.
5.  Si no existe:
    -   Se valida si el autor ya está registrado.
    -   Si no existe, se crea.
    -   Se guarda el libro asociado al autor.

------------------------------------------------------------------------

## 🛠️ Configuración

Asegúrate de configurar correctamente el archivo:

    src/main/resources/application.properties

Ejemplo:

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literatura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

------------------------------------------------------------------------

## ▶️ Ejecución

1.  Clonar el repositorio.
2.  Configurar la base de datos.
3.  Ejecutar:

``` bash
mvn spring-boot:run
```

O ejecutar la clase:

    LiteraturaApplication.java

La aplicación se ejecuta en consola mostrando el menú interactivo.

------------------------------------------------------------------------

## 📌 Ejemplo de Uso

    1 - Buscar libro por título
    Ingrese el título del libro que desea buscar:
    Quijote

    Libro guardado exitosamente: Don Quijote

------------------------------------------------------------------------

## 🧩 Consideraciones Técnicas

-   Se utiliza `Enum` para garantizar integridad en los idiomas.
-   Se evita duplicar libros mediante validación previa.
-   Se reutilizan autores si ya existen en la base de datos.
-   Se eliminó `CascadeType.PERSIST` innecesario en la relación
    `ManyToOne` para evitar errores de entidades detached.

------------------------------------------------------------------------

## 📚 Fuente de Datos

API pública utilizada:

https://gutendex.com/


------------------------------------------------------------------------

## ✍️ Autor

Gilberto Marcial Jiménez

Proyecto desarrollado como parte del curso de formación en Java + Spring
Boot.
