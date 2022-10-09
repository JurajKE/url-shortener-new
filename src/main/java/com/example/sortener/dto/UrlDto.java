package com.example.sortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlDto {

    private String url;
    private int redirectType;
    private String shortUrl;
    private int calls;

}
