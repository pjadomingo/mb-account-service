package com.mb.account_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.account_service.dto.AccountDto;
import com.mb.account_service.dto.AccountResponse;
import com.mb.account_service.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDto accountDto) {
        try{
        	AccountResponse response = service.create(accountDto);
        	
        	switch(response.getTransactionStatusCode()) {
        		case 400:
           		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        		case 201:
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
        	}
        	
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        
        return ResponseEntity.badRequest().body("Bad Request");
    }

    @GetMapping("/{customerName}")
    public ResponseEntity<?> getAccountByCustomerName(@PathVariable String customerName) {
        try{
        	AccountResponse response = service.getByCustomerNumber(customerName);
        	
        	switch(response.getTransactionStatusCode()) {
        		case 404:
           		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        		case 302:
                    return ResponseEntity.status(HttpStatus.FOUND).body(response);
        	}
        	
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        
        return ResponseEntity.badRequest().body("Bad Request KADAW");
    }
}
