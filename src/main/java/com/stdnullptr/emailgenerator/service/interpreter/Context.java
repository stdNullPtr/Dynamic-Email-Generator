package com.stdnullptr.emailgenerator.service.interpreter;

import java.util.Map;

public class Context {
    private final Map<String, String> inputs;

    public Context(Map<String, String> inputs) {
        this.inputs = inputs;
    }

    public String getValue(String key) {
        return inputs.get(key);
    }
}