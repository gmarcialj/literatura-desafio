package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_Base = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private List<DatosAutor> datosAutores = new ArrayList<>();
    private List<Libro> libros;
    private List<Autor> autores;
    private Optional<Libro> libroBuscado;

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    -----------------------
                    Elija la opción a través de su número:
                    1 - Buscar libro por título.
                    2 - Listar libros registrados.
                    3 - Listar autores registrados.
                    4 - Listar autores vivos en un determinado año.
                    5 - Listar libros por idioma.
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    // Métodos de DatosLibro y DatosAutor
    private Optional<DatosLibro> getDatosLibro() {
        System.out.println("Ingrese el título del libro que desea buscar: ");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_Base + "?search=" + tituloLibro.replace(" ", "+"));
        var resultados = convierteDatos.obtenerDatos(json, Results.class);

        Optional<DatosLibro> libroEncontrado = resultados.resultados().stream()
                .filter(l -> l.titulo().toLowerCase().contains(tituloLibro.toLowerCase()))
                .findFirst();

        return libroEncontrado;
    }

    // Métodos del menú
    private void buscarLibroPorTitulo() {
        Optional <DatosLibro> libroEncontrado = getDatosLibro();

        if (libroEncontrado.isEmpty()) {
            System.out.println("No se encontró el libro, verifique que el nombre esté escrito correctamente");
        } else {
            // System.out.println("Libro encontrado: ");
            // System.out.println(libroEncontrado.get());
            
        }
    }

    private void listarLibrosRegistrados() {

    }

    private void listarAutoresRegistrados() {

    }

    private void listarAutoresVivosPorAnio() {

    }

    private void listarLibrosPorIdioma() {}
}
