package isleapyear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;





class Claude3_5Test {
	
	@ParameterizedTest
	@DisplayName("Valid leap years should return true")
	@ValueSource(strings = {"4", "2000", "2020", "2096"})
	void validLeapYearsShouldReturnTrue(String year) {
		try {
			boolean result = LeapYear.isLeapYear(year);
			assertEquals(true, result, () -> year + " should be a leap year");
		} catch (Exception e) {
			fail("Should not throw exception for valid leap year: " + year);
		}
	}

	@ParameterizedTest
	@DisplayName("Valid non-leap years should return false")
	@ValueSource(strings = {"1", "2001", "2019", "2023"})
	void validNonLeapYearsShouldReturnFalse(String year) {
		try {
			boolean result = LeapYear.isLeapYear(year);
			assertEquals(false, result, () -> year + " should not be a leap year");
		} catch (Exception e) {
			fail("Should not throw exception for valid non-leap year: " + year);
		}
	}

	@ParameterizedTest
	@DisplayName("Boundary values should be handled correctly")
	@CsvSource({
		"0,false",
		"1,false",
		"4,true",
		"2100,true",
		"2096,true"
	})
	void boundaryValuesShouldBeHandledCorrectly(String year, boolean expected) {
		try {
			boolean result = LeapYear.isLeapYear(year);
			assertEquals(expected, result, () -> year + " should return " + expected);
		} catch (Exception e) {
			fail("Should not throw exception for boundary value: " + year);
		}
	}

	@Test
	@DisplayName("Null input should throw NullPointerException")
	void nullInputShouldThrowNullPointerException() {
		assertThrows(NullPointerException.class, () -> LeapYear.isLeapYear(null));
	}

	@Test
	@DisplayName("Empty input should throw EmptyException")
	void emptyInputShouldThrowEmptyException() {
		assertThrows(EmptyException.class, () -> LeapYear.isLeapYear(""));
	}

	@ParameterizedTest
	@DisplayName("Invalid numeric inputs should throw NumberFormatException")
	@ValueSource(strings = {"abc", "12.34", "2020.0", "2020a"})
	void invalidNumericInputsShouldThrowNumberFormatException(String input) {
		assertThrows(NumberFormatException.class, () -> LeapYear.isLeapYear(input));
	}

	@ParameterizedTest
	@DisplayName("Years outside valid range should return false")
	@ValueSource(strings = {"2101", "3000"})
	void yearsOutsideRangeShouldReturnFalse(String year) {
		try {
			boolean result = LeapYear.isLeapYear(year);
			assertEquals(false, result, () -> year + " should return false as it's outside valid range");
		} catch (Exception e) {
			fail("Should not throw exception for year outside range: " + year);
		}
	}
}