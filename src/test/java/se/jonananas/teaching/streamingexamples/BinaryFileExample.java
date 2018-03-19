package se.jonananas.teaching.streamingexamples;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class BinaryFileExample {

	List<String> filenames = Arrays.asList("a.txt", "b.txt", "c.txt", "d.txt");

	// Testa om filerna öppnas om vi bara begär första raden

	Path dir = Paths.get(
			"/Users/jonananas/Documents/workspace/nya-stat/statistik.antagning/statistik.antagning.db/docker-with-data/IKHT15");

	@Test
	public void shouldOutputText() throws Exception {
		Path filepath = aFile();
		System.err.println(filepath.getFileName());
		InputStream inputStream = Files.newInputStream(filepath, StandardOpenOption.READ);

		Set<String> pnrs = findPattern(inputStream);
		System.err.println(pnrs.size());
	}

	private Set<String> findPattern(InputStream inputStream) throws IOException {
		// NOTE: We need codepoint instead of byte if handling non-ASCII
		final Pattern pattern = Pattern.compile("[0-9]{8}[-TF][0-9]{4}");
		Set<String> result = new HashSet<>();
		Matcher matcher = pattern.matcher("");
		
		byte bytes[] = new byte[1];
		String region = "";
		while (inputStream.read(bytes) != -1) {
			if (isLetterDigitOrMinus(bytes)) {
				region = region + toString(bytes);
			} else {
				region = "";
				continue;
			}

			matcher.reset(region);
			if (matcher.matches()) {
				result.add(region);
				region = "";
			} else if (!matcher.hitEnd()) {
				region = "";
			}
		}
		return result;
	}

	private String toString(byte[] bytes) {
		return new String(bytes, Charset.forName("ISO-8859-1"));
	}

	final static byte MINUSBYTE = "-".getBytes()[0];
	private boolean isLetterDigitOrMinus(byte[] bytes) {
		return Character.isLetterOrDigit(bytes[0]) || bytes[0] == MINUSBYTE;
	}

	private Path aFile() throws IOException {
		return Files.list(dir).filter(path -> path.getFileName().toString().equals("sok_appl_fee.ixf")).findFirst()
				.orElseThrow(() -> new RuntimeException("none found"));
	}
}
