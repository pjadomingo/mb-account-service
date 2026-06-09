package com.mb.account_service.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "bank.branchCode=01",
        "bank.year=2026"
})
class BankConfigTest {

    @Autowired
    private BankConfig bankConfig;

    @Test
    void testBankConfigPropertiesBinding() {
        assertNotNull(bankConfig);
        assertEquals("01", bankConfig.getBranchCode());
        assertEquals("2026", bankConfig.getYear());
    }
}
