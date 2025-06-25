package isleapyear;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;





/**
 * Test class for the LeapYear class.
 * It employs both White Box and Black Box testing strategies.
 */
class MiGemini25proTest {

	private LeapYear leapYear;

	@BeforeEach
	void setUp() {
		leapYear = new LeapYear();
	}

	// --- White Box Tests ---
	// These tests are designed based on the internal structure of the code.

	@Test
	@DisplayName("White Box: Should throw NullPointerException for null input")
	void isLeapYear_whenYearIsNull_thenThrowsNullPointerException() {
		// This test covers the outer try-catch block for NullPointerException.
		assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
	}

	@Test
	@DisplayName("White Box: Should throw EmptyException for empty string input")
	void isLeapYear_whenYearIsEmpty_thenThrowsEmptyException() {
		// This test covers the 'if (year.isEmpty())' branch.
		assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
	}

	@ParameterizedTest
	@ValueSource(strings = { "abc", "20.24", " ", "!" })
	@DisplayName("White Box: Should throw NumberFormatException for non-integer strings")
	void isLeapYear_whenYearIsNotANumber_thenThrowsNumberFormatException(String invalidInput) {
		// This test covers the inner try-catch block for NumberFormatException.
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(invalidInput));
	}

	@ParameterizedTest
	@CsvSource({
		// Condition: num <= 0
		"0", 
		"-4", 
		"-2024",
		// Condition: num > 2100
		"2101", 
		"2104", 
		"2400",
		// Condition: num % 4 != 0
		"1", 
		"2023", 
		"2099"
	})
	@DisplayName("White Box: Should return false for years failing logical conditions")
	void isLeapYear_whenConditionsAreNotMet_shouldReturnFalse(String year) throws EmptyException {
		// This test covers all paths leading to a 'false' result from the main logical expression.
		assertFalse(leapYear.isLeapYear(year));
	}

	// --- Black Box Tests ---
	// These tests are designed based on the requirements (Equivalence Partitioning and Boundary Value Analysis).

	@ParameterizedTest
	@CsvSource({
		// Boundary Value: Lower boundary leap year
		"4",
		// Equivalence Class: Valid leap years
		"1996", 
		"2000", 
		"2024",
		// Boundary Value: Upper boundary leap year
		"2100"
	})
	@DisplayName("Black Box: Should return true for valid leap years")
	void isLeapYear_whenYearIsLeap_shouldReturnTrue(String year) throws EmptyException {
		// Tests the equivalence partition of valid leap years, including boundaries.
		assertTrue(leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@CsvSource({
		// Boundary Value: Lower boundary of the valid range (not a leap year)
		"1",
		// Equivalence Class: Valid non-leap years
		"1999", 
		"2023",
		// Boundary Value: Upper boundary neighbor (not a leap year)
		"2099",
		// Boundary Value: Below valid range
		"0",
		// Equivalence Class: Years below valid range
		"-100",
		// Boundary Value: Above valid range
		"2101",
		// Equivalence Class: Years above valid range
		"2200"
	})
	@DisplayName("Black Box: Should return false for non-leap or out-of-range years")
	void isLeapYear_whenYearIsNotLeapOrOutOfRange_shouldReturnFalse(String year) throws EmptyException {
		// Tests equivalence partitions for non-leap years and out-of-range years, including boundaries.
		assertFalse(leapYear.isLeapYear(year));
	}
}