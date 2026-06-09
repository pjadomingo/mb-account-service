package com.mb.account_service.service;

import com.mb.account_service.dto.AccountDto;
import com.mb.account_service.dto.AccountResponse;
import com.mb.account_service.enums.AccountTypeEnum;
import com.mb.account_service.mapper.AccountMapper;
import com.mb.account_service.model.Account;
import com.mb.account_service.repository.AccountRepository;
import com.mb.account_service.service.AccountNumberService;
import com.mb.account_service.service.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository repository;
    private AccountMapper mapper;
    private AccountNumberService numberService;
    private AccountService service;

    @BeforeEach
    void setUp() {
        repository = mock(AccountRepository.class);
        mapper = mock(AccountMapper.class);
        numberService = mock(AccountNumberService.class);

        service = new AccountService();
        try {
            var repoField = AccountService.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            var mapperField = AccountService.class.getDeclaredField("accountMapper");
            mapperField.setAccessible(true);
            mapperField.set(service, mapper);

            var numField = AccountService.class.getDeclaredField("accountNumberService");
            numField.setAccessible(true);
            numField.set(service, numberService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateAccount_Positive() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("John Doe");
        dto.setCustomerMobile("09171234567");
        dto.setCustomerEmail("john@example.com");
        dto.setAddress1("123 Main St");
        dto.setAccountType("S");

        Account entity = new Account();
        entity.setCustomerName("John Doe");
        entity.setAccountType(AccountTypeEnum.S);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(numberService.generateAccountNumber()).thenReturn("0120260000000001");

        AccountResponse response = service.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getTransactionStatusCode());
        assertEquals("Customer account created", response.getTransactionStatusDescription());
        assertEquals("0120260000000001", response.getCustomerNumber());

        verify(repository, times(1)).save(entity);
        verify(numberService, times(1)).generateAccountNumber();
    }

    @Test
    void testCreateAccount_Exception() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("ErrorUser");
        dto.setAccountType("S");

        Account entity = new Account();
        entity.setCustomerName("ErrorUser");
        entity.setAccountType(AccountTypeEnum.S);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(numberService.generateAccountNumber()).thenReturn("0120260000000002");
        doThrow(new RuntimeException("DB error")).when(repository).save(entity);

        assertThrows(RuntimeException.class, () -> service.create(dto));
    }

    @Test
    void testGetByCustomerNumber_Found() {
        Account account = new Account();
        account.setCustomerNumber("12345678");
        account.setCustomerName("Jane Doe");
        account.setCustomerMobile("09981234567");
        account.setCustomerEmail("jane@example.com");
        account.setAddress1("456 Ayala Ave");
        account.setAccountType(AccountTypeEnum.S);
        account.setAvailableBalance(1000.0);

        when(repository.existsByCustomerNumber("12345678")).thenReturn(true);
        when(repository.findByCustomerNumber("12345678")).thenReturn(List.of(account));

        AccountResponse response = service.getByCustomerNumber("12345678");

        assertNotNull(response);
        assertEquals(HttpStatus.FOUND.value(), response.getTransactionStatusCode());
        assertEquals("Customer Account found", response.getTransactionStatusDescription());
        assertEquals("12345678", response.getCustomerNumber());
        assertEquals("Jane Doe", response.getCustomerName());
        assertEquals(1, response.getSavings().size());
        assertEquals("Savings", response.getSavings().get(0).getAccountType());
    }

    @Test
    void testGetByCustomerNumber_NotFound_ExistsFalse() {
        when(repository.existsByCustomerNumber("Ghost")).thenReturn(false);

        AccountResponse response = service.getByCustomerNumber("Ghost");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getTransactionStatusCode());
        assertEquals("Customer not found", response.getTransactionStatusDescription());
        assertNull(response.getCustomerName());
        assertNull(response.getSavings());
    }

    @Test
    void testGetByCustomerNumber_NotFound_EmptyList() {
        when(repository.existsByCustomerNumber("Ghost")).thenReturn(true);
        when(repository.findByCustomerNumber("Ghost")).thenReturn(List.of());

        AccountResponse response = service.getByCustomerNumber("Ghost");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getTransactionStatusCode());
        assertEquals("Customer not found", response.getTransactionStatusDescription());
        assertNull(response.getCustomerName());
        assertNull(response.getSavings());
    }
}
