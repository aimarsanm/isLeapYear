package isleapyear;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;




 class Gpto3miniTest {

	private LeapYear leapYear;

	@BeforeAll
	 static void initAll() {
		// Initialization before all tests if needed.
	}

	@BeforeEach
	 void init() {
		leapYear = new LeapYear();
	}

	@DisplayName("Test valid numeric inputs for leap year determination")
	@ParameterizedTest(name = "For year \"{0}\", expected result is {1}")
	@CsvSource({
		"4, true",
		"8, true",
		"2100, true",
		"1, false",
		"5, false",
		"0, false",
		"-4, false",
		"2101, false"
	})
	 void testValidNumericInputs(String input, boolean expected) {
		try {
			boolean result = leapYear.isLeapYear(input);
			assertEquals(expected, result, "Incorrect leap year evaluation for input: " + input);
		} catch (Exception e) {
			fail("Unexpected exception for input \"" + input + "\": " + e.getMessage());
		}
	}

	@DisplayName("Test null input throws NullPointerException")
	@Test
	 void testNullInput() {
		try {
			assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null),
				"Expected NullPointerException for null input");
		} catch (Exception e) {
			fail("Unexpected exception in testNullInput: " + e.getMessage());
		}
	}

	@DisplayName("Test invalid format inputs throw expected exceptions")
	@ParameterizedTest(name = "For input \"{0}\", expected exception is {1}")
	@CsvSource({
		"'', EmptyException",
		"'abc', NumberFormatException",
		"'   ', NumberFormatException"
	})
	 void testInvalidFormatInputs(String input, String expectedExceptionName) {
		try {
			Class<? extends Throwable> expectedException = getExceptionClass(expectedExceptionName);
			assertThrows(expectedException, () -> leapYear.isLeapYear(input),
				"Expected " + expectedExceptionName + " for input: \"" + input + "\"");
		} catch (Exception e) {
			fail("Unexpected exception in testInvalidFormatInputs for input \"" + input + "\": " + e.getMessage());
		}
	}

	 Class<? extends Throwable> getExceptionClass(String exceptionName) {
		if ("NullPointerException".equals(exceptionName)) {
			return NullPointerException.class;
		} else if ("EmptyException".equals(exceptionName)) {
			return EmptyException.class;
		} else if ("NumberFormatException".equals(exceptionName)) {
			return NumberFormatException.class;
		} else {
			return Throwable.class;
		}
	}

	@AfterEach
	 void tearDown() {
		// Cleanup after each test if needed.
	}

	@AfterAll
	 static void tearDownAll() {
		// Cleanup after all tests if needed.
	}
}