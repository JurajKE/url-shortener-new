package com.example.sortener.validator;

import com.example.sortener.dto.AccountDto;
import com.example.sortener.exceptions.MissingHeaderInfoException;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.AccountRepository;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

@Data
@Component
@RequiredArgsConstructor
public class ApplicationValidator {

    @NonNull
    private final AccountRepository accountRepository;
    private String accountId;

    public void authenticate(HttpServletRequest request) {
        var upd = request.getHeader("authorization");
        var credentials = new String(decodeBase64(upd.substring(6)));

        var userName = credentials.split(":")[0];
        var password = credentials.split(":")[1];
        var accountByUsername = accountRepository.findByAccountId(userName);
        if (isNull(accountByUsername) || !password.equals(accountByUsername.getPassword())) {
            throw new RecordFoundException("Not valid credentials");
        }
        accountId = userName;
    }

    public void validateUrl(String url) {
        var validator = new UrlValidator();
        if (!validator.isValid(url)) {
            throw new RecordFoundException("Not valid URL");
        }
    }

    public void validateAccount(AccountDto accountDto){
        if (isNull(accountDto.getAccountId()) || isEmpty(accountDto.getAccountId())){
            throw new MissingHeaderInfoException("Not valid account ID");
        }
    }

}
