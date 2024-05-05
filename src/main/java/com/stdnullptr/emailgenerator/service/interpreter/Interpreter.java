package com.stdnullptr.emailgenerator.service.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;

import java.util.stream.Stream;

public class Interpreter {
    public static String evaluate(String fullExpressionString, Context context) {
        StringBuilder result = new StringBuilder();
        String[] expressions = Stream.of(fullExpressionString.split(";")).map(String::trim).toArray(String[]::new);

        for (String expression : expressions) {
            if (Operations.isStringExpression(expression)) {
                Expression<String> exp = ExpressionFactory.createExpression(expression);
                result.append(exp.interpret(context));
            } else {
                throw new InterpreterException("Unknown expression: " + expression);
            }
        }

        return result.toString();
    }
}