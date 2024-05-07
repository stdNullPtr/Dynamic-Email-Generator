package com.stdnullptr.emailgenerator.interpreter;

import com.stdnullptr.emailgenerator.exception.InterpreterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionFactoryTest {

	@Test
	void createExpression_ShouldCreateFirstCharsExpression() {
		String expressionStr = "first(testKey, 5)";
		Expression expression = ExpressionFactory.createExpression(expressionStr);

		assertInstanceOf(FirstCharsExpression.class, expression);
	}

	@Test
	void createExpression_ShouldCreateLastCharsExpression() {
		String expressionStr = "last(testKey, 3)";
		Expression expression = ExpressionFactory.createExpression(expressionStr);

		assertInstanceOf(LastCharsExpression.class, expression);
	}

	@Test
	void createExpression_ShouldCreateLiteralExpression() {
		String expressionStr = "lit(testKey)";
		Expression expression = ExpressionFactory.createExpression(expressionStr);

		assertInstanceOf(LiteralExpression.class, expression);
	}

	@Test
	void createExpression_ShouldCreateRawExpression() {
		String expressionStr = "raw(constant value)";
		Expression expression = ExpressionFactory.createExpression(expressionStr);

		assertInstanceOf(RawExpression.class, expression);
	}

	@Test
	void createExpression_ShouldCreateSubstringExpression() {
		String expressionStr = "substr(testKey, 2, 5)";
		Expression expression = ExpressionFactory.createExpression(expressionStr);

		assertInstanceOf(SubstringExpression.class, expression);
	}

	@Test
	void createExpression_ShouldCreateEqualityExpression() {
		String expressionStr = "eq(lit(val1), lit(val2), raw(result))";
		Expression expression = ExpressionFactory.createExpression(expressionStr);

		assertInstanceOf(EqualityExpression.class, expression);
	}

	@Test
	void createExpression_ShouldCreateLengthComparisonExpression() {
		String expressionStr = "longer(lit(long), lit(short), raw(result))";
		Expression expression = ExpressionFactory.createExpression(expressionStr);

		assertInstanceOf(LengthComparisonExpression.class, expression);
	}

	@Test
	void createExpression_ShouldThrowExceptionForInvalidFormat() {
		String expressionStr = "invalidExpression";

		InterpreterException exception = assertThrows(InterpreterException.class, () -> ExpressionFactory.createExpression(expressionStr));

		assertEquals("Expression must start with an operation name and follow with parameters in parentheses.", exception.getMessage());
	}

	@Test
	void createExpression_ShouldThrowExceptionForUnknownOperation() {
		String expressionStr = "unknownOp(testKey, 5)";

		InterpreterException exception = assertThrows(InterpreterException.class, () -> ExpressionFactory.createExpression(expressionStr));

		assertEquals("Unknown operation: unknownOp", exception.getMessage());
	}

	@Test
	void createExpression_ShouldThrowExceptionForInvalidNumberParameter() {
		String expressionStr = "first(testKey, invalidNumber)";

		InterpreterException exception = assertThrows(InterpreterException.class, () -> ExpressionFactory.createExpression(expressionStr));

		assertTrue(exception.getMessage().contains("Invalid number parameter"));
	}

	@Test
	void createExpression_ShouldThrowExceptionForEmptyParameters() {
		String expressionStr = "first()";

		InterpreterException exception = assertThrows(InterpreterException.class, () -> ExpressionFactory.createExpression(expressionStr));

		assertEquals("Expression has no parameters", exception.getMessage());
	}
}
