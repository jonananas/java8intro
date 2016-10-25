package se.jonananas.teaching.streamingslides;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

public class A_Imperative_vs_Functional {

	List<String> names = Arrays.asList("Anna", "Lena", "Bert", "Olof", "Mikael");

	// All names containing "a"

	@Test
	public void imperative() {

		List<String> matchar = new ArrayList<>();
		for (String ettNamn : names) {
			if (ettNamn.contains("a")) {
				matchar.add(ettNamn);
			}
		}

		assertThat(matchar).containsExactly("Anna", "Lena", "Mikael");
	}

	@Test
	public void functional() {

		List<String> matchar = names.stream() //
				.filter(ettNamn -> ettNamn.contains("a")) //
				.collect(toList());

		assertThat(matchar).containsExactly("Anna", "Lena", "Mikael");
	}

	// Sum all even numbers up to 14
	@Test
	public void summan_av_alla_jämna_tal() {
		assertThat(imperative_sumOfEvenNumbers(14)) //
				.isEqualTo(functional_sumOfEvenNumbers(14)) //
				.isEqualTo(56);
	}

	public int imperative_sumOfEvenNumbers(int upto) {
		int sum = 0;
		for (int i = 0; i <= upto; i++) {
			if (i % 2 == 0) {
				sum += i;
			}
		}
		return sum;
	}

	public int functional_sumOfEvenNumbers(int endInclusive) {
		return IntStream.range(0, endInclusive + 1) //
				.filter(x -> x % 2 == 0) //
				.sum();
	}

}
