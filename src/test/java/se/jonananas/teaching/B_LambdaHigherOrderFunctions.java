package se.jonananas.teaching;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

public class B_LambdaHigherOrderFunctions {
	
	@Test
	public void returnAFunction() throws Exception {
	
		Predicate<String> startarMedA1 = (String str) -> {
			return str.startsWith("a");
		};

		Predicate<String> startarMedA2 = str -> str.startsWith("a");

		assertThat(startarMedA2.test("apa"))
			.isEqualTo(startarMedA1.test("apa"))
			.isTrue();
	}

	@Test
	public void functionAsParameter() throws Exception {
		Function<Supplier<String>, String> quote1 = (Supplier<String> supplier) -> {
			return "\"" + supplier.get() + "\"";
		};
		Function<Supplier<String>, String> quote2 = supplier -> "\"" + supplier.get() + "\"";
		
		Supplier<String> readText = () -> "read from a very complex place";
		
		assertThat(quote1.apply(readText))
			.isEqualTo(quote2.apply(readText))
			.isEqualTo("\"read from a very complex place\"");
	};


}
