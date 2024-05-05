package com.stdnullptr.emailgenerator.service.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InterpreterService {

    public String evaluate(String fullExpressionString, Context context) {
        StringBuilder result = new StringBuilder();
        String[] expressions = Stream.of(fullExpressionString.split(";")).map(String::trim).toArray(String[]::new);

        for (String expression : expressions) {
            if (Operations.isOperation(expression)) {
                Expression exp = ExpressionFactory.createExpression(expression);
                result.append(exp.interpret(context));
            } else {
                throw new InterpreterException("Unknown expression: " + expression);
            }
        }

        return result.toString();
    }
}