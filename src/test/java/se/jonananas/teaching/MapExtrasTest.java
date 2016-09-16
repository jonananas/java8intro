package se.jonananas.teaching;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapExtrasTest {

	Map<String, String> stringMap = new HashMap<String, String>();
	Map<Integer, Integer> intMap = new HashMap<Integer, Integer>();
	
	@Test
	public void putIfAbsent() throws Exception {
		
		stringMap.putIfAbsent("one", "yes");
		stringMap.putIfAbsent("one", "no");
		
		assertThat(stringMap.get("one")).isEqualTo("yes");
	}
	
	@Test
	public void computeIfPresent() throws Exception {
		intMap.put(3, 10);
		
		intMap.computeIfPresent(3, (key, value) -> key * value);
		assertThat(intMap.get(3)).isEqualTo(30);
	}
	
	@Test
	public void computeIfAbsent() throws Exception {
		
		intMap.computeIfAbsent(5, (key) -> key * 3);
		assertThat(intMap.get(5)).isEqualTo(15);
	}
	
	@Test
	public void getOrDefault() throws Exception {
		assertThat(stringMap.getOrDefault("nope", "default")).isEqualTo("default");
	}
	
	@Test
	public void remove() throws Exception {
		stringMap.put("key1", "value1");
		stringMap.remove("notfound", "ever");
		
		assertThat(stringMap.keySet()).containsExactly("key1");
	}

	@Test
	public void merge() throws Exception {
		stringMap.put("key", "value");

		// ändrar enligt lambda
		stringMap.merge("key", "value", (key, value) -> "newvalue");
		assertThat(stringMap.get("key")).isEqualTo("newvalue");
		
		// method invocation funkar fint
		stringMap.merge("key", " is appended", String::concat);
		assertThat(stringMap.get("key")).isEqualTo("newvalue is appended");
		
		// om lamdba returnera null tas entryt bort
		stringMap.merge("key", "any", (key, value) -> value.equals("any") ? null : "nope");
		assertThat(stringMap.keySet()).isEmpty();
		
		// om lambdat inte finns så läggs value till
		stringMap.merge("newkey", "value", (key, value) -> "newvalue");
		assertThat(stringMap.get("newkey")).isEqualTo("value");
	}
	
}
