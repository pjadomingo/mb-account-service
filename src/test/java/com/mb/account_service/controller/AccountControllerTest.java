package com.mb.account_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mb.account_service.dto.AccountDto;
import com.mb.account_service.dto.AccountResponse;
import com.mb.account_service.service.AccountService;

class AccountControllerTest {

    private AccountService service;
    private AccountController controller;

    @BeforeEach
    void setUp() {
        service = mock(AccountService.class);
        controller = new AccountController(service);
    }

    @Test
    void testCreateAccount_Created() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("John Doe");
        dto.setCustomerMobile("09171234567");
        dto.setCustomerEmail("john@example.com");
        dto.setAddress1("123 Main St");
        dto.setAccountType("S");

        AccountResponse response = AccountResponse.builder()
                .transactionStatusCode(201)
                .transactionStatusDescription("Customer account created")
                .build();

        when(service.create(dto)).thenReturn(response);

        ResponseEntity<?> result = controller.createAccount(dto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(service, times(1)).create(dto);
    }

    @Test
    void testCreateAccount_BadRequest() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("Invalid");
        dto.setCustomerMobile("000");
        dto.setCustomerEmail("bademail");
        dto.setAddress1("Unknown");
        dto.setAccountType("X");

        AccountResponse response = AccountResponse.builder()
                .transactionStatusCode(400)
                .transactionStatusDescription("Validation failed")
                .build();

        when(service.create(dto)).thenReturn(response);

        ResponseEntity<?> result = controller.createAccount(dto);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(service, times(1)).create(dto);
    }

    @Test
    void testGetAccountByCustomerName_Found() {
        String customerName = "Jane Doe";
        AccountResponse response = AccountResponse.builder()
                .transactionStatusCode(302)
                .transactionStatusDescription("Customer account found")
                .build();

        when(service.getByCustomerNumber(customerName)).thenReturn(response);

        ResponseEntity<?> result = controller.getAccountByCustomerName(customerName);

        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(service, times(1)).getByCustomerNumber(customerName);
    }
    
    @Test
    void testGetAccountByCustomerName_NotFound() {
        String customerName = "Ghost";
        AccountResponse response = AccountResponse.builder()
                .transactionStatusCode(404)
                .transactionStatusDescription("Customer not found")
                .build();

        when(service.getByCustomerNumber(customerName)).thenReturn(response);

        ResponseEntity<?> result = controller.getAccountByCustomerName(customerName);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(service, times(1)).getByCustomerNumber(customerName);
    }

    @Test
    void testCreateAccount_Exception() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("ErrorUser");

        when(service.create(dto)).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<?> result = controller.createAccount(dto);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Bad Request", result.getBody());
    }
}
