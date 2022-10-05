package com.example.sortener.controller;

import com.example.sortener.Service.AccountService;
import com.example.sortener.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account")
    public ResponseEntity<ResponseDto> registerAccount(@RequestBody String accountId) {
        return new ResponseEntity<>(accountService.saveAccount(accountId), HttpStatus.OK);
    }

}
