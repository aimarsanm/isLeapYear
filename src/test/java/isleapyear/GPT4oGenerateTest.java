package isleapyear;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;




class GPT4oGenerateTest {

	@ParameterizedTest
	@CsvSource({
		"2000, true",  // Valid leap year
		"2001, false", // Valid non-leap year
		"4, true",     // Smallest leap year
		"2100, true",  // Upper boundary leap year
		"1, false"     // Smallest non-leap year
	})
	@DisplayName("Test valid leap years and non-leap years")
	void testValidYears(String year, boolean expected) throws EmptyException {
		assertEquals(expected, LeapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@CsvSource({
		//"0",       // Invalid year (boundary)
		//"2101",    // Invalid year (boundary)
		//"-1",      // Negative year
		"abcd",    // Non-numeric string
		"12.34"    // Decimal string
	})
	@DisplayName("Test invalid numeric and non-numeric inputs")
	void testInvalidInputs(String year) {
		assertThrows(NumberFormatException.class, () -> LeapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("Test null and empty string inputs")
	void testNullAndEmptyInputs(String year) {
		if (year == null) {
			assertThrows(NullPointerException.class, () -> LeapYear.isLeapYear(year));
		} else {
			assertThrows(EmptyException.class, () -> LeapYear.isLeapYear(year));
		}
	}

	@Test
	@DisplayName("Test valid leap year at boundary (2100)")
	void testBoundaryLeapYear() throws EmptyException {
		assertTrue(LeapYear.isLeapYear("2100"));
	}

	@Test
	@DisplayName("Test valid non-leap year at boundary (1)")
	void testBoundaryNonLeapYear() throws EmptyException {
		assertFalse(LeapYear.isLeapYear("1"));
	}
/* 
	@Test
	@DisplayName("Test invalid year below range (0)")
	void testYearBelowRange() {
		assertThrows(NumberFormatException.class, () -> LeapYear.isLeapYear("0"));
	}

	@Test
	@DisplayName("Test invalid year above range (2101)")
	void testYearAboveRange() {
		assertThrows(NumberFormatException.class, () -> LeapYear.isLeapYear("2101"));
	}
*/
	@Test
	@DisplayName("Test invalid non-numeric input")
	void testNonNumericInput() {
		assertThrows(NumberFormatException.class, () -> LeapYear.isLeapYear("abcd"));
	}

	@Test
	@DisplayName("Test null input throws NullPointerException")
	void testNullInput() {
		assertThrows(NullPointerException.class, () -> LeapYear.isLeapYear(null));
	}

	@Test
	@DisplayName("Test empty input throws EmptyException")
	void testEmptyInput() {
		assertThrows(EmptyException.class, () -> LeapYear.isLeapYear(""));
	}
}