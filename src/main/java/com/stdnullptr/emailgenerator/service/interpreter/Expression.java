package com.stdnullptr.emailgenerator.service.interpreter;

interface Expression {
    String interpret(Context ctx);
}