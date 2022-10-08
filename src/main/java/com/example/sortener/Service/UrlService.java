package com.example.sortener.Service;

import com.example.sortener.assembler.UrlAssembler;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.UrlRepository;
import com.example.sortener.validator.ApplicationValidator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

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

    public List<UrlDto> getStatistics(String accountId) {
        var listUrls = ofNullable(urlRepository.findByAccountId_AccountId(accountId));
        var dtosListUrls = new ArrayList<UrlDto>();

        listUrls.ifPresentOrElse(x -> {
            dtosListUrls.addAll(urlAssembler.assmebleDtos(listUrls.get()));
        }, () -> {
            throw new RecordFoundException("Account with this account id " + accountId + " does not exist");
        });
        return dtosListUrls;
    }

    public void redirectToOriginalUrl(String shortlink, HttpServletResponse response) {
        var urlObject = ofNullable(urlRepository.findByShortUrl(shortlink));
        urlObject.ifPresentOrElse(url -> {
            try {
                response.sendRedirect(urlObject.get().getOriginalUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> {
            throw new RecordFoundException("Url with this short link " + shortlink + " does not exist");
        });
    }

}
