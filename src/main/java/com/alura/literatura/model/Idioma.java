package com.alura.literatura.model;

public enum Idioma {
    INGLES("en", "inglés"),
    ESPANOL("es", "español"),
    FRANCES("fr", "francés");

    private String idiomaGut;
    private String idiomaEsp;

    Idioma (String idiomaGut, String idiomaEsp) {
        this.idiomaGut = idiomaGut;
        this.idiomaEsp = idiomaEsp;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaGut.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
    }

    public static Idioma fromEspanol(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaEsp.equalsIgnoreCase(text)) {
                return  idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
    }
}
