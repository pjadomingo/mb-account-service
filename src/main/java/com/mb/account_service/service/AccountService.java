package com.mb.account_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mb.account_service.builder.AccountResponseBuilder;
import com.mb.account_service.dto.AccountDto;
import com.mb.account_service.dto.AccountResponse;
import com.mb.account_service.dto.SavingDto;
import com.mb.account_service.mapper.AccountMapper;
import com.mb.account_service.model.Account;
import com.mb.account_service.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
    private AccountRepository repository;

    @Autowired
    private AccountMapper accountMapper;
    
    @Autowired
    private AccountNumberService accountNumberService;
    
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	
    public AccountResponse create(AccountDto accountDto) {
    	Account account = accountMapper.toEntity(accountDto);
 		AccountResponseBuilder responseBuilder = AccountResponse.builder()
 				.transactionStatusCode(HttpStatus.BAD_REQUEST.value());
 		
        try {
            account.setCustomerNumber(accountNumberService.generateAccountNumber());
            account.setAvailableBalance(500);
            repository.save(account);
            
            responseBuilder.customerNumber(account.getCustomerNumber())
            .transactionStatusCode(HttpStatus.CREATED.value())
            .transactionStatusDescription("Customer account created");
            
            return responseBuilder.build();
        }catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        
    }
	
	public AccountResponse getByCustomerNumber(String customerNumber) {

		AccountResponseBuilder responseBuilder = AccountResponse.builder()
				.transactionStatusCode(HttpStatus.NOT_FOUND.value())
				.transactionStatusDescription("Customer not found");
		
		if(!repository.existsByCustomerNumber(customerNumber)) {
			return responseBuilder.build();
		}
		
		List<Account> retrieved = repository.findByCustomerNumber(customerNumber);
		
		
		if(!retrieved.isEmpty()) {
			Account account = retrieved.get(0);
			
			responseBuilder
			        .customerNumber(account.getCustomerNumber())
			        .customerName(account.getCustomerName())
			        .customerMobile(account.getCustomerMobile())
			        .customerEmail(account.getCustomerEmail())
			        .address1(account.getAddress1())
			        .address2(account.getAddress2())
			        .transactionStatusCode(HttpStatus.FOUND.value())
			        .transactionStatusDescription("Customer Account found");
			
			List<SavingDto> savings = new ArrayList<SavingDto>();
			retrieved.forEach(a ->{
				savings.add(SavingDto.builder()
						.accountNumber(a.getCustomerNumber())
						.accountType(a.getAccountType().getDescription())
						.availableBalance(a.getAvailableBalance())
						.build());
			});
			
			responseBuilder.savings(savings);
			
			return responseBuilder.build();
		}
		
		
		return responseBuilder.build();
	}
	
}
