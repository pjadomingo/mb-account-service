package com.mb.account_service.model;

import org.junit.jupiter.api.Test;

import com.mb.account_service.enums.AccountTypeEnum;
import com.mb.account_service.model.Account;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountTest {

    @Test
    void testSettersAndGetters() {
        Account account = new Account();
        account.setId(1L);
        account.setCustomerName("John Doe");
        account.setCustomerNumber("12345678");
        account.setCustomerMobile("09171234567");
        account.setCustomerEmail("john.doe@example.com");
        account.setAddress1("123 Main Street");
        account.setAddress2("Unit 5B");
        account.setAccountType(AccountTypeEnum.S);
        account.setAvailableBalance(1000.50);

        assertEquals(1L, account.getId());
        assertEquals("John Doe", account.getCustomerName());
        assertEquals("12345678", account.getCustomerNumber());
        assertEquals("09171234567", account.getCustomerMobile());
        assertEquals("john.doe@example.com", account.getCustomerEmail());
        assertEquals("123 Main Street", account.getAddress1());
        assertEquals("Unit 5B", account.getAddress2());
        assertEquals(AccountTypeEnum.S, account.getAccountType());
        assertEquals(1000.50, account.getAvailableBalance());
    }

    @Test
    void testBuilder() {
        Account account = Account.builder()
                .customerName("Jane Smith")
                .customerMobile("09981234567")
                .customerEmail("jane.smith@example.com")
                .address1("456 Ayala Avenue")
                .address2("Floor 10")
                .accountType(AccountTypeEnum.C)
                .build();

        assertNotNull(account);
        assertEquals("Jane Smith", account.getCustomerName());
        assertEquals("09981234567", account.getCustomerMobile());
        assertEquals("jane.smith@example.com", account.getCustomerEmail());
        assertEquals("456 Ayala Avenue", account.getAddress1());
        assertEquals("Floor 10", account.getAddress2());
        assertEquals(AccountTypeEnum.C, account.getAccountType());
    }

    @Test
    void testMissingOptionalFields() {
        Account account = new Account();
        account.setCustomerName("Ghost User");
        account.setAccountType(AccountTypeEnum.S);

        assertEquals("Ghost User", account.getCustomerName());
        assertNull(account.getCustomerNumber());
        assertNull(account.getCustomerMobile());
        assertNull(account.getCustomerEmail());
        assertNull(account.getAddress1());
        assertNull(account.getAddress2());
        assertEquals(AccountTypeEnum.S, account.getAccountType());
        assertEquals(0.0, account.getAvailableBalance()); // default double
    }

    @Test
    void testSpyOnAccount() {
        Account spyAccount = spy(new Account());

        spyAccount.setCustomerName("Spy User");
        spyAccount.setAccountType(AccountTypeEnum.C);
        spyAccount.setAvailableBalance(500.0);

        verify(spyAccount).setCustomerName("Spy User");
        verify(spyAccount).setAccountType(AccountTypeEnum.C);
        verify(spyAccount).setAvailableBalance(500.0);

        assertEquals("Spy User", spyAccount.getCustomerName());
        assertEquals(AccountTypeEnum.C, spyAccount.getAccountType());
        assertEquals(500.0, spyAccount.getAvailableBalance());
    }
}
