package com.mb.account_service.service;

import org.springframework.stereotype.Service;

import com.mb.account_service.config.BankConfig;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class AccountNumberService {

    @PersistenceContext
    private EntityManager entityManager;
    
    private BankConfig bankConfig;
    
    public AccountNumberService(BankConfig bankConfig) {
    	this.bankConfig = bankConfig;
    }

    public Long getNextSequenceValue() {
        return ((Number) entityManager
            .createNativeQuery("SELECT NEXT VALUE FOR account_seq")
            .getSingleResult()).longValue();
    }

    public String generateAccountNumber() {
        Long seq = getNextSequenceValue();
        String base = bankConfig.getBranchCode() + bankConfig.getYear()+ String.format("%010d", seq);
        int checkDigit = calculateLuhn(base);
        return base + checkDigit;
    }

    private int calculateLuhn(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n = (n % 10) + 1;
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }
}
