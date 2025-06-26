package isleapyear;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;





/**
 * Test suite for the LeapYear class.
 * This class employs White Box (Condition/Decision Coverage) and Black Box (Equivalence Partitioning, Boundary Value Analysis)
 * testing techniques to ensure full coverage and correctness of the isLeapYear method.
 */
public class Gemini25propreTest {

	private LeapYear leapYear;

	@BeforeEach
	void setUp() {
		this.leapYear = new LeapYear();
	}

	@DisplayName("Should return true for leap years within the valid range")
	@ParameterizedTest(name = "Year \"{0}\" should be a leap year")
	@ValueSource(strings = {"4", "2000", "2100"})
	void isLeapYear_WhenYearIsLeapAndInRange_ShouldReturnTrue(String year) {
		try {
			assertTrue(this.leapYear.isLeapYear(year), "Year " + year + " should be considered a leap year.");
		} catch (EmptyException | NumberFormatException e) {
			fail("Should not have thrown any exception for year: " + year, e);
		}
	}

	@DisplayName("Should return false for non-leap years or years outside the valid range")
	@ParameterizedTest(name = "Year \"{0}\" should not be a leap year")
	@ValueSource(strings = {"1", "2001", "2099", "2104", "0", "-4"})
	void isLeapYear_WhenYearIsNotLeapOrOutOfRange_ShouldReturnFalse(String year) {
		try {
			assertFalse(this.leapYear.isLeapYear(year), "Year " + year + " should not be considered a leap year.");
		} catch (EmptyException | NumberFormatException e) {
			fail("Should not have thrown any exception for year: " + year, e);
		}
	}

	@Test
	@DisplayName("Should throw NullPointerException for null input")
	void isLeapYear_WhenYearIsNull_ShouldThrowNullPointerException() {
		assertThrows(NullPointerException.class, () -> this.leapYear.isLeapYear(null),
				"Calling isLeapYear with null should throw NullPointerException.");
	}

	@Test
	@DisplayName("Should throw EmptyException for empty string input")
	void isLeapYear_WhenYearIsEmpty_ShouldThrowEmptyException() {
		assertThrows(EmptyException.class, () -> this.leapYear.isLeapYear(""),
				"Calling isLeapYear with an empty string should throw EmptyException.");
	}

	@DisplayName("Should throw NumberFormatException for non-numeric input")
	@ParameterizedTest(name = "Input \"{0}\" should throw NumberFormatException")
	@ValueSource(strings = {"abc", "2a", " ", "20.04"})
	void isLeapYear_WhenYearIsNotNumeric_ShouldThrowNumberFormatException(String invalidInput) {
		assertThrows(NumberFormatException.class, () -> this.leapYear.isLeapYear(invalidInput),
				"Calling isLeapYear with non-numeric string should throw NumberFormatException.");
	}
}