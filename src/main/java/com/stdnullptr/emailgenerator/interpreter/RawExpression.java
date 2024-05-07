package com.stdnullptr.emailgenerator.interpreter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RawExpression implements Expression {
    private final String value;

    @Override
    public String interpret(Context ctx) {
        return value;
    }
}