package com.mb.account_service.builder;

import java.util.List;

import com.mb.account_service.dto.AccountResponse;
import com.mb.account_service.dto.SavingDto;

public class AccountResponseBuilder {
        private String customerNumber;
        private String customerName;
        private String customerMobile;
        private String customerEmail;
        private String address1;
        private String address2;
        private List<SavingDto> savings;
        private int transactionStatusCode;
        private String transactionStatusDescription;

        public AccountResponseBuilder customerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
            return this;
        }

        public AccountResponseBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public AccountResponseBuilder customerMobile(String customerMobile) {
            this.customerMobile = customerMobile;
            return this;
        }

        public AccountResponseBuilder customerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
            return this;
        }

        public AccountResponseBuilder address1(String address1) {
            this.address1 = address1;
            return this;
        }

        public AccountResponseBuilder address2(String address2) {
            this.address2 = address2;
            return this;
        }

        public AccountResponseBuilder savings(List<SavingDto> savings) {
            this.savings = savings;
            return this;
        }

        public AccountResponseBuilder transactionStatusCode(int code) {
            this.transactionStatusCode = code;
            return this;
        }

        public AccountResponseBuilder transactionStatusDescription(String description) {
            this.transactionStatusDescription = description;
            return this;
        }

        public AccountResponse build() {
            AccountResponse response = new AccountResponse();
            response.setCustomerNumber(customerNumber);
            response.setCustomerName(customerName);
            response.setCustomerMobile(customerMobile);
            response.setCustomerEmail(customerEmail);
            response.setAddress1(address1);
            response.setAddress2(address2);
            response.setSavings(savings);
            response.setTransactionStatusCode(transactionStatusCode);
            response.setTransactionStatusDescription(transactionStatusDescription);
            return response;
        }

    }