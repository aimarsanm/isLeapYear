package isleapyear;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

// src/test/java/isleapyear/LeapYearTest.java



class GPTo4minipreviewTest {

	private LeapYear leapYear;

	@BeforeAll
	static void initAll() {
		// global setup if needed
	}

	@AfterAll
	static void tearDownAll() {
		// global teardown if needed
	}

	@BeforeEach
	void setUp() {
		leapYear = new LeapYear();
	}

	@AfterEach
	void tearDown() {
		// cleanup after each test if needed
	}

	@DisplayName("Valid leap years within range should return true")
	@ParameterizedTest(name = "{index} => year=''{0}''")
	@ValueSource(strings = {"4", "2100"})
	void testLeapYearsReturnTrue(String year) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertTrue(result, "Expected true for leap year: " + year);
		} catch (Exception e) {
			fail("Unexpected exception for valid leap year '" + year + "': " + e);
		}
	}

	@DisplayName("Non-leap or out-of-range years should return false")
	@ParameterizedTest(name = "{index} => year=''{0}''")
	@ValueSource(strings = {"1", "0", "-4", "2104"})
	void testNonLeapYearsReturnFalse(String year) {
		try {
			boolean result = leapYear.isLeapYear(year);
			assertFalse(result, "Expected false for non-leap or out-of-range year: " + year);
		} catch (Exception e) {
			fail("Unexpected exception for non-leap year '" + year + "': " + e);
		}
	}

	@Test
	@DisplayName("Null input should throw NullPointerException")
	void testNullInputThrowsNPE() {
		assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null),
			"Expected NullPointerException for null input");
	}

	@Test
	@DisplayName("Empty input should throw EmptyException")
	void testEmptyInputThrowsEmptyException() {
		assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""),
			"Expected EmptyException for empty input");
	}

	@Test
	@DisplayName("Non-numeric input should throw NumberFormatException")
	void testNonNumericInputThrowsNumberFormatException() {
		assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abc"),
			"Expected NumberFormatException for non-numeric input");
	}
}