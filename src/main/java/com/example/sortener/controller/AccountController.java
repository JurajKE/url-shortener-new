package com.example.sortener.controller;

import com.example.sortener.Service.AccountService;
import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.dto.account.ResponseAccountDto;
import com.example.sortener.validator.ApplicationValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
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
    @NonNull
    private final ApplicationValidator validator;
    private final Logger logger = getLogger(AccountController.class);

    @PostMapping("/account")
    public ResponseEntity<ResponseAccountDto> registerAccount(@RequestBody RequestAccountDto accountId) {
        var runTime = currentTimeMillis();
        var account = accountService.saveAccount(accountId);
        logger.debug("Register new account: {} took {} millis", accountId, currentTimeMillis() - runTime);
        return ok(account);
    }

    @GetMapping("statistics/{accountId}")
    public ResponseEntity<Map<String, Integer>> getStatistics(HttpServletRequest httpServletRequest, @PathVariable String accountId) {
        validator.authenticate(httpServletRequest);
        long runTime = currentTimeMillis();
        var statistics = accountService.getStatistics(accountId);
        logger.debug("Getting statistics for user: {} took {} millis", accountId, currentTimeMillis() - runTime);
        return ok(statistics);
    }

}