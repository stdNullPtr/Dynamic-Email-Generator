package com.stdnullptr.emailgenerator.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

	@Test
	void isOperation_ShouldReturnTrueForValidOperations() {
		assertTrue(Operations.isOperation("first"));
		assertTrue(Operations.isOperation("last"));
		assertTrue(Operations.isOperation("substr"));
		assertTrue(Operations.isOperation("lit"));
		assertTrue(Operations.isOperation("raw"));
		assertTrue(Operations.isOperation("eq"));
		assertTrue(Operations.isOperation("longer"));
	}

	@Test
	void isOperation_ShouldReturnFalseForInvalidOperations() {
		assertFalse(Operations.isOperation("unknown"));
		assertFalse(Operations.isOperation("FIRST")); // Case-sensitive
		assertFalse(Operations.isOperation("Last")); // Case-sensitive
		assertFalse(Operations.isOperation(""));
		assertFalse(Operations.isOperation(null));
	}

	@Test
	void toString_ShouldReturnOperationName() {
		assertEquals("first", Operations.FIRST.toString());
		assertEquals("last", Operations.LAST.toString());
		assertEquals("substr", Operations.SUBSTR.toString());
		assertEquals("lit", Operations.LIT.toString());
		assertEquals("raw", Operations.RAW.toString());
		assertEquals("eq", Operations.EQ.toString());
		assertEquals("longer", Operations.LONGER.toString());
	}
}
