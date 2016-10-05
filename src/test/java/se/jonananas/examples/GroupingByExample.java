package se.jonananas.examples;

import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GroupingByExample {

	List<String> namnlista = Arrays.asList("Ellen", "Mikael", "Eyadh", "Jonas");

	@Test
	public void groupByLength() {
		Map<Integer, List<String>> expectedByLength = new HashMap<>();
		expectedByLength.put(5, Arrays.asList("Ellen", "Eyadh", "Jonas"));
		expectedByLength.put(6, Arrays.asList("Mikael"));

		assertThat(functionalGroupByLength()).isEqualTo(expectedByLength);
		assertThat(imperativeGroupByLength()).isEqualTo(expectedByLength);
	}

	private Map<Integer, List<String>> functionalGroupByLength() {
		return namnlista.stream() //
				.collect(groupingBy(x -> x.length()));
	}

	private Map<Integer, List<String>> imperativeGroupByLength() {
		Map<Integer, List<String>> resultMap = new HashMap<>();
		for (String namn : namnlista) {
			if (resultMap.get(namn.length()) == null) {
				resultMap.put(namn.length(), new ArrayList<String>());
			}
			resultMap.get(namn.length()).add(namn);
		}
		return resultMap;
	}

	// Övning: Gruppera på första bokstav i namnet
	@Test
	public void groupByFirstLetter() {
		Map<String, List<String>> expected = new HashMap<>();
		expected.put("E", Arrays.asList("Ellen", "Eyadh"));
		expected.put("M", Arrays.asList("Mikael"));
		expected.put("J", Arrays.asList("Jonas"));

		Map<String, List<String>> resultMap = namnlista.stream() //
				.collect(groupingBy(x -> x.substring(0, 1)));

		assertThat(resultMap).isEqualTo(expected);
	}

}
