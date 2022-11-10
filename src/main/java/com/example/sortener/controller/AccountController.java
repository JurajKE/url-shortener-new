package com.example.sortener.controller;

import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.dto.account.ResponseAccountDto;
import com.example.sortener.service.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping
@EnableWebMvc
@RequiredArgsConstructor
public class AccountController {
    @NonNull
    private final AccountService accountService;
    private final Logger logger = getLogger(AccountController.class);

    @PostMapping("/account")
    public ResponseEntity<ResponseAccountDto> registerAccount(@RequestBody RequestAccountDto accountId) {
        var runTime = currentTimeMillis();
        var account = accountService.saveAccount(accountId);
        logger.debug("Register new account: {} took {} millis", accountId, currentTimeMillis() - runTime);
        return ok(account);
    }

    @GetMapping("statistics/{accountId}")
    public ResponseEntity<Map<String, Integer>> getStatistics(@PathVariable String accountId) {
        var runTime = currentTimeMillis();
        var statistics = accountService.getStatistics(accountId);
        logger.debug("Getting statistics for user: {} took {} millis", accountId, currentTimeMillis() - runTime);
        return ok(statistics);
    }

}