package com.stdnullptr.emailgenerator.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ApiException {
    private final String message;
    private final String details;
}
