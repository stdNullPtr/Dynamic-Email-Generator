package com.stdnullptr.emailgenerator.service.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class FirstCharsExpression implements Expression {
    private final String inputKey;
    private final int numCharacters;

    @Override
    public String interpret(Context ctx) {
        String input = ctx.getValue(inputKey);

        if (input == null) {
            throw new InterpreterException("Input value is null for input key: " + inputKey);
        }

        if (numCharacters < 0) {
            throw new InterpreterException("Number of characters cannot be negative: " + numCharacters);
        }

        int endIndex = Math.min(numCharacters, input.length());
        return input.substring(0, endIndex);
    }
}
