package com.example.sortener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.example.sortener.constants.AppConstants.HELP_PAGE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping
@EnableWebMvc
@RequiredArgsConstructor
public class HelpController {

    @GetMapping(path = "help")
    public ResponseEntity<String> getHelp() {
        return ok(HELP_PAGE);
    }

}