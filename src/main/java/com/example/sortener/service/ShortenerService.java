package com.example.sortener.service;

import com.example.sortener.assembler.ShortenerAssembler;
import com.example.sortener.dto.shortener.RequestUrlDto;
import com.example.sortener.dto.shortener.ResponseUrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.UrlRepository;
import com.example.sortener.validator.ApplicationValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ShortenerService {

    @NonNull
    private final UrlRepository urlRepository;
    @NonNull
    private final ShortenerAssembler shortenerAssembler;
    @NonNull
    private final ApplicationValidator validator;

    public ResponseUrlDto saveShortUrl(RequestUrlDto dto, String accountId) {
        validator.validateUrl(dto.getUrl());
        return shortenerAssembler.assembleDto(urlRepository.save(shortenerAssembler.assembleEntity(dto, accountId)));
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