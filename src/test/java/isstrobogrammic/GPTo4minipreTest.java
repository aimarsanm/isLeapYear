package isstrobogrammic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;




class GPTo4minipreTest {

    @Test
    @DisplayName("Null input should return true")
    void testNullInputReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null));
    }

    @Test
    @DisplayName("Empty string should return true")
    void testEmptyStringReturnsTrue() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""));
    }

    @ParameterizedTest(name = "Single-digit valid: \"{0}\"")
    @ValueSource(strings = {"0", "1", "8"})
    @DisplayName("Single-digit strobogrammatic numbers")
    void testSingleDigitValid(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest(name = "Single-digit invalid: \"{0}\"")
    @ValueSource(strings = {"6", "9", "2", "3", "4", "5", "7", "A"})
    @DisplayName("Single-digit non-strobogrammatic inputs")
    void testSingleDigitInvalid(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest(name = "Two-digit valid: \"{0}\"")
    @ValueSource(strings = {"11", "69", "88", "96", "00"})
    @DisplayName("Two-digit strobogrammatic numbers")
    void testTwoDigitValid(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest(name = "Two-digit invalid: \"{0}\"")
    @ValueSource(strings = {"10", "12", "80", "29", "68"})
    @DisplayName("Two-digit non-strobogrammatic numbers")
    void testTwoDigitInvalid(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest(name = "Multi-digit valid: \"{0}\"")
    @ValueSource(strings = {"1001", "619", "6969", "111111"})
    @DisplayName("Multi-digit strobogrammatic numbers")
    void testMultiDigitValid(String input) {
        assertTrue(StrobogrammicNumber.isStrobogrammic(input));
    }

    @ParameterizedTest(name = "Multi-digit invalid: \"{0}\"")
    @ValueSource(strings = {"1231", "1010", "962", "1002"})//"6699", 
    @DisplayName("Multi-digit non-strobogrammatic numbers")
    void testMultiDigitInvalid(String input) {
        assertFalse(StrobogrammicNumber.isStrobogrammic(input));
    }
}