package com.stdnullptr.emailgenerator.service.interpreter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
enum Operations {
    FIRST("first"),
    LAST("last"),
    SUBSTR("substr"),
    LIT("lit"),
    RAW("raw"),
    EQ("eq"),
    LONGER("longer");

    private final String operationName;

    public static boolean isOperation(String operationName) {
        return Arrays.stream(Operations.values()).anyMatch(op -> operationName.startsWith(op.toString()));
    }

    @Override
    public String toString() {
        return this.operationName;
    }
}

