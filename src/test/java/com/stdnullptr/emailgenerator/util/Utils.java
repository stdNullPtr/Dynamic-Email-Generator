package com.stdnullptr.emailgenerator.util;

import com.stdnullptr.emailgenerator.exception.GlobalExceptionHandler;
import com.stdnullptr.emailgenerator.service.interpreter.InterpreterService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public final class Utils {
    private Utils() {
        // no sneaky reflections
        throw new IllegalStateException("What are you doing?");
    }

    /**
     * Encapsulate providers since they are strictly specific to individual test cases, don't pollute
     * Utils scope with these methods
     */
    public static abstract class TestParamsProvider {
        /**
         * The purpose of this provider is to deliver invalid query parameters that should fail early,
         * confirming that {@link GlobalExceptionHandler} is properly catching controller exceptions
         */
        public static Stream<Map<String, MultiValueMap<String, String>>> invalidArgumentQueryParamsProvider() {
            return Stream.of(
                    createErrorTestEntry("The only allowed input prefix is 'str' followed by a number.", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "anyExpr");
                        add("invalid2", "lol");
                    }}),
                    createErrorTestEntry("Multiple 'expression' strings are not allowed.", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("str2", "Falcon");
                        add("expression", "first(str1,2)");
                        add("expression", "last(str1,2)");
                    }}),
                    createErrorTestEntry("An 'expression' string is required.", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                    }}),
                    createErrorTestEntry("At least one input is required.", new LinkedMultiValueMap<>() {{
                        add("expression", "first(str1,2)");
                    }}),
                    createErrorTestEntry("The 'expression' cannot be empty.", new LinkedMultiValueMap<>() {{
                        add("expression", " ");
                        add("str1", "Millennium");
                        add("str2", "Falcon");
                    }})
            );
        }

        /**
         * The purpose of this provider is to deliver query parameters that break the {@link InterpreterService},
         * so that we can confirm that {@link GlobalExceptionHandler} is properly catching controller exceptions
         */
        public static Stream<Map<String, MultiValueMap<String, String>>> interpreterErrorQueryParamsProvider() {
            return Stream.of(
                    createErrorTestEntry("Unknown expression: unknown(str1)", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "unknown(str1)");
                    }}),
                    createErrorTestEntry("Expression has no parameters", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "first()");
                    }}),
                    createErrorTestEntry("Expression must start with an operation name and follow with parameters in parentheses.", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "first(");
                    }}),
                    createErrorTestEntry("Expression must start with an operation name and follow with parameters in parentheses.", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "first(str1, Millennium");
                    }}),
                    createErrorTestEntry("Expression must start with an operation name and follow with parameters in parentheses.", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "first)");
                    }}),
                    createErrorTestEntry("Input value is null for input key: str2", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "first(str2, 1)");
                    }}),
                    createErrorTestEntry("Input value is null for input key: str2", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "last(str2, 1)");
                    }}),
                    createErrorTestEntry("Input value is null for input key: str2", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "substr(str2, 1, 2)");
                    }}),
                    createErrorTestEntry("Input value is null for input key: str2", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "lit(str2)");
                    }}),
                    createErrorTestEntry("Number of characters cannot be negative: -1", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "first(str1, -1)");
                    }}),
                    createErrorTestEntry("Number of characters cannot be negative: -1", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "last(str1, -1)");
                    }}),
                    createErrorTestEntry("Start index out of bounds for substring expression, index: -1", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "substr(str1, -1, 1)");
                    }}),
                    createErrorTestEntry("Start index out of bounds for substring expression, index: 11", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "substr(str1, 11, 12)");
                    }}),
                    createErrorTestEntry("End index is less than start index: 1", new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("expression", "substr(str1, 2, 1)");
                    }})
            );
        }

        private static Map<String, MultiValueMap<String, String>> createErrorTestEntry(String expectedErrorMessage, MultiValueMap<String, String> queryParams) {
            Map<String, MultiValueMap<String, String>> testEntry = new HashMap<>();
            testEntry.put(expectedErrorMessage, queryParams);
            return testEntry;
        }
    }
}
