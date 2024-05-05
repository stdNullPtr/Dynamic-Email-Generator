package com.stdnullptr.emailgenerator.service.interpreter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RawExpression implements Expression<String> {
    private final String value;

    @Override
    public String interpret(Context ctx) {
        return value;
    }
}