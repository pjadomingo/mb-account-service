package com.mb.account_service.dto;

import org.junit.jupiter.api.Test;

import com.mb.account_service.builder.AccountResponseBuilder;
import com.mb.account_service.dto.AccountResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountResponseTest {

    @Test
    void testSettersAndGetters() {
        AccountResponse response = new AccountResponse();
        response.setCustomerNumber("12345678");
        response.setCustomerName("John Doe");
        response.setCustomerMobile("09171234567");
        response.setCustomerEmail("john.doe@example.com");
        response.setAddress1("123 Main Street");
        response.setAddress2("Unit 5B");
        response.setTransactionStatusCode(201);
        response.setTransactionStatusDescription("Customer account created");

        assertEquals("12345678", response.getCustomerNumber());
        assertEquals("John Doe", response.getCustomerName());
        assertEquals("09171234567", response.getCustomerMobile());
        assertEquals("john.doe@example.com", response.getCustomerEmail());
        assertEquals("123 Main Street", response.getAddress1());
        assertEquals("Unit 5B", response.getAddress2());
        assertEquals(201, response.getTransactionStatusCode());
        assertEquals("Customer account created", response.getTransactionStatusDescription());
    }

    @Test
    void testBuilder() {
        AccountResponse response = AccountResponse.builder()
                .customerNumber("99999999")
                .customerName("Jane Smith")
                .customerMobile("09981234567")
                .customerEmail("jane.smith@example.com")
                .address1("456 Ayala Avenue")
                .address2("Floor 10")
                .transactionStatusCode(302)
                .transactionStatusDescription("Customer account found")
                .build();

        assertNotNull(response);
        assertEquals("99999999", response.getCustomerNumber());
        assertEquals("Jane Smith", response.getCustomerName());
        assertEquals("09981234567", response.getCustomerMobile());
        assertEquals("jane.smith@example.com", response.getCustomerEmail());
        assertEquals("456 Ayala Avenue", response.getAddress1());
        assertEquals("Floor 10", response.getAddress2());
        assertEquals(302, response.getTransactionStatusCode());
        assertEquals("Customer account found", response.getTransactionStatusDescription());
    }

    @Test
    void testMissingOptionalFields() {
        AccountResponse response = AccountResponse.builder()
                .customerNumber("11111111")
                .customerName("Ghost User")
                .transactionStatusCode(401)
                .transactionStatusDescription("Customer not found")
                .build();

        assertNotNull(response);
        assertEquals("11111111", response.getCustomerNumber());
        assertEquals("Ghost User", response.getCustomerName());
        assertNull(response.getCustomerMobile());
        assertNull(response.getCustomerEmail());
        assertNull(response.getAddress1());
        assertNull(response.getAddress2());
        assertNull(response.getSavings());
        assertEquals(401, response.getTransactionStatusCode());
        assertEquals("Customer not found", response.getTransactionStatusDescription());
    }

    @Test
    void testSpyOnAccountResponse() {
        AccountResponse spyResponse = spy(new AccountResponse());

        spyResponse.setCustomerName("Spy User");
        spyResponse.setTransactionStatusCode(201);

        verify(spyResponse).setCustomerName("Spy User");
        verify(spyResponse).setTransactionStatusCode(201);

        assertEquals("Spy User", spyResponse.getCustomerName());
        assertEquals(201, spyResponse.getTransactionStatusCode());
    }
}
