package com.example.sortener.Service;

import com.example.sortener.assembler.AccountAssembler;
import com.example.sortener.assembler.UrlAssemler;
import com.example.sortener.dto.FinalResponseDto;
import com.example.sortener.dto.ResponseDto;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlAssemler urlAssemler;

    public UrlService(UrlRepository urlRepository, UrlAssemler urlAssemler) {
        this.urlRepository = urlRepository;
        this.urlAssemler = urlAssemler;
    }

    public UrlDto saveShortUrl(UrlDto dto) {
        return urlAssemler.assembleDto(urlRepository.save(urlAssemler.assembleEntity(dto)));
    }

    public UrlDto getStatistics(String accountId) {
        Url byAccountId = urlRepository.findByAccountId_AccountId(accountId);
        return urlAssemler.assembleDto(byAccountId);
    }

}
