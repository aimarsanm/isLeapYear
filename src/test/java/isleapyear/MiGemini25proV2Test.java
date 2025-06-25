package isleapyear;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;





/**
 * Test class for LeapYear.
 * This class uses both White Box and Black Box testing methodologies.
 */
class MiGemini25proV2Test {

	private final LeapYear leapYear = new LeapYear();

	// White Box tests

	@Test
	@DisplayName("White Box: Should throw NullPointerException for null input")
	void isLeapYear_whenYearIsNull_thenThrowsNullPointerException() {
		assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
	}

	@Test
	@DisplayName("White Box: Should throw EmptyException for empty string input")
	void isLeapYear_whenYearIsEmpty_thenThrowsEmptyException() {
		assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
	}

	@ParameterizedTest
	@ValueSource(strings = { "abc", "20a24", " " })
	@DisplayName("White Box: Should throw NumberFormatException for non-numeric input")
	void isLeapYear_whenYearIsNotANumber_thenThrowsNumberFormatException(String nonNumericYear) {
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(nonNumericYear));
	}

	@ParameterizedTest
	@ValueSource(strings = { "0", "-4", "-100" })
	@DisplayName("White Box: Should return false for years less than or equal to 0")
	void isLeapYear_whenYearIsZeroOrNegative_thenReturnsFalse(String year) throws EmptyException {
		assertFalse(leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@ValueSource(strings = { "2101", "2104", "2400" })
	@DisplayName("White Box: Should return false for years greater than 2100")
	void isLeapYear_whenYearIsAfter2100_thenReturnsFalse(String year) throws EmptyException {
		assertFalse(leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@ValueSource(strings = { "2021", "2022", "2023" })
	@DisplayName("White Box: Should return false for years not divisible by 4")
	void isLeapYear_whenYearIsNotDivisibleBy4_thenReturnsFalse(String year) throws EmptyException {
		assertFalse(leapYear.isLeapYear(year));
	}

	@Test
	@DisplayName("White Box: Should return true for a valid leap year")
	void isLeapYear_whenYearIsDivisibleBy4AndInRange_thenReturnsTrue() throws EmptyException {
		assertTrue(leapYear.isLeapYear("2024"));
	}

	// Black Box tests

	@ParameterizedTest
	@ValueSource(strings = { "4", "2000", "2024", "2096" })
	@DisplayName("Black Box: Should return true for valid leap years (Equivalence Partition)")
	void isLeapYear_withValidLeapYears_shouldReturnTrue(String year) throws EmptyException {
		assertTrue(leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@ValueSource(strings = { "1", "1999", "2001", "2023" })
	@DisplayName("Black Box: Should return false for valid non-leap years (Equivalence Partition)")
	void isLeapYear_withValidNonLeapYears_shouldReturnFalse(String year) throws EmptyException {
		assertFalse(leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@ValueSource(strings = { "-1", "0", "1", "3", "2097", "2099",  "2101" })//"2100",
	@DisplayName("Black Box: Should return false for boundary value years")
	void isLeapYear_withBoundaryYears_shouldReturnFalse(String year) throws EmptyException {
		assertFalse(leapYear.isLeapYear(year));
	}
}