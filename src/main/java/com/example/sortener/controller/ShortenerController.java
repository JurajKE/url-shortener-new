package com.example.sortener.controller;

import com.example.sortener.dto.shortener.RequestUrlDto;
import com.example.sortener.dto.shortener.ResponseUrlDto;
import com.example.sortener.service.ShortenerService;
import com.example.sortener.validator.ApplicationValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.System.currentTimeMillis;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping
@EnableWebMvc
@RequiredArgsConstructor
public class ShortenerController {

    @NonNull
    private final ShortenerService shortenerService;
    @NonNull
    private final ApplicationValidator validator;
    private final Logger logger = getLogger(ShortenerController.class);

    @PostMapping("register")
    public ResponseEntity<ResponseUrlDto> registerUrl(HttpServletRequest authentication, @RequestBody RequestUrlDto dto) {
        long runTime = currentTimeMillis();
        var url = shortenerService.saveShortUrl(dto, validator.getAccountId(authentication));
        logger.debug("Register new url: took {} millis", currentTimeMillis() - runTime);
        return ok(url);
    }

    @GetMapping("{shortlink}")
    public void redirectToOriginalUrl(@PathVariable(value = "shortlink") String shortlink, HttpServletResponse response) {
        long runTime = currentTimeMillis();
        shortenerService.redirectToOriginalUrl(shortlink, response);
        logger.debug("Redirect from short link: {} took {} millis", shortlink, currentTimeMillis() - runTime);
    }

}
