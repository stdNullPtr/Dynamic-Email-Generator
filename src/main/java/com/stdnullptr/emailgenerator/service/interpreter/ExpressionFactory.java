package com.stdnullptr.emailgenerator.service.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
class ExpressionFactory {

    static Expression<String> createExpression(String expression) {
        String[] tokens = Stream.of(
                expression.split("[(),]")).filter(w -> !w.isEmpty()).map(String::trim).toArray(String[]::new);

        if (tokens.length < 2) {
            log.error("Invalid expression, too few tokens after parsing: {}", expression);
            throw new InterpreterException("Failed to parse expression");
        }

        Operations operation = Operations.valueOf(tokens[0].toUpperCase());
        String firstParam = tokens[1];

        return switch (operation) {
            case FIRST -> new FirstCharsExpression(firstParam, Integer.parseInt(tokens[2]));
            case LAST -> new LastCharsExpression(firstParam, Integer.parseInt(tokens[2]));
            case LIT -> new LiteralExpression(firstParam);
            case RAW -> new RawExpression(firstParam);
            case SUBSTR ->
                    new SubstringExpression(firstParam, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        };
    }
}
