package se.jonananas.solutions.datetime;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.LocalDate.now;
import static java.time.Month.APRIL;
import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.previous;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjuster;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

// From http://docs.oracle.com/javase/tutorial/datetime/iso/QandE/questions.html

public class DateTime {

	// Given a random date, find the date of the previous Thursday!
	@Test
	public void findDateOfThursday() {
		LocalDate previousThursday = now().with(previous(THURSDAY));

		assertThat(previousThursday).isEqualTo(LocalDate.of(2016, 12, 8));
	}

	// Find the timezone for stockholm!
	@Test
	public void findStockholmTimezone() {
		String stockholmTZ = timeZoneIdStockholm("stockholm");

		assertThat(stockholmTZ).isEqualTo("Europe/Stockholm");
	}

	private String timeZoneIdStockholm(String needle) {
		return ZoneId.getAvailableZoneIds().stream() //
			.filter(zone -> zone.toString().toLowerCase().contains(needle.toLowerCase()))
			.findFirst()
			.get();
	}

	// Convert an Instant to a ZonedDateTime! 
	@Test
	public void InstantToZonedDateTime() {
		Instant instant = Instant.ofEpochSecond(1477312439);

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Europe/Stockholm"));

		assertThat(zonedDateTime.toString()).isEqualTo("2016-10-24T14:33:59+02:00[Europe/Stockholm]");

	}

	// Write an example that, for a given year, reports the length of each month within 2017.
	@Test
	public void lengthOfMonthPerYear() {
		String daysPerMonth = lengthOfMonthPerYear(Year.of(2017));

		assertThat(daysPerMonth).isEqualTo("JANUARY 31\n" + //
				"FEBRUARY 28\n" + //
				"MARCH 31\n" + //
				"APRIL 30\n" + //
				"MAY 31\n" + //
				"JUNE 30\n" + //
				"JULY 31\n" + //
				"AUGUST 31\n" + //
				"SEPTEMBER 30\n" + //
				"OCTOBER 31\n" + //
				"NOVEMBER 30\n" + //
				"DECEMBER 31");
	}

	private String lengthOfMonthPerYear(Year year) {
		return Stream.of(Month.values()) //
			.map(month -> month.toString() + " " + year.atMonth(month).lengthOfMonth())
			.collect(joining("\n"));
	}

	// Write an example that, for a given month of the current year, lists all of the Mondays in april
	@Test
	public void allMondaysOfMonth() {
		List<LocalDate> result = allMondaysOfMonth(Year.now().atMonth(APRIL));

		assertThat(result).containsExactly(LocalDate.of(2016, 04, 04), //
				LocalDate.of(2016, 04, 11), //
				LocalDate.of(2016, 04, 18), //
				LocalDate.of(2016, 04, 25));
	}

	private List<LocalDate> allMondaysOfMonth(YearMonth yearMonth) {
		TemporalAdjuster nextMonday = next(MONDAY);
		return Stream.iterate(yearMonth.atDay(01).with(nextMonday), d -> d.with(nextMonday))
			.limit(5)
			.filter(d -> d.getMonth() == yearMonth.getMonth())
			.collect(Collectors.toList());
	}

	// Find first Friday the 13th 2016
	@Test
	public void dateOccursOnFriday13th() {

		LocalDate friday13th = firstFriday13thOfYear(Year.of(2016));

		assertThat(friday13th).isEqualTo(LocalDate.of(2016, 05, 13));
	}

	private LocalDate firstFriday13thOfYear(Year year) {
		return Stream.of(Month.values())
			.map(month -> year.atMonth(month).atDay(13))
			.filter(this::isFriday13th)
			.findFirst()
			.get();
//		LocalDate startDate = year.atMonth(1).atDay(1);
//		while(1)
//		return .with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
	}

	public Boolean isFriday13th(LocalDate date) {
		return date.getDayOfWeek() == FRIDAY && date.getDayOfMonth() == 13;
	}
}
