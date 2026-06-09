package com.mb.account_service.dto;

import com.mb.account_service.builder.SavingDtoBuilder;

public class SavingDto {
    private String accountNumber;
    private String accountType;
    private double availableBalance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public double getAvailableBalance() {
        return availableBalance;
    }
    
    public static SavingDtoBuilder builder() {
        return new SavingDtoBuilder();
    }
}
