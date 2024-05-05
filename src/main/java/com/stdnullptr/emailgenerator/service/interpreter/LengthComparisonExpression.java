package com.stdnullptr.emailgenerator.service.interpreter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class LengthComparisonExpression implements Expression {
    private final Expression left;
    private final Expression right;
    private final Expression action;

    @Override
    public String interpret(Context ctx) {
        if (left.interpret(ctx).length() > right.interpret(ctx).length()) {
            return action.interpret(ctx);
        }
        return ""; // If we did not meet the condition, just return an empty string
    }
}