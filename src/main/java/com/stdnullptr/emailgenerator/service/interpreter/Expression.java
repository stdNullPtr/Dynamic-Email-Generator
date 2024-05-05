package com.stdnullptr.emailgenerator.service.interpreter;

interface Expression<T> {
    T interpret(Context ctx);
}