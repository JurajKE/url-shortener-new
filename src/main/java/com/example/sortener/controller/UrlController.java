package com.example.sortener.controller;

import com.example.sortener.Service.UrlService;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.validator.ApplicationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "url")
@EnableWebMvc
public class UrlController {

    private final UrlService urlService;
    private final ApplicationValidator validator;

    public UrlController(UrlService urlService, ApplicationValidator validator) {
        this.urlService = urlService;
        this.validator = validator;
    }

    @PostMapping("/register")
    public ResponseEntity<UrlDto> registerUrl(HttpServletRequest httpServletRequest, @RequestBody UrlDto dto) {
        validator.authenticate(httpServletRequest);
        return new ResponseEntity<>(urlService.saveShortUrl(dto), HttpStatus.OK);
    }

    @GetMapping("/statistics/{accountId}")
    public ResponseEntity<List<UrlDto>> getStatistics(HttpServletRequest httpServletRequest, @PathVariable String accountId) throws Exception {
        validator.authenticate(httpServletRequest);
        return new ResponseEntity<>(urlService.getStatistics(accountId), HttpStatus.OK);
    }

    @GetMapping("/{shortlink}")
    public void redirectToOriginalUrl(@PathVariable String shortlink, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        validator.authenticate(httpServletRequest);
        urlService.redirectToOriginalUrl(shortlink, response);
    }

}
