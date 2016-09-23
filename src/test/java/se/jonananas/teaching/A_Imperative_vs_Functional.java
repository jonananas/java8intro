package se.jonananas.teaching;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class A_Imperative_vs_Functional {

	List<String> namn = Arrays.asList("Anna", "Lena", "Bert", "Olof", "Mikael");
	
	@Test
	public void imperative() throws Exception {
		
		// Hitta alla namn med a i sig
		List<String> matchar = new ArrayList<>();
		for (String ettNamn : namn) {
			if (ettNamn.contains("a")) {
				matchar.add(ettNamn);
			}
		}
		
		assertThat(matchar).containsExactly("Anna", "Lena", "Mikael");
	}
	
	@Test
	public void functional() throws Exception {
		
		List<String> matchar = namn.stream()
			.filter(ettNamn -> ettNamn.contains("a"))
			.collect(toList());
		
		assertThat(matchar).containsExactly("Anna", "Lena", "Mikael");
	}
}
