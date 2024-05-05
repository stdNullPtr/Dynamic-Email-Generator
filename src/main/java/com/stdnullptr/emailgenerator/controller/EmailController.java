package com.stdnullptr.emailgenerator.controller;

import com.stdnullptr.emailgenerator.model.response.ApiResponse;
import com.stdnullptr.emailgenerator.service.EmailGeneratorService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/v1/email")
@RequiredArgsConstructor
public class EmailController extends EmailApi {
    private final EmailGeneratorService service;

    @Override
    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> generateEmails(
            @RequestParam
            @NotEmpty(message = ERROR_QUERY_EMPTY)
            MultiValueMap<String, String> queryParams) {

        List<String> generatedEmails = service.generateEmails(queryParams);

        ApiResponse<?> response = ApiResponse.success(generatedEmails);
        return ResponseEntity.ok().body(response);
    }
}
