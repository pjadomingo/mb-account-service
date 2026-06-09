package com.mb.account_service.mapper;

import com.mb.account_service.dto.AccountDto;
import com.mb.account_service.enums.AccountTypeEnum;
import com.mb.account_service.mapper.AccountMapper;
import com.mb.account_service.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountMapperTest {

    private AccountMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AccountMapper();
    }

    @Test
    void testToEntity_Positive() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("John Doe");
        dto.setCustomerMobile("09171234567");
        dto.setCustomerEmail("john.doe@example.com");
        dto.setAddress1("123 Main Street");
        dto.setAddress2("Unit 5B");
        dto.setAccountType("S");

        Account entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("John Doe", entity.getCustomerName());
        assertEquals("09171234567", entity.getCustomerMobile());
        assertEquals("john.doe@example.com", entity.getCustomerEmail());
        assertEquals("123 Main Street", entity.getAddress1());
        assertEquals("Unit 5B", entity.getAddress2());
        assertEquals(AccountTypeEnum.S, entity.getAccountType());
    }

    @Test
    void testToEntity_InvalidAccountType() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("Jane Doe");
        dto.setCustomerMobile("09981234567");
        dto.setCustomerEmail("jane.doe@example.com");
        dto.setAddress1("456 Ayala Avenue");
        dto.setAccountType("X"); // invalid

        assertThrows(IllegalArgumentException.class, () -> mapper.toEntity(dto));
    }

    @Test
    void testToDTO_Positive() {
        Account entity = Account.builder()
                .customerName("Mark Smith")
                .customerMobile("09170000000")
                .customerEmail("mark.smith@example.com")
                .address1("789 Makati Avenue")
                .address2("Floor 3")
                .accountType(AccountTypeEnum.C)
                .build();

        AccountDto dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals("Mark Smith", dto.getCustomerName());
        assertEquals("09170000000", dto.getCustomerMobile());
        assertEquals("mark.smith@example.com", dto.getCustomerEmail());
        assertEquals("789 Makati Avenue", dto.getAddress1());
        assertEquals("Floor 3", dto.getAddress2());
        assertEquals("C", dto.getAccountType()); // enum converted to string
    }

    @Test
    void testToDTO_VerifyEntityGetters() {
        Account entity = spy(Account.builder()
                .customerName("Spy User")
                .customerMobile("09179999999")
                .customerEmail("spy@example.com")
                .address1("Spy Street")
                .accountType(AccountTypeEnum.S)
                .build());

        mapper.toDTO(entity);

        verify(entity).getCustomerName();
        verify(entity).getCustomerMobile();
        verify(entity).getCustomerEmail();
        verify(entity).getAddress1();
        verify(entity).getAccountType();
    }
}
