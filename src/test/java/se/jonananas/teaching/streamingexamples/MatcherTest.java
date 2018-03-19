package se.jonananas.teaching.streamingexamples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class MatcherTest {
	
	@Test
	public void testName() throws Exception {
		
		 Pattern pattern = Pattern.compile("[0-9]{8}[-TF][0-9]{4}");
		 Matcher matcher = pattern.matcher("19571007T0012");

		 assertThat(matcher.matches()).isTrue();
		 assertThat(matcher.hitEnd()).isFalse();
		 
		 matcher = pattern.matcher("1");
		 assertThat(matcher.matches()).isFalse();
		 assertThat(matcher.hitEnd()).isTrue();
		 
		 matcher.reset("1957");
		 assertThat(matcher.matches()).isFalse();
		 assertThat(matcher.hitEnd()).isTrue();
		 
		 matcher.reset("1957c");
		 assertThat(matcher.matches()).isFalse();
		 assertThat(matcher.hitEnd()).isFalse();
	}
	
	@Test
	public void advanced() throws Exception {
		 Pattern pattern = Pattern.compile("[0-9]{8}[-TF][0-9]{4}");
		 String region = "19571007T0012";
		 Matcher matcher = pattern.matcher(region);
		 if (matcher.matches()) {
			 System.err.println(region);
			 region = "";
		 } else if (!matcher.hitEnd()) {
			 region = "";
		 }
		 assertThat(region).isEmpty();
	}

}
