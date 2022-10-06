package com.example.sortener.assembler;

import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Account;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import static java.util.Objects.isNull;

@Component
public class UrlAssemler {

    private final AccountAssembler accountAssembler;
    private final AccountRepository accountRepository;
    private final UrlRepository urlRepository;

    public UrlAssemler(AccountAssembler accountAssembler, AccountRepository accountRepository, UrlRepository urlRepository) {
        this.accountAssembler = accountAssembler;
        this.accountRepository = accountRepository;
        this.urlRepository = urlRepository;
    }

    public Url assembleEntity(UrlDto dto, String userId) {
        if (isNull(dto)) {
            return null;
        }

        Url url = new Url();
        url.setId(1);
        url.setOriginalUrl(dto.getUrl());
        url.setShortUrl("http://short.com/" + encodedUrl(dto.getUrl()));
        url.setRedirectType(dto.getRedirectType());
        Account byAccountId = accountRepository.findByAccountId(userId);
        url.setAccountId(byAccountId);
//        Account account = new Account();
//        account.setId(12);
//        Account juraj = accountRepository.findByAccountId("juraj");
//        url.setAccountId(juraj);

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
