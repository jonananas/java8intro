package se.jonananas.examples;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

import org.junit.Test;

public class Find {

	// Övning: Returnera ett udda slumpmässigt tal
	// Sedan: ExG

	@Test
	public void shouldFindRandomNumber() {
		IntSupplier randomInt = () -> new Random().ints()//
				.findFirst()
				.getAsInt();

		for (int i = 0; i < 200; i++) {
			int randomOne = randomInt.getAsInt();
			assertThat(randomOne % 2).isNotEqualTo(0);
		}

		Stream.iterate(1, x -> x + 1).limit(200).forEach(x -> {
			int randomOne = randomInt.getAsInt();
			assertThat(randomOne % 2).isNotEqualTo(0);
		});
	}

}
