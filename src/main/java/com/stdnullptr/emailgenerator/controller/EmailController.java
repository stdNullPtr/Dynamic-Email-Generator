package com.stdnullptr.emailgenerator.controller;

import com.stdnullptr.emailgenerator.service.EmailGeneratorService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/v1/email")
@RequiredArgsConstructor
public class EmailController implements EmailApi {
    private final EmailGeneratorService service;

    @Override
    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> generateEmails(@RequestParam @NotEmpty(message = "Query parameters must be provided") Map<String, String> queryParams) {

        List<String> generatedEmails = service.generateEmails(queryParams, null);
        return ResponseEntity.ok().body(generatedEmails);
    }
}
