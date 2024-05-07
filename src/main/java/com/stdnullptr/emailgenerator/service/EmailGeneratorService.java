package com.stdnullptr.emailgenerator.service;

import com.stdnullptr.emailgenerator.exception.InvalidArgumentException;
import com.stdnullptr.emailgenerator.interpreter.Context;
import com.stdnullptr.emailgenerator.interpreter.Interpreter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailGeneratorService {
    public List<String> generateEmails(MultiValueMap<String, String> inputs) {
        log.info("Generating emails for input {}", inputs);

        // Should contain 1 element, a single 'expression' parameter
        List<String> expressionList = inputs.remove("expression");

        if (expressionList == null || expressionList.isEmpty()) {
            throw new InvalidArgumentException("An 'expression' string is required.");
        }
        if (expressionList.size() > 1) {
            throw new InvalidArgumentException("Multiple 'expression' strings are not allowed.");
        }
        if (inputs.isEmpty()) {
            throw new InvalidArgumentException("At least one input is required.");
        }
        if (inputs.keySet().stream().anyMatch(key -> !key.startsWith("str"))) {
            throw new InvalidArgumentException("The only allowed input prefix is 'str' followed by a number.");
        }
        String expression = expressionList.getFirst().trim();
        if (expression.isEmpty()) {
            throw new InvalidArgumentException("The 'expression' cannot be empty.");
        }

        return parseExpression(inputs, expression);
    }

    public List<String> parseExpression(MultiValueMap<String, String> inputs, String expression) {
        List<String> results = new ArrayList<>();

        List<Context> allContexts = prepareContexts(inputs);

        for (Context context : allContexts) {
            String evaluatedExpression = Interpreter.evaluate(expression, context);
            results.add(evaluatedExpression);
        }

        return results;
    }

    public List<Context> prepareContexts(MultiValueMap<String, String> inputs) {
        List<Context> contexts = new ArrayList<>();

        List<HashMap<String, String>> results = new ArrayList<>();
        recursiveFlattenMultiValueMap(new ArrayList<>(inputs.keySet()), new HashMap<>(), inputs, results, 0);

        results.forEach(c -> contexts.add(new Context(c)));

        return contexts;
    }

    /***
     * TODO Potential issues with recursion, but it is way cleaner than iterative approach
     */
    public void recursiveFlattenMultiValueMap(List<String> keys, HashMap<String, String> current, MultiValueMap<String, String> originalInputs, List<HashMap<String, String>> results, int depth) {
        if (depth == keys.size()) {
            results.add(new HashMap<>(current));
            return;
        }

        String key = keys.get(depth);
        Collection<String> values = originalInputs.get(key);
        for (String value : values) {
            current.put(key, value);
            recursiveFlattenMultiValueMap(keys, current, originalInputs, results, depth + 1);
        }
        current.remove(key);
    }
}
