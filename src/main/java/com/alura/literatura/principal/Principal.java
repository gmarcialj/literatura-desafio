package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_Base = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<DatosLibro> datosLibro = new ArrayList<>();
    private Optional<Autor> autorBuscado;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        List<Libro> libros = new ArrayList<>();
        List<Autor> autores = new ArrayList<>();
    }

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

    // Métodos que conectan a la BD
    public boolean buscarLibroEnBD(String titulo) {
        List<Libro> libroEncontrado = libroRepository.busquedaLibro(titulo);

        if (libroEncontrado.isEmpty()) {
            return false;
        }
        return true;
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
        Optional<DatosLibro> libroEncontrado = getDatosLibro();
        Optional<Libro> found = Optional.empty();

        if (libroEncontrado.isEmpty()) {
            System.out.println("No se encontró el libro, verifique que el nombre esté escrito correctamente");
        } else {
            if (!buscarLibroEnBD(libroEncontrado.get().titulo())) {
                DatosAutor datosAutor = libroEncontrado.get().autor().getFirst();
                Autor autor = autorRepository.findByNombreIgnoreCase(datosAutor.nombre())
                        .orElseGet(() -> {
                            // Si no existe se crea un nuevo
                            Autor nuevoAutor = new Autor(datosAutor);
                            return autorRepository.save(nuevoAutor);
                        });

                // Se almacena el libro
                Libro libro = new Libro(libroEncontrado.get(), autor);
                libroRepository.save(libro);
                System.out.println(libroEncontrado.get());
                System.out.println("Libro guardado exitosamente: " + libro.getTitulo());

            } else {
                System.out.println("No se puede registrar el mismo libro más de una vez");
            }
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(l ->
                        System.out.printf("""
                                ---------- Datos del Libro ----------
                                Título: %s
                                Autor: %s
                                Idioma: %s
                                Descargas totales: %s
                                -------------------------------------
                                """,
                                l.getTitulo(),
                                l.getAutor().getNombre(),
                                l.getIdioma(),
                                l.getTotalDescargas()
                        )
                );
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAllAutoresLibros();

        Set<String> nombres = new HashSet<>();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .filter(a -> nombres.add(a.getNombre()))
                .forEach(a -> {
                    String titulos = a.getLibros().stream()
                            .map(Libro::getTitulo)
                            .collect(Collectors.joining(", "));
                    System.out.printf("""
                            ---------- Datos del Autor ----------
                            Nombre: %s
                            Año de nacimiento: %s
                            Año de muerte: %s
                            Libros: [%s]
                            -------------------------------------
                            """,
                            a.getNombre(),
                            a.getAnioNacimiento(),
                            a.getAnioMuerte(),
                            titulos);
                });
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Escriba un año para listar los autores vivos en dicho año (YYYY)");
        var anio = teclado.nextInt();
        List<Autor> autores = autorRepository.autoresVivosPorAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores registrados vivos en el año " + anio);
        } else {
            Set<String> nombres = new HashSet<>();
            autores.stream()
                    .filter(a -> nombres.add(a.getNombre()))
                    .forEach(a -> {
                        String libros = a.getLibros().stream()
                                .map(Libro::getTitulo)
                                .collect(Collectors.joining(", "));
                        System.out.printf("""
                                ---------- Datos del Autor ----------
                                Nombre: %s
                                Año de nacimiento: %s
                                Año de muerte: %s
                                Libros: [%s]
                                -------------------------------------
                                """,
                                a.getNombre(),
                                a.getAnioNacimiento(),
                                a.getAnioMuerte(),
                                libros);
                    });
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Indique el idioma por el que desea buscar los libros");
        System.out.println("en - Inglés\nes - Español\nfr - Frances\npt - Portugués");
        var idiomaInput = teclado.nextLine().trim();

        Idioma idioma = null;

        try {
            idioma = Idioma.fromString(idiomaInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma inválido: " + idiomaInput);
        }

        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
        } else {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(l ->
                            System.out.printf("""
                                    ---------- Datos del Libro ----------
                                    Título: %s
                                    Autor: %s
                                    Idioma: %s
                                    Descargas totales: %s
                                    -------------------------------------
                                    """,
                                    l.getTitulo(),
                                    l.getAutor().getNombre(),
                                    l.getIdioma(),
                                    l.getTotalDescargas()));
        }
    }
}
