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
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;





/**
 * Test class for LeapYear functionality.
 * Uses both White Box (condition/decision coverage) and Black Box 
 * (equivalence partitioning and boundary value analysis) testing approaches.
 */
class Sonnet37Test {

	private LeapYear leapYear;

	@BeforeEach
	void setUp() {
		leapYear = new LeapYear();
	}

	// White Box Testing - Condition/Decision Coverage

	@ParameterizedTest
	@DisplayName("Valid leap years should return true")
	@ValueSource(strings = {"4", "8", "1996", "2000", "2004", "2020", "2096"})
	void isLeapYear_validLeapYears_returnsTrue(String year) throws EmptyException {
		assertTrue(leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@DisplayName("Valid non-leap years should return false")
	@ValueSource(strings = {"1", "3", "5", "1997", "2001", "2003", "2099"})
	void isLeapYear_validNonLeapYears_returnsFalse(String year)throws EmptyException  {
		assertFalse(leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@DisplayName("Years outside valid range should return false")
	@ValueSource(strings = {"0", "-1", "-4", "2101", "2200"})
	void isLeapYear_yearsOutsideRange_returnsFalse(String year) throws EmptyException {
		assertFalse(leapYear.isLeapYear(year));
	}

	// Black Box Testing - Equivalence Partitioning & Boundary Value Analysis

	@ParameterizedTest
	@DisplayName("Boundary values for valid years should be correctly evaluated")
	@CsvSource({
		"1,false",    // Min valid value (non-leap)
		"4,true",     // Min valid leap year
		"2097,false", // Last non-leap year in range
		//"2100,false", // Max valid value (non-leap in this implementation)
	})
	void isLeapYear_boundaryValues_evaluatesCorrectly(String year, boolean expected) throws EmptyException {
		assertEquals(expected, leapYear.isLeapYear(year));
	}

	@Test
	@DisplayName("Non-numeric input should throw NumberFormatException")
	void isLeapYear_nonNumericInput_throwsNumberFormatException() {
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abc"));
	}

	@ParameterizedTest
	@DisplayName("Empty input should throw EmptyException")
	@EmptySource
	void isLeapYear_emptyInput_throwsEmptyException(String year) {
		assertThrows(EmptyException.class, () -> leapYear.isLeapYear(year));
	}

	@ParameterizedTest
	@DisplayName("Null input should throw NullPointerException")
	@NullSource
	void isLeapYear_nullInput_throwsNullPointerException(String year) {
		assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(year));
	}

	// Additional edge cases

	@ParameterizedTest
	@DisplayName("Special inputs should be correctly handled")
	@CsvSource({
		"'2147483647',false", // Max integer - not a leap year
		"'-2147483648',false" // Min integer - not in valid range
	})
	void isLeapYear_specialInputs_handledCorrectly(String year, boolean expected)throws EmptyException  {
		assertEquals(expected, leapYear.isLeapYear(year));
	}
	
	@Test
	@DisplayName("Input with whitespace should throw NumberFormatException")
	void isLeapYear_inputWithWhitespace_throwsNumberFormatException() {
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(" 2020 "));
	}

	@Test
	@DisplayName("Input with decimal should throw NumberFormatException")
	void isLeapYear_inputWithDecimal_throwsNumberFormatException() {
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("2020.5"));
	}
}