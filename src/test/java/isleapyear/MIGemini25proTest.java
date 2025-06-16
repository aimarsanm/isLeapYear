package isleapyear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

// Assuming EmptyException is defined in the isleapyear package
// import isleapyear.EmptyException; // Ensure this is present or defined

class MIGemini25proTest {
	LeapYear leapYear = new LeapYear();

    // White Box Tests: Specific Path and Exception Coverage

    @Test
    @DisplayName("isLeapYear(null) should throw NullPointerException")
    void testIsLeapYear_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            leapYear.isLeapYear(null);
        }, "Calling isLeapYear with null should throw NullPointerException.");
    }

    @Test
    @DisplayName("isLeapYear(\"\") should throw EmptyException")
    void testIsLeapYear_emptyInput_throwsEmptyException() {
        assertThrows(EmptyException.class, () -> {
            leapYear.isLeapYear("");
        }, "Calling isLeapYear with an empty string should throw EmptyException.");
    }

    // White Box & Black Box Combined: Parameterized Tests

    @ParameterizedTest(name = "Input ''{0}'' (invalid format) should throw NumberFormatException")
    @ValueSource(strings = {
            "abc",           // Non-numeric
            "12a3",          // Mixed numeric and non-numeric
            "2147483648",    // Integer.MAX_VALUE + 1
            "-2147483649",   // Integer.MIN_VALUE - 1
            "  ",            // Spaces only (Integer.parseInt throws NFE)
            "--1",           // Invalid number format
            "1.0",           // Decimal format
            "+",             // Sign only
            "-"              // Sign only
    })
    @DisplayName("isLeapYear with non-integer or out-of-range integer strings should throw NumberFormatException")
    void testIsLeapYear_invalidNumericFormat_throwsNumberFormatException(String yearStr) {
        assertThrows(NumberFormatException.class, () -> {
            leapYear.isLeapYear(yearStr);
        }, "Input '" + yearStr + "' should cause a NumberFormatException.");
    }

    @ParameterizedTest(name = "isLeapYear(''{0}'') should return {1}")
    @CsvSource({
            // White Box: MCDC cases for (num > 0 && num <= 2100 && num % 4 == 0)
            // A = num > 0, B = num <= 2100, C = num % 4 == 0
            "'2000', true",  // A=T, B=T, C=T -> Result: T
            "'0',    false", // A=F, B=T, C=T -> Result: F (A is false)
            "'2104', false", // A=T, B=F, C=T -> Result: F (B is false)
            "'2001', false", // A=T, B=T, C=F -> Result: F (C is false)

            // Black Box: Equivalence Partitioning & Boundary Value Analysis (non-redundant cases)
            // Lower boundary for valid range [1, 2100]
            "'1',    false", // Min valid range, not leap (1>0 T, 1<=2100 T, 1%4==0 F)
            "'4',    true",  // Min valid range + 3, leap (4>0 T, 4<=2100 T, 4%4==0 T)
            // Upper boundary for valid range [1, 2100]
            "'2100', true",  // Max valid range, leap (2100>0 T, 2100<=2100 T, 2100%4==0 T)
            "'2099', false", // Max valid range - 1, not leap (2099>0 T, 2099<=2100 T, 2099%4==0 F)
            // Other valid leap/non-leap years within range
            "'1996', true",  // Leap
            "'1997', false", // Non-leap
            "'2024', true",  // A common leap year
            "'2023', false", // A common non-leap year
            // Values just outside range (covered by MCDC logic, but explicit for clarity)
            "'-4',   false", // Below range (num > 0 is F)
            "'2102', false", // Above range (num <= 2100 is F), not div by 4
            // Inputs with leading/trailing spaces (Integer.parseInt handles these)
            "' 2000 ', true",
            "' 4 ',   true",
            "' 1 ',   false",
            "' 2099 ', false",
            "' 2100 ', true"
    })
    @DisplayName("isLeapYear should correctly determine leap status for various year inputs")
    void testIsLeapYear_validAndBoundaryYears_returnsCorrectBoolean(String yearStr, boolean expected) {
        try {
            boolean actual = leapYear.isLeapYear(yearStr);
            assertEquals(expected, actual,
                    "Year '" + yearStr + "' was expected to be " + (expected ? "a leap year" : "not a leap year") +
                    " but was " + (actual ? "a leap year" : "not a leap year") + ".");
        } catch (NullPointerException | EmptyException | NumberFormatException e) {
            // These specific test cases are designed not to throw exceptions.
            // If an exception is thrown, the test should fail.
            fail("Test case for year '" + yearStr + "' was not expected to throw " +
                 e.getClass().getSimpleName() + ", but it did: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exception
            fail("Test case for year '" + yearStr + "' threw an unexpected " +
                 e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}