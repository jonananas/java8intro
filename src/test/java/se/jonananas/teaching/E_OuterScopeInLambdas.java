package se.jonananas.teaching;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;

import org.junit.Test;

public class E_OuterScopeInLambdas {

	@Test
	public void usingFinalIsOk() {
		Integer y = 3;
		Function<Integer, Integer> addX = addInt(y);

		// Does not affect addX
		y = 4;

		assertThat(addX.apply(4)).isEqualTo(7);
	}

	Function<Integer, Integer> addInt(Integer y) {
		return (z) -> y + z;
	}

	@Test
	public void usingImplicitFinalIsOk() {
		int x = 3;
		Function<Integer, Integer> addX = (y) -> x + y;

		assertThat(addX.apply(4)).isEqualTo(7);
		// Changing value of x will generate compilation error!
		//		x = 5;
		// Vad kan hända om icke-finals tilläts?
	}

	static int staticX = 7;

	@Test
	public void usingStaticIsOk() {
		Function<Integer, Integer> addX = (y) -> staticX + y;

		staticX = 5;

		// We would expect 9, right?
		assertThat(addX.apply(4)).isEqualTo(9);
	}

	@Test
	public void writingStaticIsOk() {
		Function<Integer, Integer> addX = (y) -> staticX = y;

		assertThat(staticX).isEqualTo(7);
		assertThat(addX.apply(4)).isEqualTo(4);
		assertThat(staticX).isEqualTo(4);
	}
}
