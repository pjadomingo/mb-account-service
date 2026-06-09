package com.mb.account_service.builder;

import com.mb.account_service.dto.AccountDto;
import com.mb.account_service.enums.AccountTypeEnum;
import com.mb.account_service.model.Account;

public class AccountBuilder {
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String address1;
    private String address2;
    private AccountTypeEnum accountType;

    public AccountBuilder customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public AccountBuilder customerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
        return this;
    }

    public AccountBuilder customerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public AccountBuilder address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public AccountBuilder address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public AccountBuilder accountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
        return this;
    }

    public Account build() {
        Account account = new Account();
        account.setCustomerName(customerName);
        account.setCustomerMobile(customerMobile);
        account.setCustomerEmail(customerEmail);
        account.setAddress1(address1);
        account.setAddress2(address2);
        account.setAccountType(accountType);
        return account;
    }
    
    public AccountDto buildDto() {
    	AccountDto account = new AccountDto();
         account.setCustomerName(customerName);
         account.setCustomerMobile(customerMobile);
         account.setCustomerEmail(customerEmail);
         account.setAddress1(address1);
         account.setAddress2(address2);
         account.setAccountType(accountType.toString());
         return account;
    }
}
