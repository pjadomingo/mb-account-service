package com.mb.account_service.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class AccountDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidAccountDto() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("John Doe");
        dto.setCustomerMobile("09171234567");
        dto.setCustomerEmail("john.doe@example.com");
        dto.setAddress1("123 Main Street");
        dto.setAddress2("Unit 5B");
        dto.setAccountType("S");

        Set<ConstraintViolation<AccountDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    @Test
    void testMissingRequiredFields() {
        AccountDto dto = new AccountDto(); // all fields null

        Set<ConstraintViolation<AccountDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Customer name is required field")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Customer mobile is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email is required field")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Address 1 is required field")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Account type is required field")));
    }

    @Test
    void testInvalidEmail() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("Jane Doe");
        dto.setCustomerMobile("09981234567");
        dto.setCustomerEmail("invalid-email"); // not a valid email
        dto.setAddress1("456 Ayala Avenue");
        dto.setAccountType("C");

        Set<ConstraintViolation<AccountDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Invalid email format")));
    }

    @Test
    void testInvalidAccountType() {
        AccountDto dto = new AccountDto();
        dto.setCustomerName("Mark Smith");
        dto.setCustomerMobile("09170000000");
        dto.setCustomerEmail("mark.smith@example.com");
        dto.setAddress1("789 Makati Avenue");
        dto.setAccountType("X"); // invalid, must be S or C

        Set<ConstraintViolation<AccountDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Account type must be 'S'")));
    }
}
