package com.example.sortener.Service;

import com.example.sortener.assembler.UrlAssemler;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.UrlRepository;
import com.example.sortener.validator.AuthenticateValidator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlAssemler urlAssemler;
    private final AuthenticateValidator validator;

    public UrlService(UrlRepository urlRepository, UrlAssemler urlAssemler, AuthenticateValidator validator) {
        this.urlRepository = urlRepository;
        this.urlAssemler = urlAssemler;
        this.validator = validator;
    }

    public UrlDto saveShortUrl(UrlDto dto, HttpServletRequest httpServletRequest) throws Exception {
        if (validator.authenticate(httpServletRequest)) {
            return urlAssemler.assembleDto(urlRepository.save(urlAssemler.assembleEntity(dto, validator.getAccountId())));
        }
        throw new Exception("Na picu pristupy");

    }

    public UrlDto getStatistics(String accountId, HttpServletRequest httpServletRequest) throws Exception {
        if (validator.authenticate(httpServletRequest)) {
            Url byAccountId = urlRepository.findByAccountId_AccountId(accountId);
            return urlAssemler.assembleDto(byAccountId);
        }
        throw new Exception("Na picu pristupy");
    }

}
