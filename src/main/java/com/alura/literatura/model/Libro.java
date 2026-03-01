package com.alura.literatura.model;

import java.util.List;

public class Libro {
    private String titulo;
    private Idioma idioma;
    private Double totalDescargas;

    private List<Autor> autores;

    // Constructores
    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.fromString(datosLibro.idioma().getFirst());
        this.totalDescargas = datosLibro.totalDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Double getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(Double totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "titulo=" + titulo +
                ", idioma='" + idioma + '\'' +
                ", totalDescargas='" + totalDescargas + '\'' ;
    }
}
