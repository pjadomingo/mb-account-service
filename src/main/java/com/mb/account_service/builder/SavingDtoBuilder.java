package com.mb.account_service.builder;

import com.mb.account_service.dto.SavingDto;

public class SavingDtoBuilder {
    private String accountNumber;
    private String accountType;
    private double availableBalance;

    public SavingDtoBuilder accountNumber(String string) {
        this.accountNumber = string;
        return this;
    }

    public SavingDtoBuilder accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public SavingDtoBuilder availableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public SavingDto build() {
        SavingDto saving =  new SavingDto();
        saving.setAccountNumber(accountNumber);
        saving.setAccountType(accountType);
        saving.setAvailableBalance(availableBalance);
        
        return saving;
    }
}