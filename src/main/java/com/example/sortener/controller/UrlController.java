package com.example.sortener.controller;

import com.example.sortener.Service.UrlService;
import com.example.sortener.dto.UrlDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/register")
    public ResponseEntity<UrlDto> registerUrl(HttpServletRequest httpServletRequest, @RequestBody UrlDto dto) throws Exception {
        return new ResponseEntity<>(urlService.saveShortUrl(dto, httpServletRequest), HttpStatus.OK);
    }

    @GetMapping("/statistics/{accountId}")
    public ResponseEntity<UrlDto> getStatistics(HttpServletRequest httpServletRequest, @PathVariable String accountId) throws Exception {
        return new ResponseEntity<>(urlService.getStatistics(accountId, httpServletRequest), HttpStatus.OK);
    }

}
