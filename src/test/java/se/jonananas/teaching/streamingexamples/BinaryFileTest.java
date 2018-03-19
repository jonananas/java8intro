package se.jonananas.teaching.streamingexamples;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;

import org.junit.Test;

public class BinaryFileTest {
	
	@Test
	public void shouldMatchCorrectPnr() {
		byte bytearr[] = "1111-22-33F0000".getBytes(Charset.forName("UTF8"));
		
		assertThat(matchesPattern(bytearr)).isEqualTo("1111-22-33F0000");
	}
	
	@Test
	public void shouldNotMatchInCorrectPnr() {
		byte bytearr[] = "c111-22-33F0000".getBytes(Charset.forName("UTF8"));
		
		assertThat(matchesPattern(bytearr)).isEmpty();
	}
	
	@Test
	public void shouldMatchCorrectPnrWhenOtherStuffBegins() {
		byte bytearr[] = "xxx1111-22-33F0000".getBytes(Charset.forName("UTF8"));
		
		assertThat(matchesPattern(bytearr)).isEqualTo("1111-22-33F0000");
	}

	private String matchesPattern(byte[] bytearr) {
		int i = 0;
		for (byte b : bytearr) {
			if (!conformsTo(b, i));
				return "";
		}
		return "1111-22-33F0000";
	}

	private boolean conformsTo(byte b, int i) {
		return false;
	}

}
