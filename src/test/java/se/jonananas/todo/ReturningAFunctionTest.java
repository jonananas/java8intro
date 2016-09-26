package se.jonananas.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

public class ReturningAFunctionTest {

	List<String> djurlista = Arrays.asList("apa", "ko", "katt");

	@Test
	public void useAFilterFunction() {
		Function<String, Predicate<String>> startarMed = bokstav -> djur -> djur.startsWith(bokstav);

		List<String> resultat = djurlista.stream().filter(startarMed.apply("a")).collect(Collectors.toList());

		assertThat(resultat).containsExactly("apa");
	}

	@Test
	public void useAFilterFunction__() {
		Function<String, Predicate<String>> startarMed = bokstav -> djur -> djur.startsWith(bokstav);
		Predicate<String> startarMedA = startarMed.apply("a");

		List<String> resultat = djurlista.stream().filter(startarMedA).collect(Collectors.toList());

		assertThat(resultat).containsExactly("apa");
	}

	@Test
	public void useAFilterMethod() {

		List<String> resultat = djurlista.stream().filter(startarMed("a")).collect(Collectors.toList());

		assertThat(resultat).containsExactly("apa");
	}

	Predicate<String> startarMed(String bokstav) {
		return djur -> djur.startsWith(bokstav);
	}

	@Test
	public void useAStaticFilterMethod() {

		List<String> resultat = djurlista.stream().filter(staticStartarMed("a")).collect(Collectors.toList());

		assertThat(resultat).containsExactly("apa");
	}

	private static Predicate<String> staticStartarMed(String bokstav) {
		return djur -> djur.startsWith(bokstav);
	}
}
