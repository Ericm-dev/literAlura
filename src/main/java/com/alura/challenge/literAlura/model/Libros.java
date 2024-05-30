package com.alura.challenge.literAlura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;
@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    //@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autores> autores;

    private String idiomas;

    private Double numeroDeDescargas;

    // Constructor vacío necesario para JPA
    public Libros() {}

    // Constructor que acepta un objeto DatosLibros como entrada
    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        // Crear y asignar autores al libro
        this.autores = datosLibros.autor().stream()
                .map(datosAutor -> {
                    Autores autor = new Autores(datosAutor);
                    autor.setLibro(this); // Asignar este libro al autor
                    return autor;
                })
                .collect(Collectors.toList());
        String idiomaConCorchetes = datosLibros.idiomas().toString();
        String idiomaSinCorchetes = idiomaConCorchetes.replace("[", "").replace("]", "");
        this.idiomas = idiomaSinCorchetes;
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }


    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autores> getAutores() {
        return autores;
    }

    public void setAutores(List<Autores> autores) {
        this.autores = autores;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return  "Libro " + " " +
                "Titulo: " + titulo + " " +
                "Autor: " + autores + " " +
                "Idioma: " + idiomas + " " +
                "Numero De Descargas: " + numeroDeDescargas;
    }
}

