package se.jonananas.teaching.streamingexamples;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class FlatMapExample {
	
	// @See image at https://1.bp.blogspot.com/-RJseuNzmm7I/Vtb3pH7iPkI/AAAAAAAAE-s/ZJSxR4EnlSI/s1600/Java%2B8%2BflatMap%2Bexample%2B.jpg

	// These are in src/test/resources
	List<String> filenames = Arrays.asList("a.txt", "b.txt", "c.txt", "d.txt");

	@Test
	public void shouldMergeFileContents() {
		String result = filenames.stream()//
				.map(filename -> readContentsAsList(filename))
				.flatMap(fileContents -> fileContents.stream())
				.collect(Collectors.joining(" "));

		assertThat(result).isEqualTo("hej så mycket då vi syns");
	}

	@Test
	public void shouldMergeFileContents_better() {
		String result = filenames.stream()//
				.map(filename -> readContents(filename))
				.flatMap(fileContents -> fileContents)
				.collect(Collectors.joining(" "));

		assertThat(result).isEqualTo("hej så mycket då vi syns");
	}

	@Test
	public void shouldCountLines() {
		int result = filenames.stream()//
				.map(filename -> readContentsAsList(filename))
				.flatMapToInt((List<String> fileContents) -> IntStream.of(fileContents.size()))
				.sum();

		assertThat(result).isEqualTo(5);
	}

	private List<String> readContentsAsList(String filename) {
		return readContents(filename).collect(toList());
	}

	private Stream<String> readContents(String filename) {
		try {
			URI testResource = this.getClass().getClassLoader().getResource(filename).toURI();
			return lines(get(testResource));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
