package se.jonananas.teaching.streamingslides;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

public class D_LambdaHigherOrderFunctions {

	@Test
	public void returnAFunction() {

		assertThat(startarMedA().test("apa")).isTrue();
	}

	private Predicate<String> startarMedA() {
		return str -> str.startsWith("a");
	}

	@Test
	public void functionAsParameter() {

		Function<Supplier<String>, String> quote = supplier -> "\"" + supplier.get() + "\"";

		Supplier<String> readText = () -> "read from a very complex place";

		assertThat(quote.apply(readText)).isEqualTo("\"read from a very complex place\"");
	};

	@Test
	public void staticMethodAsParameter() {

		Function<Supplier<String>, String> quote = supplier -> "\"" + supplier.get() + "\"";

		assertThat(quote.apply(D_LambdaHigherOrderFunctions::readTextStaticMethod)).isEqualTo("\"read from a very complex place\"");
	};

	public static String readTextStaticMethod() {
		return "read from a very complex place";
	}

	@Test
	public void methodAsParameter() {

		Function<Supplier<String>, String> quote = supplier -> "\"" + supplier.get() + "\"";

		assertThat(quote.apply(this::readTextMethod)).isEqualTo("\"read from a very complex place\"");
	};

	public String readTextMethod() {
		return "read from a very complex place";
	}
}
