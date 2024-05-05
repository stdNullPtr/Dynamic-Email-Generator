package com.stdnullptr.emailgenerator.service.interpreter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class LiteralExpression implements Expression<String> {
    private final String inputKey;

    @Override
    public String interpret(Context ctx) {
        return ctx.getValue(inputKey);
    }
}