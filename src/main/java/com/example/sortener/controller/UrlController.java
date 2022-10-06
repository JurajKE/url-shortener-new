package com.example.sortener.controller;

import com.example.sortener.Service.UrlService;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.validator.AuthenticateValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "url")
public class UrlController {

    private final UrlService urlService;
    private final AuthenticateValidator validator;

    public UrlController(UrlService urlService, AuthenticateValidator validator) {
        this.urlService = urlService;
        this.validator = validator;
    }

    @PostMapping("/register")
    public ResponseEntity<UrlDto> registerUrl(HttpServletRequest httpServletRequest, @RequestBody UrlDto dto) throws Exception {
        validator.authenticate(httpServletRequest);
        return new ResponseEntity<>(urlService.saveShortUrl(dto), HttpStatus.OK);
    }

    @GetMapping("/statistics/{accountId}")
    public ResponseEntity<List<UrlDto>> getStatistics(HttpServletRequest httpServletRequest, @PathVariable String accountId) throws Exception {
        validator.authenticate(httpServletRequest);
        return new ResponseEntity<>(urlService.getStatistics(accountId), HttpStatus.OK);
    }

}
