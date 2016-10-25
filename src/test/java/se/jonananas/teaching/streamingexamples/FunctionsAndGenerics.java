package se.jonananas.teaching.streamingexamples;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

public class FunctionsAndGenerics {

	@Test
	public void shouldConvertStringListToIntList() {
		List<String> intsAsString = Arrays.asList("1", "2", "10");
		List<Integer> ints = convertListAsGenericClassMethod(intsAsString, x -> Integer.valueOf(x));

		assertThat(ints).containsExactly(1, 2, 10);
	}

	private static <T, U> List<U> convertListAsGenericClassMethod(List<T> in, Function<T, U> converter) {
		return in.stream() //
				.map(converter)
				.collect(toList());
	}

	//			static <T,U> BiFunction<List<T>, Function<T, U>, List<T>> convertListGeneric = (List<T> in, Function<T,U> converter) -> {
	//		        return in.stream() //
	//		                                 .map(converter)
	//		                                 .collect(toList());
	//		};

	static BiFunction<List<Integer>, Function<Integer, Integer>, List<Integer>> convertIntegerListAsFunction = (in, converter) -> {
		return in.stream() //
				.map(converter)
				.collect(toList());
	};

	@FunctionalInterface
	interface ConvertListFunction<T, U> extends BiFunction<List<T>, Function<T, U>, List<T>> {
	}

	static ConvertListFunction<Integer, Integer> ConvertIntegerListAsConvertListFunction = (in, converter) -> {
		return in.stream() //
				.map(converter)
				.collect(toList());
	};
}
