package se.jonananas.teaching;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

public class A_LamdbaSyntacticSugar {
	
	@Test
	public void yLambda() throws Exception {
		Function<Integer, Integer> doubleIt = y -> y + y;
		
		assertThat(doubleIt.apply(3)).isEqualTo(6);
	}

	@Test
	public void yLambdaLonger() throws Exception {
		Function<Integer, Integer> doubleIt = (Integer y) -> {return y + y;};
		
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

		assertThat(stringToInt4.apply("1")).isEqualTo(1);
	}
	
	@Test
	public void methodReference() throws Exception {
		Function<String, Integer> stringToInt = Integer::valueOf;
		
		assertThat(stringToInt.apply("1")).isEqualTo(1);
	}
	
	@Test
	public void combiningLambdas() throws Exception {
		Function<String, Integer> stringToInt = Integer::valueOf;
		BiFunction<String, Integer, Integer> f = y -> stringToInt.andThen(x -> x+y).apply("3");

		assertThat(doubled).isEqualTo(6);
	}

	@Test
	public void currying() throws Exception {
		Function<String, Integer> stringToInt = Integer::valueOf;
		Function<String, Integer> doubleIt = stringToInt.andThen(x -> x+x);
		
		assertThat(doubleIt.apply("3")).isEqualTo(6);
	}

	// File API
}
