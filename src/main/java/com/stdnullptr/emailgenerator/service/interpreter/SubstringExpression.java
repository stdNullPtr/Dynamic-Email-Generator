package com.stdnullptr.emailgenerator.service.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SubstringExpression implements Expression<String> {
    private final String inputKey;
    private final int startIndex;
    private final int endIndex;

    @Override
    public String interpret(Context ctx) {
        String input = ctx.getValue(inputKey);
        if (input == null) {
            throw new InterpreterException("Input value is null for input key: " + inputKey);
        }

        if (startIndex < 0 || startIndex > input.length()) {
            throw new InterpreterException("Start index out of bounds: " + startIndex);
        }

        int safeEndIndex = Math.min(endIndex, input.length());

        if (safeEndIndex < startIndex) {
            throw new InterpreterException("End index is less than start index: " + endIndex);
        }

        return input.substring(startIndex, safeEndIndex);
    }
}
