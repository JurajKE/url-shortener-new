package com.example.sortener.validator;

import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.exceptions.RecordFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import static com.google.common.hash.Hashing.murmur3_32;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Data
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

    public String encodedUrl(String url) {
        var encodedUrl = "";
        var timestamp = new Timestamp(currentTimeMillis());
        encodedUrl = murmur3_32().hashString(url.concat(timestamp.toString()), UTF_8).toString();
        return encodedUrl;
    }

}
