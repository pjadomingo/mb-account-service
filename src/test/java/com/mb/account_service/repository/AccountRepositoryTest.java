package com.mb.account_service.repository;

import com.mb.account_service.enums.AccountTypeEnum;
import com.mb.account_service.model.Account;
import com.mb.account_service.repository.AccountRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    @Test
    void testFindByCustomerNumber_Positive() {
        Account account = new Account();
        account.setCustomerName("John Doe");
        account.setCustomerNumber("12345678");
        account.setCustomerMobile("09171234567");
        account.setCustomerEmail("john@example.com");
        account.setAddress1("123 Main Street");
        account.setAccountType(AccountTypeEnum.S);
        account.setAvailableBalance(1000.0);

        repository.save(account);

        List<Account> result = repository.findByCustomerNumber("12345678");

        assertFalse(result.isEmpty());
        assertEquals("John Doe", result.get(0).getCustomerName());
        assertEquals("12345678", result.get(0).getCustomerNumber());
    }

    @Test
    void testExistsByCustomerNumber_Positive() {
        Account account = new Account();
        account.setCustomerName("Jane Doe");
        account.setCustomerNumber("87654321");
        account.setCustomerMobile("09981234567");
        account.setCustomerEmail("jane@example.com");
        account.setAddress1("456 Ayala Avenue");
        account.setAccountType(AccountTypeEnum.C);
        account.setAvailableBalance(500.0);

        repository.save(account);

        boolean exists = repository.existsByCustomerNumber("87654321");

        assertTrue(exists);
    }

    @Test
    void testExistsByCustomerNumber_Negative() {
        boolean exists = repository.existsByCustomerNumber("Ghost");
        assertFalse(exists);

        List<Account> result = repository.findByCustomerNumber("Ghost");
        assertTrue(result.isEmpty());
    }
}
