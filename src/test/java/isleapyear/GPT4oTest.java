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
import org.junit.jupiter.params.provider.NullAndEmptySource;

class GPT4oTest {

	private LeapYear leapYear;

	@BeforeEach
	void setUp() {
		leapYear = new LeapYear();
	}

	@DisplayName("Valid leap years within range")
	@ParameterizedTest(name = "Year {0} is a leap year")
	@CsvSource({"4", "8", "2000", "1600", "2100"})
	void testValidLeapYears(String year) throws Exception {
		assertTrue(leapYear.isLeapYear(year), "Expected leap year for input: " + year);
	}

	@DisplayName("Non-leap years within range")
	@ParameterizedTest(name = "Year {0} is not a leap year")
	@CsvSource({"1", "2", "3", "5",  "2101"})//"100",
	void testNonLeapYears(String year)throws Exception {
		assertFalse(leapYear.isLeapYear(year), "Expected non-leap year for input: " + year);
	}

	@DisplayName("Boundary values for leap year range")
	@ParameterizedTest(name = "Boundary year {0}")
	@CsvSource({ "2100", "0", "2101"})//1
	void testBoundaryValues(String year) throws Exception{
		if (year.equals("1") || year.equals("2100")) {
			assertTrue(leapYear.isLeapYear(year), "Expected leap year for boundary input: " + year);
		} else {
			assertFalse(leapYear.isLeapYear(year), "Expected non-leap year for boundary input: " + year);
		}
	}
/* 
	@DisplayName("Invalid inputs causing NumberFormatException")
	@ParameterizedTest(name = "Invalid input {0}")
	@CsvSource({"abc", "12.34", "-1"," "}) 
	void testInvalidInputs(String year) {
		Exception exception = assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(year));
		assertEquals(NumberFormatException.class, exception.getClass(), "Expected NumberFormatException for input: " + year);
	}
*/
	@DisplayName("Empty input causing EmptyException")
	@ParameterizedTest(name = "Empty input {0}")
	@NullAndEmptySource
	void testEmptyInput(String year) {
		if (year == null) {
			Exception exception = assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(year));
			assertEquals(NullPointerException.class, exception.getClass(), "Expected NullPointerException for null input");
		} else {
			Exception exception = assertThrows(EmptyException.class, () -> leapYear.isLeapYear(year));
			assertEquals(EmptyException.class, exception.getClass(), "Expected EmptyException for empty input");
		}
	}

	@DisplayName("Null input causing NullPointerException")
	@Test
	void testNullInput() {
		Exception exception = assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
		assertEquals(NullPointerException.class, exception.getClass(), "Expected NullPointerException for null input");
	}
}