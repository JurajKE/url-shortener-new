package com.example.sortener.assembler;

import com.example.sortener.dto.shortener.RequestUrlDto;
import com.example.sortener.dto.shortener.ResponseUrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static com.example.sortener.constants.AppConstants.APP_LINK;
import static com.example.sortener.constants.AppConstants.CHARACTERS;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.RandomStringUtils.random;

@Component
@RequiredArgsConstructor
public class ShortenerAssembler {

    @NonNull
    private final AccountRepository accountRepository;

    public Url assembleEntity(RequestUrlDto dto, String accountId) {
        if (isNull(dto)) {
            return null;
        }

        var url = new Url();
        url.setOriginalUrl(dto.getUrl());
        url.setShortUrl(random(10, CHARACTERS));
        url.setRedirectType(dto.getRedirectType() == 0 ? 302 : dto.getRedirectType());
        ofNullable(accountRepository.findByAccountId(accountId)).ifPresent(url::setAccountId);

        return url;
    }

    public ResponseUrlDto assembleDto(Url url) {
        if (isNull(url)) {
            return null;
        }

        var urlDto = new ResponseUrlDto();
        urlDto.setShortUrl(APP_LINK + url.getShortUrl());

        return urlDto;
    }

}