package com.example.sortener.Service;

import com.example.sortener.assembler.UrlAssemler;
import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.UrlRepository;
import com.example.sortener.validator.AuthenticateValidator;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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

    public UrlDto saveShortUrl(UrlDto dto){
            return urlAssemler.assembleDto(urlRepository.save(urlAssemler.assembleEntity(dto, validator.getAccountId())));
    }

    public List<UrlDto> getStatistics(String accountId) throws ChangeSetPersister.NotFoundException {
        List<Url> byAccountId_accountId = urlRepository.findByAccountId_AccountId(accountId);
        Optional.ofNullable(byAccountId_accountId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
            return urlAssemler.assmebleDtos(byAccountId_accountId);
    }

}
