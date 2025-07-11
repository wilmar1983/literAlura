package com.Challenge.literAlura.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList; // Importar ArrayList
import java.util.List; // Importar List

@Entity
@Table(name = "autores")
@Getter
@Setter
@NoArgsConstructor // Para JPA/Hibernate

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;


    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>(); // ¡IMPORTANTE: Inicializar la lista aquí!

    // Constructor para cuando creamos un Autor desde la API

    public Autor(String nombre, Integer fechaNacimiento, Integer fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
        this.libros = new ArrayList<>(); // Inicializar también aquí
    }



    // Método para agregar un libro a la lista del autor
    // Es útil para mantener la coherencia bidireccional
    public void addLibro(Libro libro) {
        this.libros.add(libro);
        libro.setAutor(this);
    }

    @Override
    public String toString() {
        return "Autor: " + nombre +
                " (Nacimiento: " + (fechaNacimiento != null ? fechaNacimiento : "Desconocido") +
                ", Fallecimiento: " + (fechaFallecimiento != null ? fechaFallecimiento : "Vivo") + ")";
    }
}
