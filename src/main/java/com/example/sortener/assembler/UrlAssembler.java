package com.example.sortener.assembler;

import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.validator.ApplicationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class UrlAssembler {

    @NonNull
    private final AccountRepository accountRepository;
    @NonNull
    private final ApplicationValidator validator;

    public Url assembleEntity(UrlDto dto) {
        if (isNull(dto)) {
            return null;
        }

        var url = new Url();
        url.setOriginalUrl(dto.getUrl());
        url.setShortUrl(validator.encodedUrl(dto.getUrl()));
        url.setRedirectType(dto.getRedirectType() == 0 ? 302 : dto.getRedirectType());
        ofNullable(accountRepository.findByAccountId(dto.getAccountId())).ifPresent(url::setAccountId);

        return url;
    }

    public UrlDto assembleDto(Url url) {
        if (isNull(url)) {
            return null;
        }

        var urlDto = new UrlDto();
        urlDto.setShortUrl(url.getShortUrl());

        return urlDto;
    }

}