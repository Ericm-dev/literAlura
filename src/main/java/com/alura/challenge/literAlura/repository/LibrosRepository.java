package com.alura.challenge.literAlura.repository;

import com.alura.challenge.literAlura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//
public interface LibrosRepository extends JpaRepository<Libros, Long> {
    @Query("SELECT l FROM Libros l WHERE UPPER(l.idiomas) LIKE CONCAT('%', UPPER(:idioma), '%')")
    List<Libros> findByIdiomasContainingIgnoreCase(String idioma);

    boolean existsByTitulo(String titulo);

    @Query("SELECT l FROM Libros l ORDER BY l.numeroDeDescargas DESC")
    List<Libros> findTop10ByOrderByNumeroDeDescargasDesc();
}
