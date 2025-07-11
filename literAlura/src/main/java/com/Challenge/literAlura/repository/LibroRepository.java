package com.Challenge.literAlura.repository;


import com.Challenge.literAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Buscar un libro por título (ignoring case para flexibilidad)
    Optional<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Buscar libros por idioma
    List<Libro> findByIdioma(String idioma);

    // Buscar libros por el nombre del autor
    // Esto es posible porque el campo 'autor' es una relación en la entidad Libro
    List<Libro> findByAutorNombreContainingIgnoreCase(String nombreAutor);

    // Puedes añadir más métodos según las necesidades de tus filtros, por ejemplo:
    // List<Libro> findTop10ByOrderByNumeroDescargasDesc();
}
