package isleapyear;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;





class Sonnet37ThinkTest {

	private LeapYear leapYear;

	@BeforeEach
	void setUp() {
		leapYear = new LeapYear();
	}

	// White Box Tests - Decision/Condition Coverage

	@ParameterizedTest
	@DisplayName("Valid leap years return true")
	@ValueSource(strings = {"4", "8", "12", "2000", "2020", "2100"})
	void validLeapYears_ReturnTrue(String year) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertTrue(result, "Year " + year + " should be a leap year");
		} catch (Exception e) {
			fail("Should not throw exception for valid input: " + year);
		}
	}

	@ParameterizedTest
	@DisplayName("Valid non-leap years return false")
	@ValueSource(strings = {"1", "3", "5", "2001", "2019", "2099"})
	void validNonLeapYears_ReturnFalse(String year) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertFalse(result, "Year " + year + " should not be a leap year");
		} catch (Exception e) {
			fail("Should not throw exception for valid input: " + year);
		}
	}

	@ParameterizedTest
	@DisplayName("Years greater than 2100 return false")
	@ValueSource(strings = {"2101", "2104", "3000"})
	void yearsGreaterThan2100_ReturnFalse(String year) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertFalse(result, "Years greater than 2100 should return false");
		} catch (Exception e) {
			fail("Should not throw exception for valid input: " + year);
		}
	}

	@ParameterizedTest
	@DisplayName("Years less than or equal to 0 return false")
	@ValueSource(strings = {"0", "-4", "-100"})
	void yearsLessThanOrEqualToZero_ReturnFalse(String year) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertFalse(result, "Years less than or equal to 0 should return false");
		} catch (Exception e) {
			fail("Should not throw exception for valid input: " + year);
		}
	}

	// Black Box Tests - Boundary Value Analysis

	@ParameterizedTest
	@DisplayName("Test boundary values for valid range")
	@CsvSource({
		"0, false",    // At lower boundary (0)
		"1, false",    // Just above lower boundary, not divisible by 4
		"4, true",     // Lower boundary for leap years
		"2096, true",  // Upper boundary for leap years
		"2100, true",  // At upper boundary
		"2101, false"  // Just above upper boundary
	})
	void boundaryValuesForValidRange(String year, boolean expected) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertEquals(expected, result, "Boundary year " + year + " should return " + expected);
		} catch (Exception e) {
			fail("Should not throw exception for valid input: " + year);
		}
	}

	@ParameterizedTest
	@DisplayName("Test boundary values for leap year condition")
	@CsvSource({
		"3, false",   // Just before divisible by 4
		"4, true",    // Divisible by 4
		"7, false",   // Just before next divisible by 4
		"8, true"     // Divisible by 4
	})
	void boundaryValuesForLeapYearCondition(String year, boolean expected) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertEquals(expected, result, "Year " + year + " leap condition should return " + expected);
		} catch (Exception e) {
			fail("Should not throw exception for valid input: " + year);
		}
	}

	// Exception Tests

	@Test
	@DisplayName("Null input throws NullPointerException")
	void nullInput_ThrowsNullPointerException() {
		try {
			leapYear.isLeapYear(null);
			fail("Should throw NullPointerException for null input");
		} catch (NullPointerException e) {
			// Expected - test passes
		} catch (Exception e) {
			fail("Should throw NullPointerException, but got " + e.getClass().getSimpleName());
		}
	}

	@Test
	@DisplayName("Empty input throws EmptyException")
	void emptyInput_ThrowsEmptyException() {
		try {
			leapYear.isLeapYear("");
			fail("Should throw EmptyException for empty input");
		} catch (EmptyException e) {
			// Expected - test passes
		} catch (Exception e) {
			fail("Should throw EmptyException, but got " + e.getClass().getSimpleName());
		}
	}

	@ParameterizedTest
	@DisplayName("Non-numeric input throws NumberFormatException")
	@ValueSource(strings = {"abc", "12a", "a12", "2000a"})
	void nonNumericInput_ThrowsNumberFormatException(String input) {
		try {
			leapYear.isLeapYear(input);
			fail("Should throw NumberFormatException for input: " + input);
		} catch (NumberFormatException e) {
			// Expected - test passes
		} catch (Exception e) {
			fail("Should throw NumberFormatException, but got " + e.getClass().getSimpleName());
		}
	}

	// Truth Table Combinations Test

	@ParameterizedTest
	@DisplayName("Test condition combinations from truth table")
	@CsvSource({
		"4, true",      // num>0=T, num<=2100=T, num%4==0=T => true
		"3, false",     // num>0=T, num<=2100=T, num%4==0=F => false
		"2104, false",  // num>0=T, num<=2100=F, num%4==0=T => false
		"2101, false",  // num>0=T, num<=2100=F, num%4==0=F => false
		"0, false",     // num>0=F, num<=2100=T, num%4==0=T => false
		"-3, false",    // num>0=F, num<=2100=T, num%4==0=F => false
		"-4, false",    // num>0=F, num<=2100=F, num%4==0=T => false (due to short-circuit)
		"-5, false"     // num>0=F, num<=2100=F, num%4==0=F => false (due to short-circuit)
	})
	void truthTableCombinations(String year, boolean expected) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertEquals(expected, result, "Year " + year + " should return " + expected);
		} catch (Exception e) {
			fail("Should not throw exception for valid input: " + year);
		}
	}
}