package com.example.sortener.controller;

import com.example.sortener.Service.UrlService;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.validator.ApplicationValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "url")
@EnableWebMvc
@RequiredArgsConstructor
public class UrlController {

    @NonNull
    private final UrlService urlService;
    @NonNull
    private final ApplicationValidator validator;
    private final Logger logger = getLogger(UrlController.class);

    @PostMapping("/register")
    public ResponseEntity<UrlDto> registerUrl(HttpServletRequest httpServletRequest, @RequestBody UrlDto dto) {
        dto.setAccountId(validator.authenticate(httpServletRequest));
        long runTime = currentTimeMillis();
        var url = urlService.saveShortUrl(dto);
        logger.debug("Register new url: {} took {} millis", url.getUrl(), currentTimeMillis() - runTime);
        return ok(url);
    }

    @GetMapping("/statistics/{accountId}")
    public ResponseEntity<Map<String, Integer>> getStatistics(HttpServletRequest httpServletRequest, @PathVariable String accountId) {
        validator.authenticate(httpServletRequest);
        long runTime = currentTimeMillis();
        var statistics = urlService.getStatistics(accountId);
        logger.debug("Getting statistics for user: {} took {} millis", accountId, currentTimeMillis() - runTime);
        return ok(statistics);
    }

    @GetMapping("/{shortlink}")
    public void redirectToOriginalUrl(@PathVariable(value = "shortlink") String shortlink, HttpServletResponse response) {
        long runTime = currentTimeMillis();
        urlService.redirectToOriginalUrl(shortlink, response);
        logger.debug("Redirect from short link: {} took {} millis", shortlink, currentTimeMillis() - runTime);
    }

}
