package com.alura.challenge.literAlura.repository;

import com.alura.challenge.literAlura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutoresRepository extends JpaRepository<Autores, Long> {

    // Listar autores vivos en un año específico
    @Query("SELECT a FROM Autores a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :anio)")
    List<Autores> findAutoresVivosPorAnio(@Param("anio") int anio);

}
