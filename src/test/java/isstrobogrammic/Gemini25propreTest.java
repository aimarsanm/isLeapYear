
package isstrobogrammic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;




/**
 * Test suite for the StrobogrammicNumber class.
 * This class employs White Box and Black Box testing methodologies to ensure full coverage.
 */
class Gemini25propreTest {

    @DisplayName("Should return true for valid strobogrammic numbers")
    @ParameterizedTest(name = "Input: \"{0}\"")
    @ValueSource(strings = {"0", "1", "8", "11", "69", "88", "96", "101", "181", "609", "818", "986", "1001", "6889", "8008"})
    void isStrobogrammic_whenNumberIsStrobogrammic_shouldReturnTrue(String strobogrammicNumber) {
        try {
            assertTrue(StrobogrammicNumber.isStrobogrammic(strobogrammicNumber),
                    "Expected " + strobogrammicNumber + " to be strobogrammic, but it was not.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    @DisplayName("Should return false for non-strobogrammic numbers")
    @ParameterizedTest(name = "Input: \"{0}\"")
    @ValueSource(strings = {"2", "3", "4", "5", "7", "12", "61", "89", "44", "699", "123"})
    void isStrobogrammic_whenNumberIsNotStrobogrammic_shouldReturnFalse(String nonStrobogrammicNumber) {
        try {
            assertFalse(StrobogrammicNumber.isStrobogrammic(nonStrobogrammicNumber),
                    "Expected " + nonStrobogrammicNumber + " not to be strobogrammic, but it was.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should return true for a null input string")
    void isStrobogrammic_whenInputIsNull_shouldReturnTrue() {
        try {
            assertTrue(StrobogrammicNumber.isStrobogrammic(null), "A null input should be considered strobogrammic.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should return true for an empty input string")
    void isStrobogrammic_whenInputIsEmpty_shouldReturnTrue() {
        try {
            assertTrue(StrobogrammicNumber.isStrobogrammic(""), "An empty string should be considered strobogrammic.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Condition/Decision Coverage: Right char not in map")
    void isStrobogrammic_whenRightCharIsNotStrobogrammic_shouldReturnFalse() {
        // This test covers the case where `!map.containsKey(number.charAt(right))` is true.
        // Input: "62" -> right char '2' is not in the map.
        try {
            assertFalse(StrobogrammicNumber.isStrobogrammic("62"),
                    "Should return false when a character is not a valid strobogrammic digit.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Condition/Decision Coverage: Chars are not a valid pair")
    void isStrobogrammic_whenCharsAreNotInStrobogrammicPair_shouldReturnFalse() {
        // This test covers the case where `!map.containsKey` is false, but `number.charAt(left) != map.get(...)` is true.
        // Input: "68" -> right char '8' is in map, but map.get('8') is '8', which is not equal to left char '6'.
        try {
            assertFalse(StrobogrammicNumber.isStrobogrammic("68"),
                    "Should return false when characters do not form a valid strobogrammic pair.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }
}