package com.stdnullptr.emailgenerator.exception;

import com.stdnullptr.emailgenerator.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<String>> handleMethodValidationException(HandlerMethodValidationException ex) {
        log.error("Validation exception occurred", ex);
        Map<String, List<String>> errors = new HashMap<>();

        ex.getAllValidationResults().forEach(result ->
                errors.put(
                        result.getMethodParameter().getParameterName(),
                        result.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList()));

        ApiResponse<String> response = ApiResponse.error("Invalid request argument(s): ".concat(errors.toString()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidArgumentExceptions(InvalidArgumentException ex) {
        log.error("Invalid argument exception occurred", ex);
        ApiResponse<String> response = ApiResponse.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Wrap all unhandled exceptions, prevent internal information leakage to responses, like stack traces
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAllExceptions(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        ApiResponse<String> response = ApiResponse.error("Unhandled exception occurred, it has been logged.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
