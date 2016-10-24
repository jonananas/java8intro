package se.jonananas.teaching.streamingslides;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

import org.junit.Test;

public class C_CombiningPartialAndCurrying {

	@Test
	public void combiningPredicates() {
		Predicate<String> startsWithUpperCase = str -> Character.isUpperCase(str.charAt(0));
		Predicate<String> endsWithLowerCase = str -> str.substring(1).toLowerCase().equals(str.substring(1));
		Predicate<String> startsWithUpperContinuesWithLower = startsWithUpperCase.and(endsWithLowerCase);

		assertThat(startsWithUpperContinuesWithLower.test("Apa")).isTrue();
		assertThat(startsWithUpperContinuesWithLower.test("apa")).isFalse();
		assertThat(startsWithUpperContinuesWithLower.test("ApA")).isFalse();
	}

	@Test
	public void combiningFunctions() {

		Function<String, Integer> stringToInt = Integer::valueOf;
		Function<String, Integer> doubleIt = stringToInt.andThen(x -> x + x);
		int result = doubleIt.apply("3");

		assertThat(result).isEqualTo(6);
	}

	@Test
	public void partialEvaluationSimple() {
		BiFunction<Integer, Integer, Integer> addTwo = (x, y) -> x + y;
		Function<Integer, Integer> addThree = (x) -> addTwo.apply(3, x);

		assertThat(addThree.apply(1)).isEqualTo(4);
	}

	@Test
	public void partialEvaluationUsingCurrying() {
		Function<Integer, Function<Integer, Integer>> addTwo = (x) -> (y) -> x + y;
		Function<Integer, Integer> addThree = addTwo.apply(3);

		assertThat(addThree.apply(1)).isEqualTo(4);
	}

	@Test
	public void partialEvaluationUsingCurryingAndIntInterface() {
		IntFunction<IntUnaryOperator> addTwo = (x) -> (y) -> x + y;
		IntUnaryOperator addThree = addTwo.apply(3);

		assertThat(addThree.applyAsInt(1)).isEqualTo(4);
	}
}
