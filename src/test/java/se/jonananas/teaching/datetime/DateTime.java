package se.jonananas.teaching.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;

// TODO:
// - ZoneID
// - TemporalAdjusters

public class DateTime {
	// http://docs.oracle.com/javase/tutorial/datetime/iso/QandE/questions.html

	// Given a random date, how would you find the date of the previous Thursday?
	@Test
	public void findDateOfThursday() {
		LocalDate previousThursday = LocalDate.of(2016, 10, 24).with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));

		assertThat(previousThursday).isEqualTo(LocalDate.of(2016, 10, 20));
	}

	// 
	@Test
	public void findStockholmTimezone() {
		String stockholmTZ = timeZoneIdStockholm("stockholm");

		assertThat(stockholmTZ).isEqualTo("Europe/Stockholm");
	}

	private String timeZoneIdStockholm(String needle) {
		return StreamSupport.stream(ZoneId.getAvailableZoneIds().spliterator(), false) //
				.filter(x -> x.toString().toLowerCase().contains(needle.toLowerCase()))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Not found"));
	}

	// How would you convert an Instant to a ZonedDateTime? How would you convert a ZonedDateTime to an Instant?
	@Test
	public void InstantToZonedDateTime() {
		Instant instant = Instant.ofEpochSecond(1477312439);

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Europe/Stockholm"));

		assertThat(zonedDateTime.toString()).isEqualTo("2016-10-24T14:33:59+02:00[Europe/Stockholm]");

	}

	// Write an example that, for a given year, reports the length of each month within that year.
	@Test
	public void lengthOfMonthPerYear() {
		int year = 2016;
		LocalDate startDate = LocalDate.of(year, 01, 01);
		LocalDate nextMonth = ChronoUnit.MONTHS.addTo(startDate, 1);

		Stream.of(Month.values()).map(month -> YearMonth.of(year, month).lengthOfMonth()).reduce((list, res) -> list + res);
	}

	// Write an example that, for a given month of the current year, lists all of the Mondays in that month.

	// Write an example that tests whether a given date occurs on Friday the 13th.
}
