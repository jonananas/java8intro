package se.jonananas.teaching;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

// Från https://www.voxxed.com/blog/2016/04/gang-four-patterns-functional-light-part-1/
public class CommandPatternTest {
	static List<String> result = new ArrayList<String>();

	public void log(String message) {
		// Setting a breakpoint here will show this is on stack, might be expensive!
		result.add("Logging: " + message);
	}

	public static void save(String message) {
		result.add("Saving: " + message);
	}
	
	public static Runnable send(String message) {
		return () -> result.add("Sending: "+message);
	}

	public static void execute(List<Runnable> tasks) {
		tasks.forEach(Runnable::run);
	}

	@Test
	public void invokeCommands() throws Exception {

		List<Runnable> tasks = Arrays.asList(
				() -> this.log("Hi"), 
				() -> save("Cheers"), 
				send("Bye"));

		execute(tasks);
		
		assertThat(result).containsExactly("Logging: Hi", "Saving: Cheers", "Sending: Bye");
	}
}
