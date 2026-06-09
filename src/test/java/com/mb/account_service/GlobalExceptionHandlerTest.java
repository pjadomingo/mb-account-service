package com.mb.account_service;

import com.mb.account_service.GlobalExceptionHandler;
import com.mb.account_service.dto.AccountResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleValidationErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("objectName", "field", "Account type is required")));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<AccountResponse> responseEntity = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getTransactionStatusCode());
        assertEquals("Account type is required", responseEntity.getBody().getTransactionStatusDescription());
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Database connection failed");

        ResponseEntity<Map<String, Object>> responseEntity = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Map<String, Object> body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
        assertEquals("Runtime Exception", body.get("error"));
        assertEquals("Database connection failed", body.get("message"));
        assertTrue(body.containsKey("timestamp"));
    }

    @Test
    void testHandleGeneralException() {
        Exception ex = new Exception("Unexpected failure");

        ResponseEntity<Map<String, Object>> responseEntity = handler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Map<String, Object> body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
        assertEquals("Unexpected Error", body.get("error"));
        assertEquals("An unexpected error occurred", body.get("message"));
        assertTrue(body.containsKey("timestamp"));
    }
}
