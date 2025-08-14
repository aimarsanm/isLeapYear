package isstrobogrammic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StrobogrammicNumber Tests (White-Box & Black-Box)")
class StrobogrammicNumberGPTo4minipreviewTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "8", "11", "818" })
    @DisplayName("Should return true for null, empty, and valid strobogrammic numbers")
    void returnsTrueForValidOrEmptyOrNull(String input) {
        try {
            assertTrue(
                StrobogrammicNumber.isStrobogrammic(input),
                () -> "Expected true for input: " + input
            );
        } catch (Exception e) {
            fail("Unexpected exception for input '" + input + "': " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "2", "19" })
    @DisplayName("Should return false for non-strobogrammic numbers")
    void returnsFalseForInvalidNumbers(String input) {
        try {
            assertFalse(
                StrobogrammicNumber.isStrobogrammic(input),
                () -> "Expected false for input: " + input
            );
        } catch (Exception e) {
            fail("Unexpected exception for input '" + input + "': " + e.getMessage());
        }
    }
}