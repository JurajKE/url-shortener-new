package com.example.sortener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping
@EnableWebMvc
@RequiredArgsConstructor
public class HelpController {

    @GetMapping("help")
    public ResponseEntity<Map<String, Integer>> getStatistics(HttpServletRequest httpServletRequest, @PathVariable String accountId) {
        validator.authenticate(httpServletRequest);
        long runTime = currentTimeMillis();
        var statistics = accountService.getStatistics(accountId);
        logger.debug("Getting statistics for user: {} took {} millis", accountId, currentTimeMillis() - runTime);
        return ok(statistics);
    }

}
