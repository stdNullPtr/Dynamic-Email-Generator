package com.stdnullptr.emailgenerator.service.interpreter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class LastCharsExpression implements Expression<String> {
    private final String inputKey;
    private final int numCharacters;

    @Override
    public String interpret(Context ctx) {
        String input = ctx.getValue(inputKey);
        int start = Math.max(0, input.length() - numCharacters);
        return input.substring(start);
    }
}