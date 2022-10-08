package com.example.sortener.assembler;

import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Account;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.AccountRepository;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.hash.Hashing.murmur3_32;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;

@Component
public class UrlAssemler {

    private final AccountRepository accountRepository;

    public UrlAssemler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Url assembleEntity(UrlDto dto, String userId) {
        if (isNull(dto)) {
            return null;
        }

        var url = new Url();
        url.setOriginalUrl(dto.getUrl());
        url.setShortUrl(encodedUrl(dto.getUrl()));
        url.setRedirectType(dto.getRedirectType());
        var byAccountId = accountRepository.findByAccountId(userId);
        url.setAccountId(byAccountId);

        return url;
    }

    public UrlDto assembleDto(Url url) {
        if (isNull(url)) {
            return null;
        }

        var urlDto = new UrlDto();
        urlDto.setShortUrl(url.getShortUrl());
        urlDto.setRedirectType(url.getRedirectType());

        return urlDto;
    }

    public List<UrlDto> assmebleDtos(List<Url> urlList) {
        return urlList.stream().map(this::assembleDto).collect(Collectors.toList());
    }

    public String encodedUrl(String url) {
        var encodedUrl = "";
        var timestamp = new Timestamp(currentTimeMillis());
        encodedUrl = murmur3_32().hashString(url.concat(timestamp.toString()), UTF_8).toString();
        return encodedUrl;
    }

}
