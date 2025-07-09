package isleapyear;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class Gemini25propreGTTest {

    private LeapYear leapYear;

    @BeforeEach
    void setUp() {
        leapYear = new LeapYear();
    }

    @Test
    void testIsLeapYearWithValidLeapYear() throws EmptyException {
        assertTrue(leapYear.isLeapYear("2020"), "2020 should be a leap year");
    }

    @Test
    void testIsLeapYearWithValidNonLeapYear() throws EmptyException {
        assertFalse(leapYear.isLeapYear("2021"), "2021 should not be a leap year");
    }

    @Test
    void testIsLeapYearWithYearDivisibleBy100ButNot400() throws EmptyException {
        // The current implementation considers any year divisible by 4 as a leap year.
        // For example, 1900 is not a leap year by standard definition, but the code returns true.
        // This test verifies the implemented logic, not the Gregorian calendar rule.
        assertTrue(leapYear.isLeapYear("1900"), "1900 is considered a leap year by the current implementation");
    }

    @Test
    void testIsLeapYearWithYearDivisibleBy400() throws EmptyException {
        assertTrue(leapYear.isLeapYear("2000"), "2000 should be a leap year");
    }

    @Test
    void testIsLeapYearWithLowerBoundLeapYear() throws EmptyException {
        assertTrue(leapYear.isLeapYear("4"), "4 should be a leap year");
    }

    @Test
    void testIsLeapYearWithLowerBoundNonLeapYear() throws EmptyException {
        assertFalse(leapYear.isLeapYear("1"), "1 should not be a leap year");
    }

    @Test
    void testIsLeapYearWithUpperBoundLeapYear() throws EmptyException {
        assertTrue(leapYear.isLeapYear("2100"), "2100 should be a leap year by the current implementation");
    }

    @Test
    void testIsLeapYearWithYearZero() throws EmptyException {
        assertFalse(leapYear.isLeapYear("0"), "0 should not be a leap year");
    }

    @Test
    void testIsLeapYearWithNegativeYear() throws EmptyException {
        assertFalse(leapYear.isLeapYear("-4"), "Negative years should not be leap years");
    }

    @Test
    void testIsLeapYearWithYearGreaterThanUpperBound() throws EmptyException {
        assertFalse(leapYear.isLeapYear("2104"), "Years greater than 2100 should not be leap years");
    }

    @Test
    void testIsLeapYearWithNullInput() {
        assertThrows(NullPointerException.class, () -> {
            leapYear.isLeapYear(null);
        }, "Passing null should throw NullPointerException");
    }

    @Test
    void testIsLeapYearWithEmptyInput() {
        // Assuming EmptyException is a custom exception class in the same package
        assertThrows(EmptyException.class, () -> {
            leapYear.isLeapYear("");
        }, "Passing an empty string should throw EmptyException");
    }

    @Test
    void testIsLeapYearWithNonNumericInput() {
        assertThrows(NumberFormatException.class, () -> {
            leapYear.isLeapYear("abc");
        }, "Passing a non-numeric string should throw NumberFormatException");
    }

    @Test
    void testIsLeapYearWithDecimalInput() {
        assertThrows(NumberFormatException.class, () -> {
            leapYear.isLeapYear("2020.5");
        }, "Passing a decimal string should throw NumberFormatException");
    }
}