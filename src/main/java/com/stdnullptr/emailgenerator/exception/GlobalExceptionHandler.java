package com.stdnullptr.emailgenerator.exception;

import com.stdnullptr.emailgenerator.model.ApiException;
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
    public ResponseEntity<ApiException> handleMethodValidationException(HandlerMethodValidationException ex) {
        log.error("Exception occurred", ex);
        Map<String, List<String>> errors = new HashMap<>();
        ex.getAllValidationResults().forEach(result ->
                errors.put(result.getMethodParameter().getParameterName(), result.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList()));

        ApiException apiException = new ApiException("Invalid request argument(s)", errors.toString());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    // Wrap all unhandled exceptions, prevent internal information leakage to responses, like stacktraces
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleAllExceptions(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        ApiException apiException = new ApiException("Unhandled exception occurred, it has been logged.", null);
        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
