package se.jonananas.teaching.streamingslides;

import java.io.PrintWriter;
import java.util.Optional;

import org.junit.Test;

public class F_CaseForOptional {

	/*
	 *  null version
	 */
	@Test(expected = RuntimeException.class)
	public void shouldNotFindPerson_nullversion() {
		findPerson(3);
	}

	private Person findPerson(int id) {
		PersonRepository personRepo = new PersonRepository();
		Person p = personRepo.findPersonMaybeNull(id);
		if (p == null) {
			throw new RuntimeException("Person not found");
		}
		return p;
	}

	/*
	 * Optional version - better?
	 */
	@Test(expected = RuntimeException.class)
	public void shouldNotFindPerson_optional() {
		findPersonAsOptional(3);
	}

	private Person findPersonAsOptional(int id) {
		PersonRepository personRepo = new PersonRepository();
		Optional<Person> p = personRepo.findPersonAsOptional(id);
		if (p.isPresent()) {
			return p.get();
		}
		throw new RuntimeException("Person not found");
	}

	/*
	 * Better Optional version
	 */
	@Test(expected = RuntimeException.class)
	public void shouldNotFindPerson_optional_better() {
		findPersonAsOptionalBetter(3);
	}

	private Person findPersonAsOptionalBetter(int id) {
		PersonRepository personRepo = new PersonRepository();
		return personRepo.findPersonAsOptional(id) //
				.orElseThrow(() -> new RuntimeException("Person not found"));
	}

	/*
	 * Spot the problem?
	 */
	void writePersonToFile(Optional<Person> person, PrintWriter writer) {
		if (person.isPresent()) {
			writer.print(person.get());
		}
	}

	/*
	 * Helpers
	 */
	public static class Person {

	}

	public static class PersonRepository {

		public Person findPersonMaybeNull(int id) {
			return null;
		}

		public Optional<Person> findPersonAsOptional(int id) {
			return Optional.empty();
		}
	}
}
