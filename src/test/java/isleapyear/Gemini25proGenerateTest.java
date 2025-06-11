package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
class Gemini25proGenerateTest {

	@Test
	void isLeapYear_whenYearIsValidLeapYear_shouldReturnTrue() throws Exception {
		assertTrue(LeapYear.isLeapYear("4"), "Year 4 should be a leap year");
		assertTrue(LeapYear.isLeapYear("100"), "Year 100 should be a leap year by the current rule (divisible by 4)");
		assertTrue(LeapYear.isLeapYear("1996"), "Year 1996 should be a leap year");
		assertTrue(LeapYear.isLeapYear("2000"), "Year 2000 should be a leap year");
		assertTrue(LeapYear.isLeapYear("2024"), "Year 2024 should be a leap year");
		assertTrue(LeapYear.isLeapYear("2100"), "Year 2100 should be a leap year by the current rule (boundary, divisible by 4)");
	}

	@Test
	void isLeapYear_whenYearIsValidNonLeapYear_shouldReturnFalse() throws Exception {
		assertFalse(LeapYear.isLeapYear("1"), "Year 1 should not be a leap year");
		assertFalse(LeapYear.isLeapYear("2"), "Year 2 should not be a leap year");
		assertFalse(LeapYear.isLeapYear("1997"), "Year 1997 should not be a leap year");
		assertFalse(LeapYear.isLeapYear("2019"), "Year 2019 should not be a leap year");
		assertFalse(LeapYear.isLeapYear("2099"), "Year 2099 should not be a leap year");
	}

	@Test
	void isLeapYear_whenYearIsOutOfLowerRange_shouldReturnFalse() throws Exception {
		assertFalse(LeapYear.isLeapYear("0"), "Year 0 is out of range (num > 0 fails)");
		assertFalse(LeapYear.isLeapYear("-4"), "Year -4 is out of range (num > 0 fails)");
		assertFalse(LeapYear.isLeapYear("-2000"), "Year -2000 is out of range (num > 0 fails)");
	}

	@Test
	void isLeapYear_whenYearIsOutOfUpperRange_shouldReturnFalse() throws Exception {
		assertFalse(LeapYear.isLeapYear("2101"), "Year 2101 is out of range (num <= 2100 fails)");
		assertFalse(LeapYear.isLeapYear("2104"), "Year 2104 is out of range (num <= 2100 fails), even if divisible by 4");
		assertFalse(LeapYear.isLeapYear("3000"), "Year 3000 is out of range");
	}

	@Test
	void isLeapYear_whenYearIsNull_shouldThrowNullPointerException() {
		assertThrows(NullPointerException.class, () -> {
			LeapYear.isLeapYear(null);
		}, "Passing null should throw NullPointerException");
	}

	@Test
	void isLeapYear_whenYearIsEmpty_shouldThrowEmptyException() {
		assertThrows(EmptyException.class, () -> {
			LeapYear.isLeapYear("");
		}, "Passing an empty string should throw EmptyException");
	}

	@Test
	void isLeapYear_whenYearIsNotNumeric_shouldThrowNumberFormatException() {
		assertThrows(NumberFormatException.class, () -> {
			LeapYear.isLeapYear("abc");
		}, "Passing 'abc' should throw NumberFormatException");

		assertThrows(NumberFormatException.class, () -> {
			LeapYear.isLeapYear("20.20");
		}, "Passing '20.20' (decimal) should throw NumberFormatException");

		assertThrows(NumberFormatException.class, () -> {
			LeapYear.isLeapYear("Twenty");
		}, "Passing 'Twenty' should throw NumberFormatException");
		
		assertThrows(NumberFormatException.class, () -> {
			LeapYear.isLeapYear(" 12 "); // Integer.parseInt handles this, but good to be aware.
										 // The current code would parse " 12 " as 12 and return true.
										 // Let's use a more clearly invalid format.
			LeapYear.isLeapYear("12a"); 
		}, "Passing '12a' should throw NumberFormatException");

		assertThrows(NumberFormatException.class, () -> {
			LeapYear.isLeapYear("2,000"); // Contains comma
		}, "Passing '2,000' should throw NumberFormatException");
	}

	@Test
	void isLeapYear_whenYearIsLargeNumberString_shouldThrowNumberFormatExceptionOrReturnFalse() {
		// Test with a number string that might be too large for int, or simply out of range
		// Integer.parseInt will throw NumberFormatException for numbers outside int range.
		// If it parses but is > 2100, it should return false.
		String veryLargeNumber = "2147483648"; // Larger than Integer.MAX_VALUE
		assertThrows(NumberFormatException.class, () -> {
			LeapYear.isLeapYear(veryLargeNumber);
		}, "Passing a string for a number larger than Integer.MAX_VALUE should throw NumberFormatException");

		String largeButParseableOutOfRange = "10000";
		try {
			assertFalse(LeapYear.isLeapYear(largeButParseableOutOfRange), "Year 10000 is out of range");
		} catch (Exception e) {
			// Should not throw for this specific case if it parses to int correctly
			org.junit.jupiter.api.Assertions.fail("Should not throw for " + largeButParseableOutOfRange);
		}
	}
}