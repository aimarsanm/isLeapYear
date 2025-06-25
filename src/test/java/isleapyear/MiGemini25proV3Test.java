package isleapyear;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;





class MiGemini25proV3Test {

	private LeapYear leapYear;

	@BeforeEach
	void setUp() {
		leapYear = new LeapYear();
	}

	// 3-1. White box tests
	@Test
	@DisplayName("White Box: Test Null Input Throws NullPointerException")
	void testIsLeapYear_withNullInput_shouldThrowNullPointerException() {
		assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null),
				"Calling isLeapYear with null should throw NullPointerException.");
	}

	@Test
	@DisplayName("White Box: Test Empty Input Throws EmptyException")
	void testIsLeapYear_withEmptyInput_shouldThrowEmptyException() {
		assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""),
				"Calling isLeapYear with an empty string should throw EmptyException.");
	}

	@ParameterizedTest
	@ValueSource(strings = { "abc", "20a16", " ", "nineteen-ninety-six" })
	@DisplayName("White Box: Test Non-Numeric Input Throws NumberFormatException")
	void testIsLeapYear_withNonNumericInput_shouldThrowNumberFormatException(String invalidInput) {
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(invalidInput),
				"Calling isLeapYear with a non-numeric string should throw NumberFormatException.");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4", "2000", "2024", "2100" })
	@DisplayName("White Box: Test Valid Leap Years Return True")
	void testIsLeapYear_withValidLeapYears_shouldReturnTrue(String year) throws EmptyException {
		assertTrue(leapYear.isLeapYear(year), "Year " + year + " should be identified as a leap year.");
	}

	@ParameterizedTest
	@ValueSource(strings = { "0", "-4", "-2000" })
	@DisplayName("White Box: Test Years Less Than Or Equal To Zero Return False")
	void testIsLeapYear_withYearsNotGreaterThanZero_shouldReturnFalse(String year)throws EmptyException {
		assertFalse(leapYear.isLeapYear(year), "Year " + year + " should not be a leap year.");
	}

	@ParameterizedTest
	@ValueSource(strings = { "2101", "2104", "2400" })
	@DisplayName("White Box: Test Years Greater Than 2100 Return False")
	void testIsLeapYear_withYearsGreaterThan2100_shouldReturnFalse(String year)throws EmptyException {
		assertFalse(leapYear.isLeapYear(year), "Year " + year + " is out of the valid range and should be false.");
	}

	@ParameterizedTest
	@ValueSource(strings = { "1", "1999", "2023" })
	@DisplayName("White Box: Test Years Not Divisible By 4 Return False")
	void testIsLeapYear_withYearsNotDivisibleByFour_shouldReturnFalse(String year)throws EmptyException {
		assertFalse(leapYear.isLeapYear(year), "Year " + year + " is not divisible by 4 and should be false.");
	}

	// 3-2. Black box tests
	@ParameterizedTest
	@CsvSource({
			// Equivalence Partition: Valid Leap Years (divisible by 4, in range)
			"'4',    true",
			"'1996', true",
			"'2000', true",
			// Equivalence Partition: Valid Non-Leap Years (not divisible by 4, in range)
			"'1',    false",
			"'1997', false",
			"'2023', false",
			// Equivalence Partition: Invalid Years (below range)
			"'0',    false",
			"'-4',   false",
			// Equivalence Partition: Invalid Years (above range)
			"'2104', false",
			"'2400', false"
	})
	@DisplayName("Black Box: Test Equivalence Partitions")
	void testIsLeapYear_withEquivalencePartitions(String year, boolean expected)throws EmptyException {
		assertEquals(expected, leapYear.isLeapYear(year),
				"Equivalence partition test failed for year: " + year);
	}

	@ParameterizedTest
	@CsvSource({
			// Lower boundary
			"'-1',   false", // Below lower bound
			"'0',    false", // On lower bound (invalid as per spec)
			"'1',    false", // Above lower bound (valid non-leap)
			"'3',    false", // Near lower leap year
			"'4',    true",  // On lower leap year
			"'5',    false", // Above lower leap year
			// Upper boundary
			"'2099', false", // Near upper leap year
			"'2100', true",  // On upper leap year (valid)
			"'2101', false", // Above upper bound
			"'2104', false"  // Above upper bound (but would be leap if in range)
	})
	@DisplayName("Black Box: Test Boundary Values")
	void testIsLeapYear_withBoundaryValues(String year, boolean expected) throws EmptyException{
		assertEquals(expected, leapYear.isLeapYear(year),
				"Boundary value analysis failed for year: " + year);
	}
}