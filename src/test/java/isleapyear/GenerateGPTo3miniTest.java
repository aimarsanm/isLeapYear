package isleapyear;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;




class GenerateGPTo3miniTest {

	private final LeapYear leapYear = new LeapYear();

	@Test
	void testValidLeapYear() throws Exception {
		// Valid leap years within range and divisible by 4.
		assertTrue(leapYear.isLeapYear("2004"));
		assertTrue(leapYear.isLeapYear("2100")); // 2100 % 4 == 0
	}

	@Test
	void testNonLeapYear() throws Exception {
		// Years that are not divisible by 4.
		assertFalse(leapYear.isLeapYear("2001"));
		assertFalse(leapYear.isLeapYear("1999"));
	}

	@Test
	void testYearZeroNotLeap() throws Exception {
		// Year zero is explicitly not a leap year.
		assertFalse(leapYear.isLeapYear("0"));
	}

	@Test
	void testNegativeYearNotLeap() throws Exception {
		// Negative years are out of range.
		assertFalse(leapYear.isLeapYear("-4"));
	}

	@Test
	void testYearAboveRangeNotLeap() throws Exception {
		// Years above 2100 are out of range.
		assertFalse(leapYear.isLeapYear("2101"));
	}

	@Test
	void testEmptyYearThrowsException() {
		// Passing an empty string should throw EmptyException.
		assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
	}

	@Test
	void testNullYearThrowsException() {
		// Passing a null should throw NullPointerException.
		assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
	}

	@Test
	void testNonNumericYearThrowsException() {
		// Passing a non-numeric string should throw NumberFormatException.
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abc"));
	}
}