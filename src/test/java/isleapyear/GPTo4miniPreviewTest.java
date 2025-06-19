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




class Gpto4miniPreviewTest {

    LeapYear leapYear;

    @BeforeEach
    void setUp() {
        leapYear = new LeapYear();
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "8", "2100"})
    @DisplayName("Valid leap years within 1-2100 should return true")
    void testValidLeapYears(String input) {
        try {
            boolean result = leapYear.isLeapYear(input);
            assertTrue(result, "Expected true for leap year input: " + input);
        } catch (Exception e) {
            fail("Unexpected exception for valid input '" + input + "': " + e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-4", "5", "2101"})
    @DisplayName("Non-leap or out-of-range years should return false")
    void testNonLeapOrOutOfRangeYears(String input) {
        try {
            boolean result = leapYear.isLeapYear(input);
            assertFalse(result, "Expected false for non-leap or out-of-range input: " + input);
        } catch (Exception e) {
            fail("Unexpected exception for input '" + input + "': " + e);
        }
    }

    @Test
    @DisplayName("Empty string should throw EmptyException")
    void testEmptyStringThrowsEmptyException() {
        assertThrows(EmptyException.class,
                     () -> leapYear.isLeapYear(""),
                     "EmptyException expected for empty input");
    }

    @Test
    @DisplayName("Null input should throw NullPointerException")
    void testNullInputThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                     () -> leapYear.isLeapYear(null),
                     "NullPointerException expected for null input");
    }

    @Test
    @DisplayName("Non-numeric string should throw NumberFormatException")
    void testNonNumericThrowsNumberFormatException() {
        assertThrows(NumberFormatException.class,
                     () -> leapYear.isLeapYear("abc"),
                     "NumberFormatException expected for non-numeric input");
    }
}