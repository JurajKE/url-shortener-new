package com.example.sortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class UrlDto {

    private String url;
    private int redirectType;
    private String shortUrl;
    private int calls;
    private String accountId;

}
