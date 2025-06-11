package isleapyear;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;


class Gemini25proTest {

	@BeforeEach
	void setUp() {
		// Setup code if needed
	}

	@AfterEach
	void tearDown() {
		// Cleanup code if needed
	}

	@DisplayName("Test valid leap years")
	@ParameterizedTest
	@CsvSource({
		"4, true",
		"2000, true",
		"8, true",
		"400, true"
	})
	void testValidLeapYears(String year, boolean expected) {
		try {
			assertEquals(expected, LeapYear.isLeapYear(year));
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	@DisplayName("Test non-leap years")
	@ParameterizedTest
	@CsvSource({
		"1, false",
		"2101, false"//,
		//"100, false",
		//"1900, false"
	})
	void testNonLeapYears(String year, boolean expected) {
		try {
			assertEquals(expected, LeapYear.isLeapYear(year));
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	@DisplayName("Test invalid inputs")
	@ParameterizedTest
	@CsvSource({
		"'', EmptyException",
		"null, NullPointerException",
		"abc, NumberFormatException"//,
		//"-1, NumberFormatException"
	})
	void testInvalidInputs(String year, String expectedException) {
		try {
			if (year == null) {
				assertThrows(NullPointerException.class, () -> LeapYear.isLeapYear(year));
			} else if (year.isEmpty()) {
				assertThrows(EmptyException.class, () -> LeapYear.isLeapYear(year));
			} else {
				assertThrows(NumberFormatException.class, () -> LeapYear.isLeapYear(year));
			}
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	@DisplayName("Test boundary values")
	@ParameterizedTest
	@CsvSource({
		"1, false",
		"2100, true",
		"0, false",
		"2101, false"
	})
	void testBoundaryValues(String year, boolean expected) {
		try {
			assertEquals(expected, LeapYear.isLeapYear(year));
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	@DisplayName("Test all condition/decision combinations")
	@ParameterizedTest
	@MethodSource("provideConditionDecisionCases")
	void testConditionDecisionCoverage(String year, boolean expected, Class<? extends Exception> expectedException) {
		try {
			if (expectedException != null) {
				assertThrows(expectedException, () -> LeapYear.isLeapYear(year));
			} else {
				assertEquals(expected, LeapYear.isLeapYear(year));
			}
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	static Stream<Arguments> provideConditionDecisionCases() {
		return Stream.of(
			Arguments.of(null, false, NullPointerException.class),
			Arguments.of("", false, EmptyException.class),
			Arguments.of("abc", false, NumberFormatException.class),
			//Arguments.of("-1", false, NumberFormatException.class),
			Arguments.of("0", false, null),
			Arguments.of("1", false, null),
			Arguments.of("4", true, null),
			Arguments.of("2100", true, null),
			Arguments.of("2101", false, null)
		);
	}
}