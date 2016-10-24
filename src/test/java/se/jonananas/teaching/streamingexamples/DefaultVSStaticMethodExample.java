package se.jonananas.teaching.streamingexamples;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DefaultVSStaticMethodExample {

	interface Person {

		String getFirstName();

		String getLastName();

		default String getName() {
			return getFirstName() + " " + getLastName();
		}

		static int getVersion() {
			return 1;
		}
	}

	interface Snäll {

		default String getName() {
			return "Snäll";
		}

	}

	public static class PersonImpl implements Person, Snäll {

		private String firstName;
		private String lastName;

		public PersonImpl(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		@Override
		public String getFirstName() {
			return firstName;
		}

		@Override
		public String getLastName() {
			return lastName;
		}

		@Override
		public String getName() {
			return Snäll.super.getName();
		}
	}

	@Test
	public void nameShouldBeJohnDoe() {
		assertThat(new PersonImpl("John", "Doe").getName()).isEqualTo("John Doe");
	}

	@Test
	public void versionShouldBe2() {
		assertThat(Person.getVersion()).isEqualTo(1);
	}
}
