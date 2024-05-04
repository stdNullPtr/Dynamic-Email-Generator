package com.stdnullptr.emailgenerator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmailGeneratorService {
    public List<String> generateEmails(MultiValueMap<String, String> inputs, String expression) {
        log.info("Generating emails for expression {}", expression);

        List<String> emails = new ArrayList<>();
        String email = parseExpression(inputs, expression);
        emails.add(email);
        return emails;
    }

    private String parseExpression(MultiValueMap<String, String> inputs, String expression) {
        return inputs.toString() + expression;
    }
}
