package com.stdnullptr.emailgenerator.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import com.stdnullptr.emailgenerator.util.Util;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LiteralExpressionTest {

	@Test
	void interpret_ShouldReturnInputValueWhenInputIsValid() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", "expected value");
		Context context = Util.createContext(inputs);
		LiteralExpression expression = new LiteralExpression("testKey");

		String result = expression.interpret(context);

		assertEquals("expected value", result);
	}

	@Test
	void interpret_ShouldThrowExceptionWhenInputIsNull() {
		Map<String, String> inputs = new HashMap<>();
		inputs.put("testKey", null);
		Context context = Util.createContext(inputs);
		LiteralExpression expression = new LiteralExpression("testKey");

		InterpreterException thrown = assertThrows(InterpreterException.class, () -> expression.interpret(context));

		assertEquals("Input value is null for input key: testKey", thrown.getMessage());
	}

	@Test
	void interpret_ShouldThrowExceptionWhenInputKeyIsMissing() {
		Map<String, String> inputs = new HashMap<>();
		Context context = Util.createContext(inputs);
		LiteralExpression expression = new LiteralExpression("missingKey");

		InterpreterException thrown = assertThrows(InterpreterException.class, () -> expression.interpret(context));

		assertEquals("Input value is null for input key: missingKey", thrown.getMessage());
	}
}
