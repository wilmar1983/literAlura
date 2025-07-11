package com.Challenge.literAlura.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "libros") // Mapea esta entidad a la tabla 'libros' en la BD
@Getter // Anotación de Lombok para generar getters
@Setter // Anotación de Lombok para generar setters
@NoArgsConstructor // Anotación de Lombok para generar constructor sin argumentos
@AllArgsConstructor // Anotación de Lombok para generar constructor con todos los argumentos
public class Libro {
    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID por la BD
    private Long id;

    @Column(nullable = false) // Indica que la columna no puede ser nula
    private String titulo;

    @ManyToOne // Relación ManyToOne con Autor (muchos libros pueden tener un autor)
    @JoinColumn(name = "id_autor", nullable = false) // Columna en la tabla 'libros' que es la FK
    private Autor autor;

    @Column(nullable = false)
    private String idioma;

    private Integer numeroDescargas; // Usamos Integer para permitir nulos

    // Constructor para cuando creamos un Libro desde la API
    public Libro(String titulo, Autor autor, String idioma, Integer numeroDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "----- LIBRO -----" +
                "\nTitulo: " + titulo +
                "\nAutor: " + autor.getNombre() +
                "\nIdioma: " + idioma +
                "\nNumero de descargas: " + (numeroDescargas != null ? numeroDescargas : "N/A") +
                "\n-----------------\n";
    }

}
