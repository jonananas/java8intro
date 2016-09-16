package se.jonananas.teaching;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;

import org.junit.Test;

public class A_LamdbaSyntacticSugar {

	@Test
	public void someLambdas() throws Exception {
		Function<String, Integer> f1 = (String param1) -> {
			return Integer.valueOf(param1);
		};

		Function<String, Integer> f2 = (param1) -> {
			return Integer.valueOf(param1);
		};

		Function<String, Integer> f3 = param1 -> {
			return Integer.valueOf(param1);
		};

		// För one-liners kan vi förenkla ytterligare
		Function<String, Integer> f4 = param1 -> Integer.valueOf(param1);

		// Method reference, när kompilatorn kan matcha input/output
		Function<String, Integer> f5 = Integer::valueOf;

		assertThat(f5.apply("1")).isEqualTo(1);
	}

	// Currying

	// File API
}
