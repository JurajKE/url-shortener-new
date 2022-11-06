package com.example.sortener.validator;

import com.example.sortener.dto.AccountBaseDto;
import com.example.sortener.exceptions.MissingHeaderInfoException;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.AccountRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;

import static com.google.common.hash.Hashing.murmur3_32;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

@Data
@Component
@RequiredArgsConstructor
public class ApplicationValidator {

    @NonNull
    private final AccountRepository accountRepository;

    public String authenticate(HttpServletRequest request) {
        var upd = request.getHeader("authorization");
        var credentials = new String(decodeBase64(upd.substring(6)));

        var userName = credentials.split(":")[0];
        var password = credentials.split(":")[1];
        var accountByUsername = accountRepository.findByAccountId(userName);
        if (isNull(accountByUsername) || !password.equals(accountByUsername.getPassword())) {
            throw new MissingHeaderInfoException("Not valid credentials");
        }
        return userName;
    }

    public void validateUrl(String url) {
        var validator = new UrlValidator();
        if (!validator.isValid(url)) {
            throw new RecordFoundException("Not valid URL");
        }
    }

    public void validateAccount(AccountBaseDto accountBaseDto){
        if (isNull(accountBaseDto.getAccountId()) || isEmpty(accountBaseDto.getAccountId())){
            throw new MissingHeaderInfoException("Not valid account ID");
        }
    }

    public String encodedUrl(String url) {
        var encodedUrl = "";
        var timestamp = new Timestamp(currentTimeMillis());
        encodedUrl = murmur3_32().hashString(url.concat(timestamp.toString()), UTF_8).toString();
        return encodedUrl;
    }

}
