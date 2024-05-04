package com.stdnullptr.emailgenerator.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.stream.Stream;

@SuppressWarnings("unused")
public abstract class TestUtils {
    private TestUtils() {
        // no sneaky reflections
        throw new IllegalStateException("What are you doing?");
    }

    /**
     * Encapsulate providers since they are strictly specific to individual test cases, don't pollute
     * TestUtils scope with these methods
     */
    @SuppressWarnings("unused")
    public static abstract class TestParamsProvider{
        /**
         * The idea of this provider is to deliver too few query parameters, logically they can't be less than 2:
         * at least one input and exactly one expression.
         */
        public static Stream<MultiValueMap<String, String>> tooFewQueryParamsProvider() {
            return Stream.of(
                    new LinkedMultiValueMap<>() {{
                        add("expression", "anystr");
                    }},
                    new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                    }},
                    new LinkedMultiValueMap<>() {{
                        add("str1", "Millennium");
                        add("str1", "Fal");
                        add("str1", "con");
                        add("str1", "");
                    }}
            );
        }
    }
}
