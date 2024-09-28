package com.alura.challenge.literAlura.service;

public interface IConvierteDatosDeAPI {
    <T> T obtenerDatos(String json, Class<T> clase);
}
