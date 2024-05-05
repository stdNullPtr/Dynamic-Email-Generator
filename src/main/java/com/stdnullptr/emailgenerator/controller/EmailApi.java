package com.stdnullptr.emailgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * The purpose of this class is:<p>
 * <ul>
 * <li>To hold API description details, removing bloat from the implementation
 * <li>To define validation rules and error messages, again removing bloat from the implementation details.
 * </ul>
 */
@Tag(name = "Email", description = "Email generator API")
public abstract class EmailApi {
    protected static final String ERROR_QUERY_EMPTY = "Query parameters cannot be empty";

    @Operation(
            summary = "Generate email addresses using a custom expression language, passed via query parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "invalid request parameters")
    })
    @SuppressWarnings("unused")
    abstract ResponseEntity<com.stdnullptr.emailgenerator.model.response.ApiResponse<?>> generateEmails(
            @NotEmpty(message = ERROR_QUERY_EMPTY)
            MultiValueMap<String, String> queryParams);
}
