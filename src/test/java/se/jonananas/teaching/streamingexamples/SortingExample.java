package se.jonananas.teaching.streamingexamples;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class SortingExample {

	List<String> namn = Arrays.asList("Clas", "Mattias", "Håkan", "Jonas", "Erik");

	@Test
	public void sorteraNamnAlfabetiskt() {

		List<String> sorterat = namn.stream() //
				.sorted()
				.collect(toList());

		assertThat(sorterat).containsExactly("Clas", "Erik", "Håkan", "Jonas", "Mattias");
	}

	@Test
	public void sorteraAlfabetisktLängstNamnSist() {

		Comparator<String> iLängdOrdning = (x, y) -> x.length() - y.length();
		Comparator<String> alfabetiskt = String::compareTo;

		List<String> sorterat = namn.stream() //
				.sorted(iLängdOrdning.thenComparing(alfabetiskt))
				.collect(toList());
		assertThat(sorterat).containsExactly("Clas", "Erik", "Håkan", "Jonas", "Mattias");
	}

	@Test
	public void sorteraNamnLängstFörstSenAlfabetiskt() {
		Comparator<String> iLängdOrdning = (x, y) -> x.length() - y.length();
		Comparator<String> alfabetiskt = String::compareTo;

		List<String> sorterat = namn.stream()//
				.sorted(iLängdOrdning.reversed().thenComparing(alfabetiskt))
				.collect(toList());
		assertThat(sorterat).containsExactly("Mattias", "Håkan", "Jonas", "Clas", "Erik");
	}

	@Test
	public void sorteraNamnLängstFörstOmvändAlfabetisk() {
		Comparator<String> iLängdOrdning = (x, y) -> x.length() - y.length();
		Comparator<String> alfabetiskt = String::compareTo;

		List<String> sorterat = namn.stream()//
				.sorted(iLängdOrdning.reversed().thenComparing(alfabetiskt.reversed()))
				.collect(toList());
		assertThat(sorterat).containsExactly("Mattias", "Jonas", "Håkan", "Erik", "Clas");
	}

}
