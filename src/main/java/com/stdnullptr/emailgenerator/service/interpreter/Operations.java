package com.stdnullptr.emailgenerator.service.interpreter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;

@Getter
@RequiredArgsConstructor
public enum Operations {
    FIRST("first"),
    LAST("last"),
    SUBSTR("substr"),
    LIT("lit"),
    RAW("raw");

    private static final EnumSet<Operations> stringOperations =
            EnumSet.of(FIRST, LAST, SUBSTR, LIT, RAW);

    private final String operationName;

    public static boolean isStringExpression(String expr) {
        return stringOperations.stream().anyMatch(op -> expr.startsWith(op.toString()));
    }

    @Override
    public String toString() {
        return this.operationName;
    }
}

