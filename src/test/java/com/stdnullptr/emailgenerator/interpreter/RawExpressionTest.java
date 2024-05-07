package com.stdnullptr.emailgenerator.interpreter;

import com.stdnullptr.emailgenerator.util.Util;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RawExpressionTest {

	@Test
	void interpret_ShouldReturnRawValue() {
		Context context = Util.createContext(new HashMap<>());
		String rawValue = "This is a raw value";
		RawExpression expression = new RawExpression(rawValue);

		String result = expression.interpret(context);

		assertEquals(rawValue, result);
	}

	@Test
	void interpret_ShouldReturnEmptyStringWhenValueIsEmpty() {
		Context context = Util.createContext(new HashMap<>());
		String emptyValue = "";
		RawExpression expression = new RawExpression(emptyValue);

		String result = expression.interpret(context);

		assertEquals(emptyValue, result);
	}

	@Test
	void interpret_ShouldReturnProvidedNumericValue() {
		Context context = Util.createContext(new HashMap<>());
		String numericValue = "12345";
		RawExpression expression = new RawExpression(numericValue);

		String result = expression.interpret(context);

		assertEquals(numericValue, result);
	}

	@Test
	void interpret_ShouldReturnSpecialCharacters() {
		Context context = Util.createContext(new HashMap<>());
		String specialChars = "@#$%^&*";
		RawExpression expression = new RawExpression(specialChars);

		String result = expression.interpret(context);

		assertEquals(specialChars, result);
	}
}
