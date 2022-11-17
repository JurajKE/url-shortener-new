package com.example.sortener.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.sortener.constants.AppConstants.HELP_PAGE;

@RestController
@RequestMapping
public class HelpController {

    @GetMapping(value = "help", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getHelp() {
        return HELP_PAGE;
    }

}