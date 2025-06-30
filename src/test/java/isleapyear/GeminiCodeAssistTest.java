package isleapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test suite for the {@link LeapYear} class.
 * This class employs both White Box and Black Box testing methodologies
 * to ensure 100% code, condition, and decision coverage.
 */
class GeminiCodeAssistTest {

    private LeapYear leapYear;

    @BeforeEach
    void setUp() {
        leapYear = new LeapYear();
    }

    // -----------------------------------------------------------------------------------------
    // Black Box Testing: Equivalence Partitioning & Boundary Value Analysis
    // White Box Testing: These tests also cover the "true" path of the main decision.
    // -----------------------------------------------------------------------------------------

    @DisplayName("Should return true for years divisible by 4 within the valid range [1, 2100]")
    @ParameterizedTest(name = "Year {0} should be a leap year.")
    @ValueSource(strings = {"4", "1996", "2000", "2096", "2100"})
    void isLeapYear_whenYearIsLeap_shouldReturnTrue(String year) {
        try {
            assertTrue(leapYear.isLeapYear(year), "The year " + year + " should be identified as a leap year.");
        } catch (Exception e) {
            fail("Test failed unexpectedly for year " + year + ". No exception should have been thrown.", e);
        }
    }

    @DisplayName("Should return false for years not divisible by 4 or outside the valid range [1, 2100]")
    @ParameterizedTest(name = "Year {0} should not be a leap year.")
    @ValueSource(strings = {"1", "1999", "2001", "2099", "0", "-4", "2101", "2104"})
    void isLeapYear_whenYearIsNotLeap_shouldReturnFalse(String year) {
        try {
            assertFalse(leapYear.isLeapYear(year), "The year " + year + " should not be identified as a leap year.");
        } catch (Exception e) {
            fail("Test failed unexpectedly for year " + year + ". No exception should have been thrown.", e);
        }
    }

    // -----------------------------------------------------------------------------------------
    // White Box & Black Box Testing: Exception Paths
    // -----------------------------------------------------------------------------------------

    @Test
    @DisplayName("Should throw NullPointerException when the input year is null")
    void isLeapYear_whenYearIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> leapYear.isLeapYear(null),
                "A NullPointerException should be thrown for a null input.");
    }

    @Test
    @DisplayName("Should throw EmptyException when the input string is empty")
    void isLeapYear_whenYearIsEmpty_shouldThrowEmptyException() {
        assertThrows(EmptyException.class,
                () -> leapYear.isLeapYear(""),
                "An EmptyException should be thrown for an empty string input.");
    }

    @Test
    @DisplayName("Should throw NumberFormatException when the input is not a valid number")
    void isLeapYear_whenYearIsNotNumeric_shouldThrowNumberFormatException() {
        assertThrows(NumberFormatException.class,
                () -> leapYear.isLeapYear("not-a-year"),
                "A NumberFormatException should be thrown for a non-numeric string input.");
    }
}