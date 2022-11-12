package com.example.sortener.validator;

import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.exceptions.RecordFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

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

    public String getAccountId(HttpServletRequest request) {
        return new String(decodeBase64(request.getHeader("authorization").substring(6))).split(":")[0];
    }

}
