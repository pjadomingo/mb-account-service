package com.mb.account_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mb.account_service.config.BankConfig;
import com.mb.account_service.service.AccountNumberService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

class AccountNumberServiceTest {

    private EntityManager entityManager;
    private BankConfig bankConfig;
    private AccountNumberService service;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        bankConfig = mock(BankConfig.class);
        service = new AccountNumberService(bankConfig);

        try {
            var field = AccountNumberService.class.getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(service, entityManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetNextSequenceValue() {
        Query mockQuery = mock(Query.class);
        when(entityManager.createNativeQuery("SELECT NEXT VALUE FOR account_seq")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(42L);

        Long result = service.getNextSequenceValue();

        assertEquals(42L, result);
        verify(entityManager).createNativeQuery("SELECT NEXT VALUE FOR account_seq");
        verify(mockQuery).getSingleResult();
    }

    @Test
    void testGenerateAccountNumber() {
        Query mockQuery = mock(Query.class);
        when(entityManager.createNativeQuery("SELECT NEXT VALUE FOR account_seq")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(1L);

        when(bankConfig.getBranchCode()).thenReturn("01");
        when(bankConfig.getYear()).thenReturn("2026");

        String accountNumber = service.generateAccountNumber();

        assertNotNull(accountNumber);
        assertTrue(accountNumber.startsWith("012026")); // branch + year
        assertEquals(17, accountNumber.length()); // 2 + 4 + 10 + 1 check digit
    }

    @Test
    void testGetNextSequenceValue_InvalidResult() {
        Query mockQuery = mock(Query.class);
        when(entityManager.createNativeQuery("SELECT NEXT VALUE FOR account_seq")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn("not-a-number");

        assertThrows(ClassCastException.class, () -> service.getNextSequenceValue());
    }

    @Test
    void testGenerateAccountNumber_VerifyBankConfigCalls() {
        Query mockQuery = mock(Query.class);
        when(entityManager.createNativeQuery("SELECT NEXT VALUE FOR account_seq")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(5L);

        BankConfig spyConfig = spy(new BankConfig());
        spyConfig.setBranchCode("99");
        spyConfig.setYear("2030");

        AccountNumberService spyService = new AccountNumberService(spyConfig);
        try {
            var field = AccountNumberService.class.getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(spyService, entityManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        spyService.generateAccountNumber();

        verify(spyConfig).getBranchCode();
        verify(spyConfig).getYear();
    }
}
