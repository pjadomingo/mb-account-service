package com.mb.account_service.builder;

import com.mb.account_service.builder.AccountBuilder;
import com.mb.account_service.dto.AccountDto;
import com.mb.account_service.enums.AccountTypeEnum;
import com.mb.account_service.model.Account;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountBuilderTest {

    @Test
    void testBuildAccount_UsingMockito() {
        Account mockAccount = Mockito.mock(Account.class);

        AccountBuilder builder = new AccountBuilder()
                .customerName("John Doe")
                .customerMobile("09171234567")
                .customerEmail("john.doe@example.com")
                .address1("123 Main Street")
                .address2("Unit 5B")
                .accountType(AccountTypeEnum.S);

        // Use spy to intercept the build process
        Account account = spy(builder).build();

        assertNotNull(account);
        assertEquals("John Doe", account.getCustomerName());
        assertEquals("09171234567", account.getCustomerMobile());
        assertEquals("john.doe@example.com", account.getCustomerEmail());
        assertEquals("123 Main Street", account.getAddress1());
        assertEquals("Unit 5B", account.getAddress2());
        assertEquals(AccountTypeEnum.S, account.getAccountType());
    }

    @Test
    void testBuildAccountDto_UsingMockito() {
        AccountDto mockDto = Mockito.mock(AccountDto.class);

        AccountBuilder builder = new AccountBuilder()
                .customerName("Jane Smith")
                .customerMobile("09981234567")
                .customerEmail("jane.smith@example.com")
                .address1("456 Ayala Avenue")
                .address2("Floor 10")
                .accountType(AccountTypeEnum.C);

        AccountDto dto = builder.buildDto();

        assertNotNull(dto);
        assertEquals("Jane Smith", dto.getCustomerName());
        assertEquals("09981234567", dto.getCustomerMobile());
        assertEquals("jane.smith@example.com", dto.getCustomerEmail());
        assertEquals("456 Ayala Avenue", dto.getAddress1());
        assertEquals("Floor 10", dto.getAddress2());
        assertEquals("C", dto.getAccountType());

        // Mockito verification example
        verify(mockDto, never()).setCustomerName("Jane Smith"); // builder creates a new object, not the mock
    }

    @Test
    void testBuildAccountDto_Negative_MissingAccountType() {
        AccountBuilder builder = new AccountBuilder()
                .customerName("No Type")
                .customerMobile("09170000000")
                .customerEmail("notype@example.com")
                .address1("Unknown Street");

        assertThrows(NullPointerException.class, builder::buildDto,
                "Expected NullPointerException when accountType is not set");
    }
}
