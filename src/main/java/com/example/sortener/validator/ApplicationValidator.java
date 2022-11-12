package com.example.sortener.validator;

import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.exceptions.RecordFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class ApplicationValidator {

    public void validateUrl(String url) {
        var validator = new UrlValidator();
        if (!validator.isValid(url)) {
            throw new RecordFoundException("Not valid URL");
        }
    }

    public void validateAccount(RequestAccountDto requestAccountDto) {
        if (isNull(requestAccountDto.getAccountId()) || isEmpty(requestAccountDto.getAccountId())) {
            throw new RecordFoundException("Not valid account ID");
        }
    }

}
