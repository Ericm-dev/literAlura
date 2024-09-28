package com.alura.challenge.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosApiDto(
        @JsonAlias("results") List<DatosLibrosDto> resultados
) {
}
