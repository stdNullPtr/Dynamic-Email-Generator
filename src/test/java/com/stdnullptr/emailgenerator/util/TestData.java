package com.stdnullptr.emailgenerator.util;

import com.stdnullptr.emailgenerator.exception.GlobalExceptionHandler;
import com.stdnullptr.emailgenerator.interpreter.Interpreter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public final class TestData {
	private TestData() {
		// no sneaky reflections
		throw new IllegalStateException("What are you doing?");
	}

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
	 * The purpose of this provider is to deliver query parameters that break the {@link Interpreter},
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

	/**
	 * The purpose of this provider is to deliver valid input parameters for the {@link Interpreter}
	 */
	public static Stream<Map<String, Map<String, String>>> validInputInterpreterProvider() {
		return Stream.of(
				createInterpreterTestEntry("I.Millenium@gmaium.com", new HashMap<>() {{
					put("expression", "first(str1,1);raw(.);lit(str2);raw(@);first(str3, 3);last(str2, 3);raw(.);lit(str4)");
					put("str1", "Ivan");
					put("str2", "Millenium");
					put("str3", "gmail");
					put("str4", "com");
				}}),
				createInterpreterTestEntry("I.Ivanov@gmail.com", new HashMap<>() {{
					put("expression", "first(str1,1);raw(.);lit(str2);raw(@gmail.com)");
					put("str1", "Ivan");
					put("str2", "Ivanov");
				}}),
				createInterpreterTestEntry("MilleniumI.Millenium@gmaium.bg", new HashMap<>() {{
					put("expression", "eq(first(str1, 2),raw(Iv),lit(str2));first(str1,1);raw(.);lit(str2);raw(@);first(str3, 3);last(str2, 3);raw(.);lit(str4)");
					put("str1", "Ivan");
					put("str2", "Millenium");
					put("str3", "gmail");
					put("str4", "bg");
				}}),
				createInterpreterTestEntry("vanMillenium@gmail.com", new HashMap<>() {{
					put("expression", "last(str1, 3);eq(first(str1, 2),raw(Iv),lit(str2));raw(@gmail.com)");
					put("str1", "Ivan");
					put("str2", "Millenium");
				}}),
				createInterpreterTestEntry("Prtyahoo.Falcon@yahyahoocon.net", new HashMap<>() {{
					put("expression", "first(str1,1);last(str1,1);substr(str1, 2, 3);longer(lit(str2),lit(str1),lit(str3));raw(.);lit(str2);raw(@);first(str3, 3);eq(lit(str1),raw(Petar),lit(str3));last(str2, 3);raw(.);lit(str4)");
					put("str1", "Petar");
					put("str2", "Falcon");
					put("str3", "yahoo");
					put("str4", "net");
				}}),
				createInterpreterTestEntry("Pet@gmail.com", new HashMap<>() {{
					put("expression", "first(str1,3);raw(@gmail.com)");
					put("str1", "Petar");
				}}),
				createInterpreterTestEntry("tar@gmail.com", new HashMap<>() {{
					put("expression", "last(str1,3);raw(@gmail.com)");
					put("str1", "Petar");
				}}),
				createInterpreterTestEntry("ar@gmail.com", new HashMap<>() {{
					put("expression", "substr(str1,3,5);raw(@gmail.com)");
					put("str1", "Petar");
				}}),
				createInterpreterTestEntry("Rawtest@gmail.com", new HashMap<>() {{
					put("expression", "raw(Rawtest);raw(@gmail.com)");
					put("str1", "Petar");
				}}),
				createInterpreterTestEntry("Petar@gmail.com", new HashMap<>() {{
					put("expression", "lit(str1);raw(@gmail.com)");
					put("str1", "Petar");
				}}),
				createInterpreterTestEntry("Petar@gmail.com", new HashMap<>() {{
					put("expression", "longer(lit(str1),lit(str2),lit(str1));raw(@gmail.com)");
					put("str1", "Petar");
					put("str2", "Ivo");
				}}),
				createInterpreterTestEntry("lolnice@gmail.com", new HashMap<>() {{
					put("expression", "eq(first(str1,2),first(str1,2),raw(lol));raw(nice);raw(@gmail.com)");
					put("str1", "Ivan");
					put("str2", "Ivo");
				}})
		);
	}

	private static Map<String, MultiValueMap<String, String>> createErrorTestEntry(String expectedErrorMessage, MultiValueMap<String, String> queryParams) {
		Map<String, MultiValueMap<String, String>> testEntry = new HashMap<>();
		testEntry.put(expectedErrorMessage, queryParams);
		return testEntry;
	}

	private static Map<String, Map<String, String>> createInterpreterTestEntry(String expectedResult, Map<String, String> inputs) {
		Map<String, Map<String, String>> testEntry = new HashMap<>();
		testEntry.put(expectedResult, inputs);
		return testEntry;
	}
}

