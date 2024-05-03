package com.stdnullptr.emailgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Email", description = "email generator API")
public interface EmailApi {
    @Operation(
            summary = "Generate email addresses using a custom expression language")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    ResponseEntity<List<String>> generateEmails(@NotEmpty(message = "Query parameters must be provided") Map<String, String> queryParams);
}
