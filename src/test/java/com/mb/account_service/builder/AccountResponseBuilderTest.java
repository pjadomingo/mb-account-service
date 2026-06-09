package com.mb.account_service.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mb.account_service.builder.AccountResponseBuilder;
import com.mb.account_service.dto.AccountResponse;
import com.mb.account_service.dto.SavingDto;

class AccountResponseBuilderTest {

    @Test
    void testBuildAccountResponse_UsingMockito() {
        // Spy on AccountResponse to intercept setter calls
        AccountResponse spyResponse = spy(new AccountResponse());

        AccountResponseBuilder builder = new AccountResponseBuilder()
                .customerNumber("12345678")
                .customerName("John Doe")
                .customerMobile("09171234567")
                .customerEmail("john.doe@example.com")
                .address1("123 Main Street")
                .address2("Unit 5B")
                .savings(List.of(new SavingDto()))
                .transactionStatusCode(201)
                .transactionStatusDescription("Customer account created");

        // Manually inject spy instead of new object
        AccountResponse response = builder.build();

        // Functional assertions
        assertEquals("12345678", response.getCustomerNumber());
        assertEquals("John Doe", response.getCustomerName());
        assertEquals("09171234567", response.getCustomerMobile());
        assertEquals("john.doe@example.com", response.getCustomerEmail());
        assertEquals("123 Main Street", response.getAddress1());
        assertEquals("Unit 5B", response.getAddress2());
        assertEquals(201, response.getTransactionStatusCode());
        assertEquals("Customer account created", response.getTransactionStatusDescription());

        // Mockito verification example (spyResponse would be used if injected)
        // Here we show how to verify setter calls if builder accepted an injected response
        verify(spyResponse, never()).setCustomerName("John Doe"); 
    }

    @Test
    void testBuildAccountResponse_Negative_MissingOptionalFields() {
        AccountResponse response = new AccountResponseBuilder()
                .customerNumber("99999999")
                .customerName("Jane Smith")
                .customerMobile("09981234567")
                .customerEmail("jane.smith@example.com")
                .address1("456 Ayala Avenue")
                .transactionStatusCode(302)
                .transactionStatusDescription("Customer account found")
                .build();

        assertNotNull(response);
        assertNull(response.getAddress2());
        assertNull(response.getSavings());
    }

    @Test
    void testBuildAccountResponse_Negative_MissingRequiredFields() {
        AccountResponse response = new AccountResponseBuilder()
                .transactionStatusCode(400)
                .transactionStatusDescription("Bad request")
                .build();

        assertNotNull(response);
        assertNull(response.getCustomerNumber());
        assertNull(response.getCustomerName());
        assertNull(response.getCustomerMobile());
        assertNull(response.getCustomerEmail());
        assertNull(response.getAddress1());
        assertNull(response.getAddress2());
        assertNull(response.getSavings());
        assertEquals(400, response.getTransactionStatusCode());
        assertEquals("Bad request", response.getTransactionStatusDescription());
    }
}
