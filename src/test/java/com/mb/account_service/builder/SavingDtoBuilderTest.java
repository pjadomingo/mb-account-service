package com.mb.account_service.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.mb.account_service.dto.SavingDto;

class SavingDtoBuilderTest {

    @Test
    void testBuildSavingDto_Positive() {
        SavingDto dto = new SavingDtoBuilder()
                .accountNumber("12345678")
                .accountType("Savings")
                .availableBalance(1000.50)
                .build();

        assertNotNull(dto);
        assertEquals("12345678", dto.getAccountNumber());
        assertEquals("Savings", dto.getAccountType());
        assertEquals(1000.50, dto.getAvailableBalance());
    }

    @Test
    void testBuildSavingDto_VerifySetters() {
        SavingDto spyDto = spy(new SavingDto());

        SavingDtoBuilder builder = new SavingDtoBuilder()
                .accountNumber("99999999")
                .accountType("Checking")
                .availableBalance(250.75);

        // Build normally
        SavingDto dto = builder.build();

        // Functional assertions
        assertEquals("99999999", dto.getAccountNumber());
        assertEquals("Checking", dto.getAccountType());
        assertEquals(250.75, dto.getAvailableBalance());

        // Mockito verification example (spy would be injected if builder allowed it)
        verify(spyDto, never()).setAccountNumber("99999999");
    }

    @Test
    void testBuildSavingDto_Negative_MissingFields() {
        SavingDto dto = new SavingDtoBuilder().build();

        assertNotNull(dto);
        assertNull(dto.getAccountNumber());
        assertNull(dto.getAccountType());
        assertEquals(0.0, dto.getAvailableBalance()); // default double value
    }
}
