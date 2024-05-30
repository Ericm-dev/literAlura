package com.alura.challenge.literAlura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
