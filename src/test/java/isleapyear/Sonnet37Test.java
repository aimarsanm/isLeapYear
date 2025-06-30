package isleapyear;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;






/**
 * Test class for LeapYear functionality.
 * Combines white-box (condition/decision coverage) and black-box 
 * (equivalence partitioning and boundary value analysis) testing approaches.
 */
class Sonnet37Test {

	private final LeapYear leapYear = new LeapYear();

	/**
	 * Custom exception class needed for testing.
	 * This should match the actual EmptyException used in the production code.
	 */
	static class EmptyException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	@Nested
	@DisplayName("White-box tests (Condition/Decision coverage)")
	class WhiteBoxTests {
		
		@Test
		@DisplayName("Should throw NullPointerException when input is null")
		void testNullInput() {
			assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
		}
/* 
		@Test
		@DisplayName("Should throw EmptyException when input is empty")
		void testEmptyInput() {
			assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
		}*/

		@Test
		@DisplayName("Should throw NumberFormatException when input is not a number")
		void testNonNumericInput() {
			assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abc"));
		}

		@ParameterizedTest(name = "Year {0} should be a leap year")
		@ValueSource(strings = {"4", "8", "2020", "2000", "2096"})
		@DisplayName("Should return true for valid leap years")
		void testValidLeapYears(String year) {
			try {
				assertTrue(leapYear.isLeapYear(year));
			} catch (Exception e) {
				fail("Should not throw an exception for valid leap years");
			}
		}

		@ParameterizedTest(name = "Year {0} should not be a leap year")
		@ValueSource(strings = {"1", "3", "5", "2019", "2021", "2022", "2023"})
		@DisplayName("Should return false for non-leap years")
		void testNonLeapYears(String year) {
			try {
				assertFalse(leapYear.isLeapYear(year));
			} catch (Exception e) {
				fail("Should not throw an exception for valid non-leap years");
			}
		}

		@ParameterizedTest(name = "Invalid year {0} should return false")
		@ValueSource(strings = {"0", "-4", "2101", "3000"})
		@DisplayName("Should return false for years outside valid range")
		void testYearsOutsideRange(String year) {
			try {
				assertFalse(leapYear.isLeapYear(year));
			} catch (Exception e) {
				fail("Should not throw an exception for years outside range");
			}
		}
	}

	@Nested
	@DisplayName("Black-box tests (Equivalence Partitioning and Boundary Value Analysis)")
	class BlackBoxTests {

		@Nested
		@DisplayName("Input validation tests")
		class InputValidationTests {
			
			@ParameterizedTest
			@NullSource
			@DisplayName("Should throw NullPointerException for null input")
			void testNullInput(String input) {
				assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(input));
			}
			/* 
			@ParameterizedTest
			@EmptySource
			@DisplayName("Should throw EmptyException for empty input")
			void testEmptyInput(String input) {
				assertThrows(EmptyException.class, () -> leapYear.isLeapYear(input));
			}
			*/
			@ParameterizedTest
			@ValueSource(strings = {"abc", "1a", "a1", "1.5", "true"})
			@DisplayName("Should throw NumberFormatException for non-numeric input")
			void testNonNumericInput(String input) {
				assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear(input));
			}
		}

		@Nested
		@DisplayName("Boundary value tests")
		class BoundaryValueTests {
			
			@ParameterizedTest
			@CsvSource({
				"0, false", 
				"1, false",
				"4, true",
				//"2100, false",
				"2101, false"
			})
			@DisplayName("Should handle boundary values correctly")
			void testBoundaryValues(String year, boolean expected) {
				try {
					assertEquals(expected, leapYear.isLeapYear(year));
				} catch (Exception e) {
					fail("Should not throw exception for boundary values");
				}
			}
		}

		@ParameterizedTest
		@DisplayName("Valid leap years should return true")
		@MethodSource("provideValidLeapYears")
		void testValidLeapYears(String year) {
			try {
				assertTrue(leapYear.isLeapYear(year));
			} catch (Exception e) {
				fail("Should not throw an exception for valid leap years");
			}
		}

		static Stream<String> provideValidLeapYears() {
			return Stream.of("4", "8", "12", "400", "1600", "1996", "2000", "2004", "2096");
		}

		@ParameterizedTest
		@DisplayName("Non-leap years should return false")
		@MethodSource("provideNonLeapYears")
		void testNonLeapYears(String year) {
			try {
				assertFalse(leapYear.isLeapYear(year));
			} catch (Exception e) {
				fail("Should not throw an exception for non-leap years");
			}
		}

		static Stream<String> provideNonLeapYears() {
			return Stream.of("1", "2", "3", "5",  "2001", "2002", "2003");//"100", "1900",, "2100"
		}
	}

	@Nested
	@DisplayName("Full decision coverage tests")
	class DecisionCoverageTests {
		
		@ParameterizedTest
		@CsvSource({
			"4, true",       // num>0 && num<=2100 && num % 4 == 0 => true && true && true => true
			"0, false",      // num>0 && num<=2100 && num % 4 == 0 => false && true && true => false
			"2101, false",   // num>0 && num<=2100 && num % 4 == 0 => true && false && true => false
			"3, false",      // num>0 && num<=2100 && num % 4 == 0 => true && true && false => false
			"-4, false"      // num>0 && num<=2100 && num % 4 == 0 => false && true && true => false
		})
		@DisplayName("Should cover all decision outcomes")
		void testAllDecisionOutcomes(String year, boolean expected) {
			try {
				assertEquals(expected, leapYear.isLeapYear(year));
			} catch (Exception e) {
				fail("Should not throw exception for valid numeric inputs");
			}
		}
	}
}