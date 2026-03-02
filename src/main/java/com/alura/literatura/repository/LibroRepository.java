package com.alura.literatura.repository;

import com.alura.literatura.model.Idioma;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l WHERE l.titulo ILIKE %:tituloLibro%")
    List<Libro> busquedaLibro(String tituloLibro);

//    List<Libro> findAll();

//    @Query("SELECT l FROM Libro l WHERE l.idioma ILIKE %:idioma%")
//    List<Libro> librosPorIdioma(String idioma);
    List<Libro> findByIdioma(Idioma idioma);
}
