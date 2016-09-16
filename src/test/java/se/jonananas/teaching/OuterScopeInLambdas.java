package se.jonananas.teaching;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;

import org.junit.Test;

public class OuterScopeInLambdas {

	
	@Test
	public void usingFinalIsOk() throws Exception {
		final int x = 3;
		Function<Integer, Integer> addX = (y) -> x + y;
		
		assertThat(addX.apply(4)).isEqualTo(7);
	}

	@Test
	public void usingImplicitFinalIsOk() throws Exception {
		int x = 3;
		Function<Integer, Integer> addX = (y) -> x + y;
		
		assertThat(addX.apply(4)).isEqualTo(7);
		// Changing value of x will generate compilation error!
//		x = 5;
		// Vad kan hända om icke-finals tilläts?
	}

	static int staticX = 7;
	
	public void usingStaticIsOk() throws Exception {
		Function<Integer, Integer> addX = (y) -> staticX + y;
		
		assertThat(addX.apply(4)).isEqualTo(11);
	}
	
	public void writingStaticIsOk() throws Exception {
		Function<Integer, Integer> addX = (y) -> staticX++;
		
		assertThat(addX.apply(4)).isEqualTo(8);
		assertThat(staticX).isEqualTo(8);
	}
	
}
