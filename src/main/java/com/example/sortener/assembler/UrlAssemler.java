package com.example.sortener.assembler;

import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Url;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import static java.util.Objects.isNull;

@Component
public class UrlAssemler {

    private final AccountAssembler accountAssembler;

    public UrlAssemler(AccountAssembler accountAssembler) {
        this.accountAssembler = accountAssembler;
    }

    public Url assembleEntity(UrlDto dto) {
        if (isNull(dto)) {
            return null;
        }

        Url url = new Url();
        url.setOriginalUrl(dto.getUrl());
        url.setShortUrl("http://short.com/" + encodedUrl(dto.getUrl()));
        url.setRedirectType(dto.getRedirectType());

        return url;
    }

    public UrlDto assembleDto(Url url) {
        if (isNull(url)) {
            return null;
        }

        UrlDto urlDto = new UrlDto();
        urlDto.setShortUrl(url.getShortUrl());

        return urlDto;
    }

    public String encodedUrl(String url) {
        String encodedUrl = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        encodedUrl = Hashing.murmur3_32().hashString(url.concat(timestamp.toString()), StandardCharsets.UTF_8).toString();
        return encodedUrl;
    }

}
