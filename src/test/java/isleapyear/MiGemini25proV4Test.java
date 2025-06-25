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
import org.junit.jupiter.params.provider.ValueSource;

class MiGemini25proV4Test {

    private LeapYear leapYear;

    @BeforeEach
    void setUp() {
        leapYear = new LeapYear();
    }

    // --- White box tests ---

    @Test
    @DisplayName("Test path for NullPointerException when year is null")
    void isLeapYear_whenYearIsNull_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> leapYear.isLeapYear(null));
    }

    @Test
    @DisplayName("Test path for EmptyException when year is empty")
    void isLeapYear_whenYearIsEmpty_thenThrowsEmptyException() {
        assertThrows(EmptyException.class, () -> leapYear.isLeapYear(""));
    }

    @Test
    @DisplayName("Test path for NumberFormatException when year is not a number")
    void isLeapYear_whenYearIsNotANumber_thenThrowsNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> leapYear.isLeapYear("abc"));
    }

    @ParameterizedTest
    @CsvSource({
            "2024, true",  // Path: All conditions met (num > 0 && num <= 2100 && num % 4 == 0)
            "2023, false", // Path: Condition failed (num % 4 == 0)
            "0,    false", // Path: Condition failed (num > 0)
            "-4,   false", // Path: Condition failed (num > 0)
            "2104, false"  // Path: Condition failed (num <= 2100)
    })
    @DisplayName("Test all internal decision paths and conditions")
    void isLeapYear_whenPathConditionsAreMet_thenReturnsExpected(String year, boolean expected) {
        try {
            assertEquals(expected, leapYear.isLeapYear(year));
        } catch (EmptyException e) {
            // This exception is not expected in this test case.
        }
    }

    // --- Black box tests ---

    @ParameterizedTest
    @ValueSource(strings = {"4", "1000", "2000"})
    @DisplayName("Test equivalence partition for valid leap years")
    void isLeapYear_withLeapYearEquivalencePartition_thenReturnsTrue(String year) {
        try {
            assertTrue(leapYear.isLeapYear(year));
        } catch (EmptyException e) {
            // This exception is not expected in this test case.
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"7", "1999", "2023"})
    @DisplayName("Test equivalence partition for valid non-leap years")
    void isLeapYear_withNonLeapYearEquivalencePartition_thenReturnsFalse(String year) {
        try {
            assertFalse(leapYear.isLeapYear(year));
        } catch (EmptyException e) {
            // This exception is not expected in this test case.
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0,    false", // Lower boundary (out of range)
            "1,    false", // Lower boundary (in range)
            "2099, false", // Upper boundary (in range)
            "2100, true",  // Upper boundary (in range)
            "2101, false"  // Upper boundary (out of range)
    })
    @DisplayName("Test boundary values around the valid range [1, 2100]")
    void isLeapYear_withBoundaryValues_thenReturnsExpected(String year, boolean expected) {
        try {
            assertEquals(expected, leapYear.isLeapYear(year));
        } catch (EmptyException e) {
            // This exception is not expected in this test case.
        }
    }
}