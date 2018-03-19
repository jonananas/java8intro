package se.jonananas.teaching.streamingexamples;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

public class CollectToMapExample {

	class MessageVO {

		private String kod;
		private String sve;
		private String eng;

		public MessageVO(String kod, String sve, String eng) {
			this.kod = kod;
			this.sve = sve;
			this.eng = eng;
		}

		public String getMeddelandeKod() {
			return this.kod;
		}

		public String getSvenskText() {
			return this.sve;
		}

		public String getEngelskText() {
			return this.eng;
		}

	}

	private Map<String, Map<String, String>> createMap_(List<MessageVO> messages) {
		return messages.stream()
				.collect(toMap(vo -> vo.getMeddelandeKod(), vo -> mapOf_("sv", vo.getSvenskText(), "en", vo.getEngelskText())));
	}

	private Map<String, String> mapOf_(String... pairs) {
		return Stream.iterate(0, x -> x + 2) //
				.limit(pairs.length / 2)
				.collect(toMap(idx -> pairs[idx], idx -> pairs[idx + 1]));
	}

	private Map<String, Map<String, String>> createMap(List<MessageVO> additionalConditionMessages) {
		Map<String, Map<String, String>> map = new HashMap<>();
		for (MessageVO vo : additionalConditionMessages) {
			map.put(vo.getMeddelandeKod(), mapOf("sv", vo.getSvenskText(), "en", vo.getEngelskText()));
		}
		return map;
	}

	private Map<String, String> mapOf(String... pairs) {
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < pairs.length; i += 2) {
			map.put(pairs[i], pairs[i + 1]);
		}
		return map;
	}

	private Map<String, Object> mapOf_(Object... pairs) {
		return Stream.iterate(0, x -> x + 2) //
				.limit(pairs.length / 2)
				.collect(toMap(idx -> pairs[idx].toString(), idx -> pairs[idx + 1]));
	}

	@Test
	public void shouldCreateMap() {
		List<MessageVO> messages =
				Stream.iterate(1, x -> x + 1).limit(2).map(x -> new MessageVO("" + x, "sve" + x, "eng" + x)).collect(toList());
		Map<String, Object> expected = mapOf_( //
				"1", mapOf("en", "eng1", "sv", "sve1"), //
				"2", mapOf("en", "eng2", "sv", "sve2") //
		);

		assertThat(this.createMap(messages)).isEqualTo(expected);
		assertThat(this.createMap_(messages)).isEqualTo(expected);
	}

}
