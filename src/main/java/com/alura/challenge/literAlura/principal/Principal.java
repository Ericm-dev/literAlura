package com.alura.challenge.literAlura.principal;

import com.alura.challenge.literAlura.dto.DatosApiDto;
import com.alura.challenge.literAlura.dto.DatosAutorDto;
import com.alura.challenge.literAlura.dto.DatosLibrosDto;
import com.alura.challenge.literAlura.model.*;
import com.alura.challenge.literAlura.repository.AutoresRepository;
import com.alura.challenge.literAlura.repository.LibrosRepository;
import com.alura.challenge.literAlura.service.ConsumoDeAPI;
import com.alura.challenge.literAlura.service.ConvertirLibro;
import com.alura.challenge.literAlura.service.ConvierteDatosDeAPI;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoDeAPI consumoAPI = new ConsumoDeAPI();
    private ConvierteDatosDeAPI conversor = new ConvierteDatosDeAPI();
    private Scanner teclado = new Scanner(System.in);
    private int opcion;
    private LibrosRepository librosRepositorio;
    private AutoresRepository autoresRepositorio;
    private List<Libros> libros;
    private List<Autores> autores;
    private String menu = """
            **-------------------------------------------------**
            **-------------------------------------------------**
            **  Elija la opción através de su número:          **
            **  1- Buscar líbro por título                     *+
            **  2- Listar líbros registrados                   **
            **  3- Listar autores registrados                  **
            **  4- Listar autores vivos en un determinado año  **
            **  5- Listar libros por idioma                    **
            **  6- Top 10 de libros                            **
            **  0- Salir                                       **
            **-------------------------------------------------**
            **-------------------------------------------------**
            """;

    public Principal(LibrosRepository librosRepository, AutoresRepository autoresRepository) {
        this.librosRepositorio = librosRepository;
        this.autoresRepositorio = autoresRepository;
    }

    public void muestraElMenu(){

        do {
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarYGuardarEnBBDD();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosPorDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    topLibros();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida, vuelva a intentar");
            }
        } while (opcion != 0);
    }

    private Optional<DatosLibrosDto> buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosApiDto.class);

        Optional<DatosLibrosDto> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            DatosLibrosDto libroEncontrado = libroBuscado.get();
            String autor = libroEncontrado.autor().stream()
                    .map(DatosAutorDto::nombre)
                    .findFirst()
                    .orElse("Autor desconocido"); // Si no hay autor, muestra "Autor desconocido"

            String idioma = libroEncontrado.idiomas().isEmpty() ? "Desconocido" : libroEncontrado.idiomas().get(0);
            System.out.println();
            System.out.println("-----Libro-----");
            System.out.println("Título: " + libroEncontrado.titulo());
            System.out.println("Autor: " + autor);
            System.out.println("Idioma: " + idioma);
            System.out.println("Número de descargas: " + libroEncontrado.numeroDeDescargas());
            System.out.println();
        }else {
            System.out.println();
            System.out.println("Libro no encontrado");
        }
        return libroBuscado;
    }

    private void buscarYGuardarEnBBDD() {
        Optional<DatosLibrosDto> datos = buscarLibroPorTitulo();
        if (datos.isPresent()) {
            DatosLibrosDto datosLibros = datos.get();
            boolean existe = librosRepositorio.existsByTitulo(datosLibros.titulo());
            if (existe) {
                System.out.println("El libro ya existe en la base de datos: " + datosLibros.titulo());
                System.out.println();
            } else {
                Libros libro = ConvertirLibro.convertToEntity(datosLibros);
                librosRepositorio.save(libro);
                System.out.println("Libro guardado en la base de datos.");
                System.out.println();
            }
        } else {
            System.out.println("Libro no encontrado, no se guardará en la base de datos.");
            System.out.println();
        }
    }

    private void listarLibros() {
        List<Libros> libros = librosRepositorio.findAll();

        System.out.println("Libros Registrados en la BBDD");
        System.out.println();
        libros.forEach(libro -> {
            System.out.println("-----Libro-----");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + obtenerNombresAutores(libro));
            System.out.println("Idioma: " + libro.getIdiomas());
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
            System.out.println();
        });
    }

    private String obtenerNombresAutores(Libros libro) {
        return libro.getAutores().stream()
                .map(Autores::getNombre)
                .collect(Collectors.joining(", "));
    }

    private void listarAutores() {
        List<Autores> autores = autoresRepositorio.findAll();

        System.out.println("Autores registrados en la BBDD");
        System.out.println();
        for (Autores autor : autores) {
            System.out.println("-----Autor-----");
            System.out.println("Nombre del autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getFechaFallecimiento());

            // Mostrar los libros escritos por este autor
            if (autor.getLibro() != null) {
                System.out.println("Libro: " + autor.getLibro().getTitulo());
            } else {
                System.out.println("No se encontraron libros escritos por " + autor.getNombre());
            }
            System.out.println();
        }
    }

    private void listarAutoresVivosPorDeterminadoAnio() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar:");
        int anio = teclado.nextInt();

        List<Autores> autoresVivos = autoresRepositorio.findAutoresVivosPorAnio(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Autores vivos en el año " + anio + ":");
            System.out.println();
            for (Autores autor : autoresVivos) {
                System.out.println("Nombre del autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFechaFallecimiento());

                if (autor.getLibro() != null) {
                    System.out.println("Libros escritos por " + autor.getNombre() + ": " + autor.getLibro().getTitulo());
                } else {
                    System.out.println("No se encontraron libros escritos por " + autor.getNombre());
                }

                System.out.println();
            }
        }
    }

    private void listarLibrosPorIdioma() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el idioma para buscar los libros: ");
        System.out.println("""
                es - español
                en - ingles
                fr - frances
                pt - portugues""");
        String idioma = teclado.nextLine();

        List<Libros> librosPorIdioma = librosRepositorio.findByIdiomasContainingIgnoreCase(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma \"" + idioma + "\"");
            System.out.println();
        } else {
            System.out.println("Libros en el idioma \"" + idioma + "\":");
            System.out.println();
            for (Libros libro : librosPorIdioma) {
                System.out.println("-----Libro-----");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + obtenerNombresAutores(libro));
                System.out.println("Idioma: " + libro.getIdiomas());
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println();
            }
        }
    }

    public void topLibros() {
        List<Libros> top10Libros = librosRepositorio.findTop10ByOrderByNumeroDeDescargasDesc();

        if (top10Libros.isEmpty()) {
            System.out.println("No se encontraron libros registrados en la base de datos.");
        } else {
            System.out.println("Top 10 libros más descargados:");
            System.out.println();
            for (int i = 0; i < top10Libros.size(); i++) {
                Libros libroEncontrado = top10Libros.get(i);
                String autor = obtenerNombresAutores(libroEncontrado);
                String idioma = libroEncontrado.getIdiomas();
                System.out.println("-----Libro " + (i + 1) + "-----");
                System.out.println("Título: " + libroEncontrado.getTitulo());
                System.out.println("Autor: " + autor);
                System.out.println("Idioma: " + idioma);
                System.out.println("Número de descargas: " + libroEncontrado.getNumeroDeDescargas());
                System.out.println();
            }
        }
    }
}
