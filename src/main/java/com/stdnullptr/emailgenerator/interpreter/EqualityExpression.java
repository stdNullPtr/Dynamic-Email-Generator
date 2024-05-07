package com.stdnullptr.emailgenerator.interpreter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EqualityExpression implements Expression {
    private final Expression left;
    private final Expression right;
    private final Expression action;

    @Override
    public String interpret(Context ctx) {
        if (left.interpret(ctx).equals(right.interpret(ctx))){
            return action.interpret(ctx);
        }
        return ""; // If we did not meet the condition, just return an empty string
    }
}
