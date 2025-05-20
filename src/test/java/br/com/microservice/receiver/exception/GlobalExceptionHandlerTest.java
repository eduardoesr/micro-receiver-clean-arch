package br.com.microservice.receiver.exception;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleInvalidArgument_deveRetornarBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/teste");

        ResponseEntity<Object> response = handler.handleInvalidArgument(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals(400, body.get("status"));
        assertEquals("Argumento inválido", body.get("message"));
        assertEquals("/teste", body.get("path"));
    }

    @Test
    void handleValidationExceptions_deveRetornarUnprocessableEntityComErros() {
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/validar");

        FieldError fieldError = new FieldError("obj", "campo", "mensagem de erro");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Object> response = handler.handleValidationExceptions(ex, request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals(422, body.get("status"));
        assertEquals("Erro de validação", body.get("message"));
        assertEquals("/validar", body.get("path"));
        Map<?, ?> errors = (Map<?, ?>) body.get("errors");
        assertEquals("mensagem de erro", errors.get("campo"));
    }

    @Test
    void handleAllExceptions_deveRetornarInternalServerError() {
        Exception ex = new Exception("Falha inesperada");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/erro");

        ResponseEntity<Object> response = handler.handleAllExceptions(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals(500, body.get("status"));
        assertEquals("Ocorreu um erro inesperado: Falha inesperada", body.get("message"));
        assertEquals("/erro", body.get("path"));
    }
}