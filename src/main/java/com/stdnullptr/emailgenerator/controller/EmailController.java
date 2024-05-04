package com.stdnullptr.emailgenerator.controller;

import com.stdnullptr.emailgenerator.exception.InvalidArgumentException;
import com.stdnullptr.emailgenerator.model.response.ApiResponse;
import com.stdnullptr.emailgenerator.service.EmailGeneratorService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
            @Size(min = 2, message = ERROR_QUERY_SIZE)
            MultiValueMap<String, String> queryParams) {

        // Should contain 1 element, a single 'expression' parameter
        List<String> expressionList = queryParams.remove("expression");

        if (expressionList == null || expressionList.isEmpty()) {
            throw new InvalidArgumentException("The 'expression' query parameter is required.");
        }
        if (expressionList.size() > 1) {
            throw new InvalidArgumentException("Multiple 'expression' query parameters are not allowed.");
        }
        if (queryParams.keySet().stream().anyMatch(key -> !key.startsWith("str"))) {
            throw new InvalidArgumentException("The only allowed input prefix is 'strN'.");
        }
        String expression = expressionList.getFirst().trim();
        if (expression.isEmpty()) {
            throw new InvalidArgumentException("The 'expression' parameter cannot be empty.");
        }

        List<String> generatedEmails = service.generateEmails(queryParams, expression);

        ApiResponse<?> response = ApiResponse.success(generatedEmails);
        return ResponseEntity.ok().body(response);
    }
}
