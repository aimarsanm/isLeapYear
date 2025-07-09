package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * This test class validates the functionality of the {@link StrobogrammicNumber} class.
 * The structure and test cases are inferred from the provided Surefire XML report.
 * A more conventional name for this class would be StrobogrammicNumberTest.
 */
class GeminiAssitCodeGTTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should return true for null or empty strings")
    void isStrobogrammic_NullOrEmpty_ReturnsTrue(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input),
                "Null or empty string should be considered strobogrammic.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "8"})
    @DisplayName("Should return true for single valid strobogrammic digits")
    void isStrobogrammic_SingleValidDigit_ReturnsTrue(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "3", "4", "5", "7", "6", "9"})
    @DisplayName("Should return false for single non-strobogrammic digits")
    void isStrobogrammic_SingleInvalidDigit_ReturnsFalse(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"69", "96", "11", "88", "101", "181", "609", "818", "986", "619", "8888", "1001", "6969"})
    @DisplayName("Should return true for valid strobogrammic numbers")
    void isStrobogrammic_ValidStrobogrammicNumbers_ReturnsTrue(String number) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"68", "98", "16", "89", "44", "33", "121", "691", "8080"})
    @DisplayName("Should return false for non-strobogrammic numbers")
    void isStrobogrammic_NonStrobogrammicNumbers_ReturnsFalse(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"161", "868", "191", "898"})
    @DisplayName("Should return false for odd-length numbers with invalid center digit (6 or 9)")
    void isStrobogrammic_InvalidCenterDigit_ReturnsFalse(String number) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(number));
    }

    @Test
    @DisplayName("Should handle long strobogrammic and non-strobogrammic numbers")
    void isStrobogrammic_LongNumbers() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("696969101696969"), "A long valid strobogrammic number");
        assertFalse(StrobogrammicNumber.isStrobogrammic("696969101696968"), "A long invalid strobogrammic number");
    }
}

