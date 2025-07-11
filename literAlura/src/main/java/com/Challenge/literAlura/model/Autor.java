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
// @AllArgsConstructor // <--- COMENTA O ELIMINA ESTA LÍNEA si vas a tener constructores manuales con lógica de inicialización
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    // Asegúrate de que esta lista sea inicializada
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>(); // ¡IMPORTANTE: Inicializar la lista aquí!

    // Constructor para cuando creamos un Autor desde la API
    // Asegúrate de que este constructor también inicialice la lista si lo usas
    public Autor(String nombre, Integer fechaNacimiento, Integer fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
        this.libros = new ArrayList<>(); // Inicializar también aquí
    }

    // Si estás usando Lombok @AllArgsConstructor, y necesitas el constructor sin la lista de libros
    // entonces debes tener cuidado, o crear un constructor que incluya la inicialización.
    // La mejor práctica es que el constructor que uses para crear nuevos objetos de Autor
    // inicialice la lista.

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
