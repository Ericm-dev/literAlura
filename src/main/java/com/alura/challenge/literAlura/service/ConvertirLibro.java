package com.alura.challenge.literAlura.service;

import com.alura.challenge.literAlura.dto.DatosLibrosDto;
import com.alura.challenge.literAlura.model.Autores;
import com.alura.challenge.literAlura.model.Libros;

import java.util.stream.Collectors;

public class ConvertirLibro {

    public static Libros convertToEntity(DatosLibrosDto datosLibros) {
        Libros libros = new Libros();
        Autores autores = new Autores();
        libros.setTitulo(datosLibros.titulo());
        libros.setAutores(datosLibros.autor().stream()
                .map(datosAutor -> {
                    Autores autor = new Autores();
                    autor.setNombre(datosAutor.nombre());
                    autor.setFechaNacimiento(datosAutor.fechaNacimiento());
                    autor.setFechaFallecimiento(datosAutor.fechaFallecimiento());
                    autor.setLibro(libros);
                    return autor;
                })
                .collect(Collectors.toList()));

        String idiomaConCorchetes = datosLibros.idiomas().toString();
        String idiomaSinCorchetes = idiomaConCorchetes.replace("[", "").replace("]", "");
        libros.setIdiomas(idiomaSinCorchetes);
        libros.setNumeroDeDescargas(datosLibros.numeroDeDescargas());
        return libros;
    }
}
