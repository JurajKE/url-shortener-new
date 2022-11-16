package com.example.sortener.controller;

import com.example.sortener.Service.UrlService;
import com.example.sortener.dto.ResponseDto;
import com.example.sortener.dto.UrlDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/account")
    public ResponseEntity<ResponseDto> registerAccount(@RequestBody String accountId) {
        return new ResponseEntity<>(urlService.saveAccount(accountId), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UrlDto> registerUrl(@RequestBody UrlDto dto) {
        return new ResponseEntity<>(urlService.saveShortUrl(dto), HttpStatus.OK);
    }

}
