package isleapyear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link LeapYear}.
 * Utilizes White Box (Condition/Decision Coverage) and Black Box (Equivalence Partitioning, Boundary Value Analysis)
 * testing methods.
 */
class Gemini25proTest {

    private LeapYear leapYear;

    @BeforeEach
    void setUp() {
        leapYear = new LeapYear();
    }

    @Test
    @DisplayName("Test isLeapYear with null input, should throw NullPointerException")
    void testIsLeapYear_NullInput_ShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            leapYear.isLeapYear(null);
        }, "Calling isLeapYear with null should throw NullPointerException.");
    }

    @Test
    @DisplayName("Test isLeapYear with empty string input, should throw EmptyException")
    void testIsLeapYear_EmptyInput_ShouldThrowEmptyException() {
        assertThrows(EmptyException.class, () -> {
            leapYear.isLeapYear("");
        }, "Calling isLeapYear with an empty string should throw EmptyException.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "12.3", "  ", "Twenty", "1,000", "42L", "NaN", "Infinity", " 2000 "})
    @DisplayName("Test isLeapYear with various non-numeric string inputs, should throw NumberFormatException")
    void testIsLeapYear_NonNumericInput_ShouldThrowNumberFormatException(String invalidYear) {
        assertThrows(NumberFormatException.class, () -> {
            leapYear.isLeapYear(invalidYear);
        }, "Calling isLeapYear with non-numeric string '" + invalidYear + "' should throw NumberFormatException.");
    }

    @ParameterizedTest
    @MethodSource("provideLeapYearTestData")
    @DisplayName("Test isLeapYear with various numeric inputs for boolean outcomes")
    void testIsLeapYear_BooleanOutcomes(String year, boolean expectedResult) {
        try {
            boolean actualResult = leapYear.isLeapYear(year);
            assertEquals(expectedResult, actualResult,
                    "Year '" + year + "' evaluation did not match expected: " + expectedResult);
        } catch (Exception e) {
            fail("Test for year '" + year + "' failed due to unexpected exception: " + e.getMessage(), e);
        }
    }

    private static Stream<Arguments> provideLeapYearTestData() {
        return Stream.of(
                // MCDC Cases & Core Logic
                Arguments.of("2000", true),  // C1=T, C2=T, C3=T -> True (Leap year)
                Arguments.of("0", false),    // C1=F (num > 0 is false) -> False
                Arguments.of("2104", false), // C2=F (num <= 2100 is false) -> False
                Arguments.of("2001", false), // C3=F (num % 4 == 0 is false) -> False

                // Boundary Value Analysis for num > 0
                Arguments.of("1", false),    // Lower boundary (1 > 0 is T, 1 % 4 != 0)
                Arguments.of("-1", false),   // Below lower boundary (num > 0 is F)
                Arguments.of("-4", false),   // Negative, divisible by 4 (num > 0 is F)

                // Boundary Value Analysis for num <= 2100
                Arguments.of("2100", true),  // Upper boundary, leap (2100 % 4 == 0)
                Arguments.of("2099", false), // Below upper boundary, not leap (2099 % 4 != 0)
                Arguments.of("2101", false), // Above upper boundary (num <= 2100 is F)

                // Equivalence Partitioning: Valid Leap Years
                Arguments.of("1996", true),  // Typical leap year
                Arguments.of("4", true),     // Smallest positive leap year

                // Equivalence Partitioning: Valid Non-Leap Years within range
                Arguments.of("1997", false), // Typical non-leap year
                Arguments.of("3", false),    // Small positive non-leap year

                // Additional cases for robustness
                Arguments.of("+100", true),  // Parsable positive with sign
                Arguments.of("-50", false)   // Parsable negative
        );
    }
}