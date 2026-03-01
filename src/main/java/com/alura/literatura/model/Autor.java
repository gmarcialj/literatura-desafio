package com.alura.literatura.model;

public class Autor {
    private String nombre;
    private String anioNacimiento;
    private String anioMuerte;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(String anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public String getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(String anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    @Override
    public String toString() {
        return "nombre=" + nombre +
                ", anioNacimiento='" + anioNacimiento + '\'' +
                ", anioMuerte='" + anioMuerte + '\'' ;
    }
}
