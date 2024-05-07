package com.stdnullptr.emailgenerator.service;

import com.stdnullptr.emailgenerator.exception.InvalidArgumentException;
import com.stdnullptr.emailgenerator.interpreter.Context;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmailGeneratorServiceTest {

	private final EmailGeneratorService emailGeneratorService = new EmailGeneratorService();

	@Test
	void generateEmails_ShouldThrowInvalidArgumentExceptionWhenExpressionIsMissing() {
		MultiValueMap<String, String> inputs = new LinkedMultiValueMap<>();
		inputs.add("str1", "test");

		Exception exception = assertThrows(InvalidArgumentException.class, () ->
				emailGeneratorService.generateEmails(inputs)
		);

		assertEquals("An 'expression' string is required.", exception.getMessage());
	}

	@Test
	void generateEmails_ShouldThrowInvalidArgumentExceptionWhenExpressionIsEmpty() {
		MultiValueMap<String, String> inputs = new LinkedMultiValueMap<>();
		inputs.add("expression", "");
		inputs.add("str1", "test");

		Exception exception = assertThrows(InvalidArgumentException.class, () ->
				emailGeneratorService.generateEmails(inputs)
		);

		assertEquals("The 'expression' cannot be empty.", exception.getMessage());
	}

	@Test
	void generateEmails_ShouldProcessValidInputs() {
		MultiValueMap<String, String> inputs = new LinkedMultiValueMap<>();
		inputs.add("expression", "raw(hello)");
		inputs.add("str1", "test");

		List<String> results = emailGeneratorService.generateEmails(inputs);

		assertNotNull(results);
		assertFalse(results.isEmpty());
		assertEquals("hello", results.getFirst());
	}

	@Test
	void parseExpression_ShouldReturnListOfResults() {
		MultiValueMap<String, String> inputs = new LinkedMultiValueMap<>();
		inputs.add("str1", "example");

		List<String> results = emailGeneratorService.parseExpression(inputs, "raw(constant)");

		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals("constant", results.getFirst());
	}

	@Test
	void prepareContexts_ShouldCreateCorrectNumberOfContexts() {
		MultiValueMap<String, String> inputs = new LinkedMultiValueMap<>();
		inputs.add("str1", "value1");
		inputs.add("str1", "value2");
		inputs.add("str2", "value3");

		List<Context> contexts = emailGeneratorService.prepareContexts(inputs);

		assertEquals(2, contexts.size());
	}

	@Test
	void recursiveFlattenMultiValueMap_ShouldProperlyFlattenInputs() {
		MultiValueMap<String, String> inputs = new LinkedMultiValueMap<>();
		inputs.add("str1", "value1");
		inputs.add("str1", "value2");
		inputs.add("str1", "value3");
		inputs.add("str2", "value4");

		List<HashMap<String, String>> results = new ArrayList<>();
		emailGeneratorService.recursiveFlattenMultiValueMap(new ArrayList<>(inputs.keySet()), new HashMap<>(), inputs, results, 0);

		assertEquals(3, results.size());
	}
}
