package com.stdnullptr.emailgenerator.service.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class LiteralExpression implements Expression {
    private final String inputKey;

    @Override
    public String interpret(Context ctx) {
        String value = ctx.getValue(inputKey);
        if (value == null) {
            throw new InterpreterException("Input value is null for input key: " + inputKey);
        }
        return value;
    }
}
