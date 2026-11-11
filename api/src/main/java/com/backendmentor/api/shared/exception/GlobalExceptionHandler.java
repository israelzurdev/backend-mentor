package com.backendmentor.api.shared.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Interceptor global de excepciones para la API REST.
 * Garantiza respuestas de error con estructura uniforme (RFC 7807 compatible).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. Recurso de negocio no encontrado (HTTP 404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Recurso no encontrado: {} en {}", ex.getMessage(), request.getRequestURI());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    // 2. Ruta o archivo estático no existente en Spring Boot 3.2+ (HTTP 404)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFound(
            NoResourceFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Ruta inexistente solicitada: {}", request.getRequestURI());
        String mensaje = String.format("El endpoint '%s' no existe en la API.", request.getRequestURI());
        return buildResponse(HttpStatus.NOT_FOUND, mensaje, request.getRequestURI());
    }

    // 3. Tipos de datos inválidos en parámetros URL/Query (HTTP 400)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String mensaje = String.format("El parámetro '%s' recibió un valor no válido: '%s'",
                ex.getName(), ex.getValue());
        log.warn("Parámetro inválido: {}", mensaje);
        return buildResponse(HttpStatus.BAD_REQUEST, mensaje, request.getRequestURI());
    }

    // 4. Validaciones de DTO fallidas con Jakarta Bean Validation (@Valid) (HTTP 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Error de validación de entrada en {}: {}", request.getRequestURI(), errores);
        return buildResponse(HttpStatus.BAD_REQUEST, errores, request.getRequestURI());
    }

    // 5. Método HTTP no soportado (ej: POST a un endpoint que solo admite GET) (HTTP 405)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {
        String mensaje = String.format("El método HTTP '%s' no está soportado para este endpoint.", ex.getMethod());
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, mensaje, request.getRequestURI());
    }

    // 6. Red de seguridad contra fallos inesperados (HTTP 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        // En producción el log retiene la traza completa, pero el cliente recibe un mensaje seguro
        log.error("Error no controlado procesando {}", request.getRequestURI(), ex);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ha ocurrido un error inesperado en el servidor. Contacte con soporte.",
                request.getRequestURI()
        );
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message, String uri) {
        ApiErrorResponse error = new ApiErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                uri,
                Instant.now()
        );
        return ResponseEntity.status(status).body(error);
    }
}
