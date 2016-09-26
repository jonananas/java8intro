package se.jonananas.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;

public class ComparatorTest {

	// New problem: Person with name, age, gender.
	// Sort asc/desc on each of those

	// comparing().thenComparing().reversed()
	// comparing is static
	// thenComparing is default

	// Group by age, retrieve only name
	// collect(groupingBy(Person::getAge, mapping(Person::getName, toList())

	@Test
	public void shouldFingDoIt() {
		List<String> letters = Arrays.asList("c", "b", "a");
		Collector<String, ?, List<String>> asList = Collectors.toList();
		Comparator<String> ascending = String::compareTo;
		List<String> sorted = letters.stream().sorted(ascending).collect(asList);
		assertThat(sorted).containsExactly("a", "b", "c");
	}

	private Comparator<String> ascending() {
		BiFunction<String, String, Integer> f = (lhs, rhs) -> lhs.compareTo(rhs);
		BiFunction<String, String, Integer> f2 = String::compareTo;
		//		return (a, b) -> f2.apply(a, b);
		return String::compareTo;
	}
}
