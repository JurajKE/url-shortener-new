package com.example.sortener.Service;

import com.example.sortener.assembler.UrlAssembler;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.UrlRepository;
import com.example.sortener.validator.ApplicationValidator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlAssembler urlAssembler;
    private final ApplicationValidator validator;

    public UrlService(UrlRepository urlRepository, UrlAssembler urlAssembler, ApplicationValidator validator) {
        this.urlRepository = urlRepository;
        this.urlAssembler = urlAssembler;
        this.validator = validator;
    }

    public UrlDto saveShortUrl(UrlDto dto) {
        validator.validateUrl(dto.getUrl());
        return urlAssembler.assembleDto(urlRepository.save(urlAssembler.assembleEntity(dto, validator.getAccountId())));
    }

    public Map getStatistics(String accountId) {
        Map map;
        var listUrls = ofNullable(urlRepository.findByAccountId_AccountId(accountId));
        if (listUrls.isEmpty()) {
            throw new RecordFoundException("Account with this account id " + accountId + " does not exist");
        }
        map = listUrls.get().stream().collect(toMap(Url::getOriginalUrl, Url::getCalls, (link, calls) -> calls));
        return map;
    }

    public void redirectToOriginalUrl(String shortlink, HttpServletResponse response) {
        var urlObject = ofNullable(urlRepository.findByShortUrl(shortlink));
        urlObject.ifPresentOrElse(url -> {
            try {
                updateCalls(url);
                response.sendRedirect(url.getOriginalUrl());
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }, () -> {
            throw new RecordFoundException("Url with this short link " + shortlink + " does not exist");
        });
    }

    private void updateCalls(Url url) {
        url.setCalls(url.getCalls() + 1);
        urlRepository.save(url);
    }

}
