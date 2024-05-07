package com.stdnullptr.emailgenerator.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import com.stdnullptr.emailgenerator.util.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InterpreterTest {

	@ParameterizedTest
	@MethodSource("com.stdnullptr.emailgenerator.util.TestData#validInputInterpreterProvider")
	void evaluate_WithValidExpression_ShouldReturnExpectedResult(Map<String, Map<String, String>> testParams) {
		Map<String, String> inputs = testParams.values().stream().findFirst().orElseThrow();
		String expression = Optional.ofNullable(inputs.remove("expression")).orElseThrow();
		String expectedResult = testParams.keySet().stream().findFirst().orElseThrow();

		Context context = Util.createContext(inputs);

		String result = Interpreter.evaluate(expression, context);

		assertEquals(expectedResult, result);
	}

	@Test
	void evaluate_WithInvalidExpression_ShouldThrow() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("str1", "test");
		Context context = Util.createContext(inputs);

		String expression = "first(str1,1);invalid(str1, 2)";

		InterpreterException thrown = assertThrows(InterpreterException.class, () ->
				Interpreter.evaluate(expression, context));

		assertEquals("Unknown expression: invalid(str1, 2)", thrown.getMessage(), "Expected specific message on failure");
	}

	@Test
	void evaluate_WithInvalidInput_ShouldThrow() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("wrong1", "test");
		Context context = Util.createContext(inputs);

		String expression = "first(str1,1)";

		InterpreterException thrown = assertThrows(InterpreterException.class, () ->
				Interpreter.evaluate(expression, context));

		assertEquals("Input value is null for input key: str1", thrown.getMessage(), "Expected specific message on failure");
	}

	@Test
	void evaluate_WithInvalidExpressionParam_ShouldThrow() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("str1", "Ivan");
		Context context = Util.createContext(inputs);

		String expression = "first(str1,str1)";

		InterpreterException thrown = assertThrows(InterpreterException.class, () ->
				Interpreter.evaluate(expression, context));

		assertEquals("Invalid number parameter: For input string: \"str1\"", thrown.getMessage(), "Expected specific message on failure");
	}

	@Test
	void evaluate_WithMalformedExpressionParam_ShouldThrow() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("str1", "Ivan");
		Context context = Util.createContext(inputs);

		String expression = "first(str1,1;raw(test)";

		InterpreterException thrown = assertThrows(InterpreterException.class, () ->
				Interpreter.evaluate(expression, context));

		assertEquals("Expression must start with an operation name and follow with parameters in parentheses.", thrown.getMessage(), "Expected specific message on failure");
	}

	@Test
	void evaluate_WithMissingExpressionParam_ShouldThrow() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("str1", "Ivan");
		Context context = Util.createContext(inputs);

		InterpreterException thrownNull = assertThrows(InterpreterException.class, () ->
				Interpreter.evaluate(null, context));

		InterpreterException thrownEmpty = assertThrows(InterpreterException.class, () ->
				Interpreter.evaluate("", context));

		InterpreterException thrownBlank = assertThrows(InterpreterException.class, () ->
				Interpreter.evaluate(" ", context));

		assertEquals("Expression is empty", thrownNull.getMessage(), "Expected specific message on failure");
		assertEquals("Expression is empty", thrownEmpty.getMessage(), "Expected specific message on failure");
		assertEquals("Expression is empty", thrownBlank.getMessage(), "Expected specific message on failure");
	}

	@Test
	void evaluate_WithMissingInputParam_ShouldThrow() {
		Map<String, String> inputs = new HashMap<>();
		Context context = Util.createContext(inputs);

		String expression = "first(str1,1);raw(test)";

		InterpreterException thrown = assertThrows(InterpreterException.class, () ->
				Interpreter.evaluate(expression, context));

		assertEquals("Input value is null for input key: str1", thrown.getMessage(), "Expected specific message on failure");
	}


}
