package com.example.sortener.controller;

import com.example.sortener.Service.AccountService;
import com.example.sortener.dto.AccountDto;
import com.example.sortener.dto.ResponseDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static java.lang.System.currentTimeMillis;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "account")
@EnableWebMvc
@RequiredArgsConstructor
public class AccountController {

    @NonNull
    private final AccountService accountService;
    private final Logger logger = getLogger(AccountController.class);

    @PostMapping
    public ResponseEntity<ResponseDto> registerAccount(@RequestBody AccountDto accountId) {
        var runTime = currentTimeMillis();
        var account = accountService.saveAccount(accountId);
        logger.debug("Register new account: {} took {} millis", accountId, currentTimeMillis() - runTime);
        return ok(account);
    }

}