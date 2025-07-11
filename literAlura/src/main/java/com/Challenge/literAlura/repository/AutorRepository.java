package com.Challenge.literAlura.repository;


import com.Challenge.literAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Anotación opcional, pero buena práctica para indicar que es un componente de Spring
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // JpaRepository<TipoDeEntidad, TipoDeIdDeLaEntidad>

    // Método para buscar un autor por nombre (ignoring case para flexibilidad)
    // Spring Data JPA puede generar esta consulta automáticamente por el nombre del método
    Optional<Autor> findByNombreContainingIgnoreCase(String nombre);

    // Método para encontrar autores que estuvieron vivos en un año específico
    // Usamos @Query para una consulta JPQL más compleja
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :anio)")
    List<Autor> findAutoresVivosEnAnio(Integer anio);
}
