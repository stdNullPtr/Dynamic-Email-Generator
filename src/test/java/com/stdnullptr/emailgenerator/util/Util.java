package com.stdnullptr.emailgenerator.util;

import com.stdnullptr.emailgenerator.interpreter.Context;

import java.util.Map;

public final class Util {
	private Util() {
		// no sneaky reflections
		throw new IllegalStateException("What are you doing?");
	}

	/**
	 * No reason to mock such a simple object, it's better to just create a real instance for simplicity
	 */
	public static Context createContext(Map<String, String> inputs) {
		return new Context(inputs);
	}
}
