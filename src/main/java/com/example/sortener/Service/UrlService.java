package com.example.sortener.Service;

import com.example.sortener.assembler.AccountAssembler;
import com.example.sortener.assembler.UrlAssemler;
import com.example.sortener.dto.ResponseDto;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UrlService {

    private final AccountRepository accountRepository;
    private final AccountAssembler accountAssembler;
    private final UrlRepository urlRepository;
    private final UrlAssemler urlAssemler;

    public UrlService(AccountRepository accountRepository, AccountAssembler accountAssembler, UrlRepository urlRepository, UrlAssemler urlAssemler) {
        this.accountRepository = accountRepository;
        this.accountAssembler = accountAssembler;
        this.urlRepository = urlRepository;
        this.urlAssemler = urlAssemler;
    }

    public ResponseDto saveAccount(String accountId) {
        if (isNull(accountRepository.findByAccountId(accountId))) {
            return accountAssembler.assembleResponse(accountRepository.save(accountAssembler.assembleEntity(accountId)));
        }
        return accountAssembler.assembleFailedResponse();
    }

    public UrlDto saveShortUrl(UrlDto dto) {
        return urlAssemler.assembleDto(urlRepository.save(urlAssemler.assembleEntity(dto)));
    }

}
