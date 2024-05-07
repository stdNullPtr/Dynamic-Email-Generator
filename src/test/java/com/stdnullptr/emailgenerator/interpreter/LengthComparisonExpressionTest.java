package com.stdnullptr.emailgenerator.interpreter;

import com.stdnullptr.emailgenerator.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LengthComparisonExpressionTest {

	private AutoCloseable mocksCloseable;

	@Mock
	private Expression left;

	@Mock
	private Expression right;

	@Mock
	private Expression action;

	@BeforeEach
	void setUp() {
		mocksCloseable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		mocksCloseable.close();
	}

	@Test
	void interpret_ShouldReturnActionWhenLeftIsLonger() {
		Context context = Util.createContext(new HashMap<>());
		when(left.interpret(context)).thenReturn("longer text");
		when(right.interpret(context)).thenReturn("short");
		when(action.interpret(context)).thenReturn("action result");

		LengthComparisonExpression comparison = new LengthComparisonExpression(left, right, action);

		String result = comparison.interpret(context);

		assertEquals("action result", result);
	}

	@Test
	void interpret_ShouldReturnEmptyWhenRightIsLonger() {
		Context context = Util.createContext(new HashMap<>());
		when(left.interpret(context)).thenReturn("short");
		when(right.interpret(context)).thenReturn("longer text");
		when(action.interpret(context)).thenReturn("action result");

		LengthComparisonExpression comparison = new LengthComparisonExpression(left, right, action);

		String result = comparison.interpret(context);

		assertEquals("", result);
	}

	@Test
	void interpret_ShouldReturnEmptyWhenLengthsAreEqual() {
		Context context = Util.createContext(new HashMap<>());
		when(left.interpret(context)).thenReturn("same length");
		when(right.interpret(context)).thenReturn("same length");
		when(action.interpret(context)).thenReturn("action result");

		LengthComparisonExpression comparison = new LengthComparisonExpression(left, right, action);

		String result = comparison.interpret(context);

		assertEquals("", result);
	}

	@Test
	void interpret_ShouldReturnEmptyWhenBothAreEmpty() {
		Context context = Util.createContext(new HashMap<>());
		when(left.interpret(context)).thenReturn("");
		when(right.interpret(context)).thenReturn("");
		when(action.interpret(context)).thenReturn("action result");

		LengthComparisonExpression comparison = new LengthComparisonExpression(left, right, action);

		String result = comparison.interpret(context);

		assertEquals("", result);
	}

	@Test
	void interpret_ShouldReturnEmptyWhenActionIsEmpty() {
		Context context = Util.createContext(new HashMap<>());
		when(left.interpret(context)).thenReturn("longer text");
		when(right.interpret(context)).thenReturn("short");
		when(action.interpret(context)).thenReturn("");

		LengthComparisonExpression comparison = new LengthComparisonExpression(left, right, action);

		String result = comparison.interpret(context);

		assertEquals("", result);
	}
}
