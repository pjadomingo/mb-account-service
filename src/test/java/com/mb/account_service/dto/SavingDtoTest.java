package com.mb.account_service.dto;

import org.junit.jupiter.api.Test;

import com.mb.account_service.dto.SavingDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SavingDtoTest {

    @Test
    void testSettersAndGetters() {
        SavingDto dto = new SavingDto();
        dto.setAccountNumber("12345678");
        dto.setAccountType("Savings");
        dto.setAvailableBalance(500.75);

        assertEquals("12345678", dto.getAccountNumber());
        assertEquals("Savings", dto.getAccountType());
        assertEquals(500.75, dto.getAvailableBalance());
    }

    @Test
    void testBuilder() {
        SavingDto dto = SavingDto.builder()
                .accountNumber("99999999")
                .accountType("Checking")
                .availableBalance(250.25)
                .build();

        assertNotNull(dto);
        assertEquals("99999999", dto.getAccountNumber());
        assertEquals("Checking", dto.getAccountType());
        assertEquals(250.25, dto.getAvailableBalance());
    }

    @Test
    void testMissingFields() {
        SavingDto dto = new SavingDto();

        assertNull(dto.getAccountNumber());
        assertNull(dto.getAccountType());
        assertEquals(0.0, dto.getAvailableBalance()); // default double value
    }

    @Test
    void testSpyOnSavingDto() {
        SavingDto spyDto = spy(new SavingDto());

        spyDto.setAccountNumber("55555555");
        spyDto.setAccountType("Savings");
        spyDto.setAvailableBalance(100.0);

        verify(spyDto).setAccountNumber("55555555");
        verify(spyDto).setAccountType("Savings");
        verify(spyDto).setAvailableBalance(100.0);

        assertEquals("55555555", spyDto.getAccountNumber());
        assertEquals("Savings", spyDto.getAccountType());
        assertEquals(100.0, spyDto.getAvailableBalance());
    }
}
