package com.stdnullptr.emailgenerator.service.interpreter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class FirstCharsExpression implements Expression<String> {
    private final String inputKey;
    private final int numCharacters;

    @Override
    public String interpret(Context ctx) {
        String input = ctx.getValue(inputKey);
        return input.substring(0, Math.min(numCharacters, input.length()));
    }
}