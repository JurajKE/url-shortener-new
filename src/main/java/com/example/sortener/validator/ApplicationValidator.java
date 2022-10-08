package com.example.sortener.validator;

import com.example.sortener.entity.Account;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.AccountRepository;
import lombok.Data;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Data
@Component
public class ApplicationValidator {

    private final AccountRepository accountRepository;
    private String accountId;

    public ApplicationValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void authenticate(HttpServletRequest request) {
        var upd = request.getHeader("authorization");
        var credentials = new String(Base64.decodeBase64(upd.substring(6)));

        var userName = credentials.split(":")[0];
        var password = credentials.split(":")[1];
        var byAccountId = accountRepository.findByAccountId(userName);
        accountId = userName;
        if (!byAccountId.getPassword().equals(password)) {
            throw new RuntimeException("Bad credentials");
        }
    }

    public void validateUrl(String url) {
        var validator = new UrlValidator();
        if (!validator.isValid(url)) {
            throw new RecordFoundException("Not valid URL");
        }
    }

}
