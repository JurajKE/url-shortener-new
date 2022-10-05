package com.example.sortener.controller;

import com.example.sortener.Service.UrlService;
import com.example.sortener.dto.ResponseDto;
import com.example.sortener.dto.UrlDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/register")
    public ResponseEntity<UrlDto> registerUrl(@RequestBody UrlDto dto) {
        return new ResponseEntity<>(urlService.saveShortUrl(dto), HttpStatus.OK);
    }

    @GetMapping("/statistics/{accountId}")
    public ResponseEntity<UrlDto> getStatistics(@PathVariable String accountId) {
        return new ResponseEntity<>(urlService.getStatistics(accountId), HttpStatus.OK);
    }

}
