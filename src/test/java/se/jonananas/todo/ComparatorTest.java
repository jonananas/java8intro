package se.jonananas.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
		List<String> sorted = letters.stream().sorted(ascending2()).collect(asList);
		assertThat(sorted).containsExactly("a", "b", "c");
	}

	public static Comparator<String> ascending1() {
		return (lhs, rhs) -> lhs.compareTo(rhs);
	}

	public static Comparator<String> ascending2() {
		return String::compareTo;
	}
}
