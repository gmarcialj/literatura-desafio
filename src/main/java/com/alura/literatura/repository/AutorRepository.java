package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllAutoresLibros();

    @Query("SELECT DISTINCT a FROM Autor a WHERE a.anioNacimiento <= :year AND (a.anioMuerte IS NULL OR a.anioMuerte >= :year)")
    List<Autor> autoresVivosPorAnio(Integer year);

    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    Optional<Autor> findByNombreIgnoreCase(String nombre);
}
