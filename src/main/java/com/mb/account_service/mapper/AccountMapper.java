package com.mb.account_service.mapper;

import org.springframework.stereotype.Component;

import com.mb.account_service.dto.AccountDto;
import com.mb.account_service.enums.AccountTypeEnum;
import com.mb.account_service.model.Account;

@Component
public class AccountMapper {

    public Account toEntity(AccountDto dto) {
        return Account.builder()
                .customerName(dto.getCustomerName())
                .customerMobile(dto.getCustomerMobile())
                .customerEmail(dto.getCustomerEmail())
                .address1(dto.getAddress1())
                .address2(dto.getAddress2())
                .accountType(AccountTypeEnum.valueOf(dto.getAccountType()))
                .build();
    }

    public AccountDto toDTO(Account entity) {
    	return Account.builder()
                .customerName(entity.getCustomerName())
                .customerMobile(entity.getCustomerMobile())
                .customerEmail(entity.getCustomerEmail())
                .address1(entity.getAddress1())
                .address2(entity.getAddress2())
                .accountType(entity.getAccountType())
                .buildDto();
    }
}
