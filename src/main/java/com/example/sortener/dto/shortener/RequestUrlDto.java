package com.example.sortener.dto.shortener;

import lombok.Data;

@Data
public class RequestUrlDto {

    private String url;
    private int redirectType;

}
