package com.stdnullptr.emailgenerator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailGeneratorService {
    public List<String> generateEmails(Map<String, String> inputs, String expression) {
        log.info("Generating emails for expression {}", expression);

        List<String> emails = new ArrayList<>();
        String email = parseExpression(inputs, expression);
        emails.add(email);
        return emails;
    }

    private String parseExpression(Map<String, String> inputs, String expression) {
        return inputs.toString() + expression;
    }
}
