package com.mb.account_service.enums;

public enum AccountTypeEnum {
    S("Savings"),
    C("Checking");

    private final String description;

    AccountTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

