package com.stdnullptr.emailgenerator.service.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class ExpressionFactory {

    static Expression createExpression(String expression) {
        int firstParen = expression.indexOf('(');
        if (firstParen == -1 || !expression.endsWith(")")) {
            log.error("Invalid expression format: {}", expression);
            throw new InterpreterException("Expression must start with an operation name and follow with parameters in parentheses.");
        }

        String operationName = expression.substring(0, firstParen).trim();
        String parameterString = expression.substring(firstParen + 1, expression.length() - 1).trim();
        List<String> parameters = parseParameters(parameterString);
        if (parameters.isEmpty()) {
            throw new InterpreterException("Expression has no parameters");
        }

        Operations operation = Operations.valueOf(operationName.toUpperCase());

        return switch (operation) {
            case FIRST -> new FirstCharsExpression(parameters.get(0), Integer.parseInt(parameters.get(1)));
            case LAST -> new LastCharsExpression(parameters.get(0), Integer.parseInt(parameters.get(1)));
            case LIT -> new LiteralExpression(parameters.get(0));
            case RAW -> new RawExpression(parameters.get(0));
            case SUBSTR ->
                    new SubstringExpression(parameters.get(0), Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
            case EQ -> new EqualityExpression(
                    createExpression(parameters.get(0)),
                    createExpression(parameters.get(1)),
                    createExpression(parameters.get(2)));
            case LONGER -> new LengthComparisonExpression(
                    createExpression(parameters.get(0)),
                    createExpression(parameters.get(1)),
                    createExpression(parameters.get(2))
            );
        };
    }

    private static List<String> parseParameters(String params) {
        List<String> resultParameters = new ArrayList<>();
        int balance = 0;
        StringBuilder currentParam = new StringBuilder();

        for (char c : params.toCharArray()) {
            switch (c) {
                case '(':
                    balance++;
                    currentParam.append(c);
                    break;
                case ')':
                    balance--;
                    currentParam.append(c);
                    break;
                case ',':
                    if (balance == 0) {
                        resultParameters.add(currentParam.toString().trim());
                        currentParam.setLength(0);
                    } else {
                        currentParam.append(c);
                    }
                    break;
                default:
                    currentParam.append(c);
            }
        }

        if (!currentParam.isEmpty()) {
            resultParameters.add(currentParam.toString().trim());
        }

        return resultParameters;
    }
}
