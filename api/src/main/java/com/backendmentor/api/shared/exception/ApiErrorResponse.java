package com.backendmentor.api.shared.exception;

import java.time.Instant;

/**
 * DTO que define la estructura JSON devuelta al cliente
 * cada vez que ocurre un error en la API
 *
 */

public record ApiErrorResponse(
        // Codigo de estado
        int status,

        // Nombre del error HTTP
        String error,

        // Mensaje descriptivo con el motivo del fallo
        String message,

        // Ruta de la petición que genero el error
        String path,

        // Marca de tiempo con el momento en que ocurrió
        Instant timestamp
) {
}
