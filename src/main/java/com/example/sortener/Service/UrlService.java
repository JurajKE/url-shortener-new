package com.example.sortener.Service;

import com.example.sortener.assembler.UrlAssembler;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.UrlRepository;
import com.example.sortener.validator.ApplicationValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class UrlService {

    @NonNull
    private final UrlRepository urlRepository;
    @NonNull
    private final UrlAssembler urlAssembler;
    @NonNull
    private final ApplicationValidator validator;

    public UrlDto saveShortUrl(UrlDto dto) {
        validator.validateUrl(dto.getUrl());
        return urlAssembler.assembleDto(urlRepository.save(urlAssembler.assembleEntity(dto)));
    }

    public Map<String, Integer> getStatistics(String accountId) {
        Map<String, Integer> map;
        var listUrls = ofNullable(urlRepository.findByAccountId_AccountId(accountId));
        if (listUrls.isEmpty()) {
            throw new RecordFoundException("Account with this account id " + accountId + " does not exist");
        }
        map = listUrls.get().stream().collect(toMap(Url::getOriginalUrl, Url::getCalls, (link, calls) -> calls));
        return map;
    }

    public void redirectToOriginalUrl(String shortLink, HttpServletResponse response) {
        var urlObject = ofNullable(urlRepository.findByShortUrl(shortLink));
        urlObject.ifPresentOrElse(url -> {
            try {
                response.sendRedirect(url.getOriginalUrl());
                updateCalls(url);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }, () -> {
            throw new RecordFoundException("Url with this short link " + shortLink + " does not exist");
        });
    }

    private void updateCalls(Url url) {
        url.setCalls(url.getCalls() + 1);
        urlRepository.save(url);
    }

}