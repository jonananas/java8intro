package se.jonananas.teaching;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

public class B_LamdbaSyntacticSugar {

	@Test
	public void yLambda() {
		Function<Integer, Integer> doubleIt = y -> y + y;

		assertThat(doubleIt.apply(3)).isEqualTo(6);
	}

	@Test
	public void yLambdaLonger() {
		Function<Integer, Integer> doubleIt = (Integer y) -> {
			return y + y;
		};

		assertThat(doubleIt.apply(3)).isEqualTo(6);
	}

	@Test
	public void sameLambdaDifferentLook() {

		Function<String, Integer> stringToInt1 = (String param1) -> {
			return Integer.valueOf(param1);
		};

		Function<String, Integer> stringToInt2 = (param1) -> {
			return Integer.valueOf(param1);
		};

		Function<String, Integer> stringToInt3 = param1 -> {
			return Integer.valueOf(param1);
		};

		// Endast one-liners
		Function<String, Integer> stringToInt4 = param1 -> Integer.valueOf(param1);

		assertThat(stringToInt1.apply("1")).isEqualTo(stringToInt2.apply("1"))
				.isEqualTo(stringToInt3.apply("1"))
				.isEqualTo(stringToInt4.apply("1"))
				.isEqualTo(1);
	}

	@Test
	public void methodReference() {
		Function<String, Integer> stringToInt = Integer::valueOf;

		assertThat(stringToInt.apply("1")).isEqualTo(1);
	}

	@Test
	public void methodReferenceToBiFunction() {
		BiFunction<String, Integer, Double> toDouble = B_LamdbaSyntacticSugar::toDoubleStat;

		assertThat(toDouble.apply("1.3", 4)).isEqualTo(5.3);
	}

	public static Double toDoubleStat(String s, Integer i) {
		return Double.valueOf(s) + i;
	}

}
