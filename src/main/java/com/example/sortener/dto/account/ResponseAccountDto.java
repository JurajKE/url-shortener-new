package com.example.sortener.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class ResponseAccountDto {

    private boolean success;
    private String description;
    private String password;

}
