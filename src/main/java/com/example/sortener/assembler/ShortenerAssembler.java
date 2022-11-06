package com.example.sortener.assembler;

import com.example.sortener.dto.shortener.RequestUrlDto;
import com.example.sortener.dto.shortener.ResponseUrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.validator.ApplicationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static com.example.sortener.Constants.AppConstants.APP_LINK;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class ShortenerAssembler {

    @NonNull
    private final AccountRepository accountRepository;
    @NonNull
    private final ApplicationValidator validator;

    public Url assembleEntity(RequestUrlDto dto, String accountId) {
        if (isNull(dto)) {
            return null;
        }

        var url = new Url();
        url.setOriginalUrl(dto.getUrl());
        url.setShortUrl(validator.encodedUrl(dto.getUrl()));
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