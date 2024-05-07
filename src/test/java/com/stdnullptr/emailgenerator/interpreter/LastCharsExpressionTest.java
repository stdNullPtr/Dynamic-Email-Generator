package com.stdnullptr.emailgenerator.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import com.stdnullptr.emailgenerator.util.Util;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LastCharsExpressionTest {

	@Test
	void interpret_ShouldReturnSubstringWhenInputIsValid() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello world");
		Context context = Util.createContext(inputs);
		LastCharsExpression expression = new LastCharsExpression("testKey", 5);

		String result = expression.interpret(context);

		assertEquals("world", result);
	}

	@Test
	void interpret_ShouldReturnFullStringWhenNumCharsEqualsStringLength() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello");
		Context context = Util.createContext(inputs);
		LastCharsExpression expression = new LastCharsExpression("testKey", 5);

		String result = expression.interpret(context);

		assertEquals("hello", result);
	}

	@Test
	void interpret_ShouldReturnEmptyWhenInputIsEmpty() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "");
		Context context = Util.createContext(inputs);
		LastCharsExpression expression = new LastCharsExpression("testKey", 5);

		String result = expression.interpret(context);

		assertEquals("", result);
	}

	@Test
	void interpret_ShouldReturnStringWhenNumCharsIsGreaterThanStringLength() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello");
		Context context = Util.createContext(inputs);
		LastCharsExpression expression = new LastCharsExpression("testKey", 10);

		String result = expression.interpret(context);

		assertEquals("hello", result);
	}

	@Test
	void interpret_ShouldThrowExceptionWhenInputIsNull() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", null);
		Context context = Util.createContext(inputs);
		LastCharsExpression expression = new LastCharsExpression("testKey", 5);

		InterpreterException thrown = assertThrows(InterpreterException.class, () -> expression.interpret(context));

		assertEquals("Input value is null for input key: testKey", thrown.getMessage());
	}

	@Test
	void interpret_ShouldThrowExceptionWhenNumCharactersIsNegative() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello world");
		Context context = Util.createContext(inputs);
		LastCharsExpression expression = new LastCharsExpression("testKey", -1);

		InterpreterException thrown = assertThrows(InterpreterException.class, () -> expression.interpret(context));

		assertEquals("Number of characters cannot be negative: -1", thrown.getMessage());
	}

	@Test
	void interpret_ShouldIgnoreIrrelevantContextEntries() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello world");
		inputs.put("irrelevantKey", "irrelevantValue");
		Context context = Util.createContext(inputs);
		LastCharsExpression expression = new LastCharsExpression("testKey", 5);

		String result = expression.interpret(context);

		assertEquals("world", result);
	}
}
