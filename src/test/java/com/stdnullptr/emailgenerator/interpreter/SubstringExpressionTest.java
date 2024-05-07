package com.stdnullptr.emailgenerator.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import com.stdnullptr.emailgenerator.util.Util;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubstringExpressionTest {

	@Test
	void interpret_ShouldReturnSubstringWhenInputIsValid() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello world");
		Context context = Util.createContext(inputs);
		SubstringExpression expression = new SubstringExpression("testKey", 0, 5);

		String result = expression.interpret(context);

		assertEquals("hello", result);
	}

	@Test
	void interpret_ShouldReturnSubstringWhenEndIndexExceedsInputLength() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello");
		Context context = Util.createContext(inputs);
		SubstringExpression expression = new SubstringExpression("testKey", 1, 10);

		String result = expression.interpret(context);

		assertEquals("ello", result);
	}

	@Test
	void interpret_ShouldReturnEmptyStringWhenStartIndexEqualsEndIndex() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello world");
		Context context = Util.createContext(inputs);
		SubstringExpression expression = new SubstringExpression("testKey", 5, 5);

		String result = expression.interpret(context);

		assertEquals("", result);
	}

	@Test
	void interpret_ShouldThrowExceptionWhenInputIsNull() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", null);
		Context context = Util.createContext(inputs);
		SubstringExpression expression = new SubstringExpression("testKey", 0, 5);

		InterpreterException thrown = assertThrows(InterpreterException.class, () -> expression.interpret(context));

		assertEquals("Input value is null for input key: testKey", thrown.getMessage());
	}

	@Test
	void interpret_ShouldThrowExceptionWhenStartIndexIsOutOfBounds() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello");
		Context context = Util.createContext(inputs);
		SubstringExpression expression = new SubstringExpression("testKey", 10, 15);

		InterpreterException thrown = assertThrows(InterpreterException.class, () -> expression.interpret(context));

		assertEquals("Start index out of bounds for substring expression, index: 10", thrown.getMessage());
	}

	@Test
	void interpret_ShouldThrowExceptionWhenEndIndexIsLessThanStartIndex() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "hello");
		Context context = Util.createContext(inputs);
		SubstringExpression expression = new SubstringExpression("testKey", 5, 3);

		InterpreterException thrown = assertThrows(InterpreterException.class, () -> expression.interpret(context));

		assertEquals("End index is less than start index: 3", thrown.getMessage());
	}
}
