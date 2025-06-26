package isleapyear;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GPTo3miniTest {

    private LeapYear leapYear;

    @BeforeAll
    @DisplayName("Setup for all tests")
    public static void initAll() {
        // Setup tasks before all tests run
    }

    @BeforeEach
    @DisplayName("Initialize LeapYear instance")
    public void init() {
        leapYear = new LeapYear();
    }

    @AfterEach
    @DisplayName("Clean up after test")
    public void tearDown() {
        // Clean up tasks after each test
    }

    @AfterAll
    @DisplayName("Clean up after all tests")
    public static void tearDownAll() {
        // Clean up tasks after all tests run
    }

    // MethodSource providing valid non-exception cases (Black Box and White Box - condition/decision)
    static Stream<Arguments> provideValidYearStrings() {
        return Stream.of(
            // Valid leap years within range: (year must be > 0, <=2100 and divisible by 4)
            Arguments.of("4", true),
            Arguments.of("1996", true),
            Arguments.of("2100", true), // boundary: exactly 2100, divisible by 4 per method's logic
            // Valid but non-leap years and out-of-bound conditions (false expected)
            Arguments.of("1", false),    // in-bound but not divisible by 4
            Arguments.of("3", false),    // in-bound but not divisible by 4
            Arguments.of("0", false),    // year 0 explicitly not leap due to num>0 condition
            Arguments.of("2101", false), // out-of-bound (>2100)
            Arguments.of("-4", false),   // negative value
            Arguments.of("1997", false)  // non-leap year
        );
    }

    @ParameterizedTest(name = "Test isLeapYear with input=\"{0}\" expecting result {1}")
    @MethodSource("provideValidYearStrings")
    @DisplayName("Parameterized Test: Valid String inputs for isLeapYear")
    public void testIsLeapYear_WithValidInputs(String input, boolean expected) {
        try {
            boolean result = leapYear.isLeapYear(input);
            assertEquals(expected, result, "The leap year evaluation for \"" + input + "\" failed.");
        } catch (Exception e) {
            fail("Unexpected exception for input \"" + input + "\": " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test isLeapYear with null input expecting NullPointerException")
    public void testIsLeapYear_NullInput() {
        try {
            assertThrows(NullPointerException.class, () -> {
                leapYear.isLeapYear(null);
            }, "Expected NullPointerException for null input.");
        } catch (Exception e) {
            fail("Unexpected exception thrown in testIsLeapYear_NullInput: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test isLeapYear with empty string expecting EmptyException")
    public void testIsLeapYear_EmptyString() {
        try {
            assertThrows(EmptyException.class, () -> {
                leapYear.isLeapYear("");
            }, "Expected EmptyException for empty string input.");
        } catch (Exception e) {
            fail("Unexpected exception thrown in testIsLeapYear_EmptyString: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test isLeapYear with non-numeric string expecting NumberFormatException")
    public void testIsLeapYear_NonNumericInput() {
        try {
            assertThrows(NumberFormatException.class, () -> {
                leapYear.isLeapYear("abc");
            }, "Expected NumberFormatException for non-numeric input.");
        } catch (Exception e) {
            fail("Unexpected exception thrown in testIsLeapYear_NonNumericInput: " + e.getMessage());
        }
    }
}