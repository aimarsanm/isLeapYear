package isstrobogrammic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GPTo1preTest {
    @BeforeAll
    static void beforeAll() {
        // Setup before all tests if needed
    }

    @AfterAll
    static void afterAll() {
        // Cleanup after all tests if needed
    }

    @BeforeEach
    void beforeEach() {
        // Setup before each test if needed
    }

    @AfterEach
    void afterEach() {
        // Cleanup after each test if needed
    }

    @Test
    @DisplayName("Check null input returns true")
    void testIsStrobogrammicReturnsTrueWhenNull() {
        try {
            boolean result = StrobogrammicNumber.isStrobogrammic(null);
            assertTrue(result, "Expected true for null input");
        } catch (Exception e) {
            fail("No exception should be thrown for null input");
        }
    }
    @Test
    @DisplayName("Check empty string returns true")
    void testIsStrobogrammicReturnsTrueWhenEmpty() {
        try {
            boolean result = StrobogrammicNumber.isStrobogrammic("");
            assertTrue(result, "Expected true for empty string");
        } catch (Exception e) {
            fail("No exception should be thrown for empty string");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0",    // Single char '0'
            "1",    // Single char '1'
            "8",    // Single char '8'
            "69",   // Classic strobogrammatic
            "96",   // Classic strobogrammatic
            "88",   // Double same strobogrammatic
            "609",  // Multi-char strobogrammatic
            "906",  // Multi-char strobogrammatic
            "1001", // Larger example
            "11"    // Two same digits
    })
    @DisplayName("Check valid strobogrammatic inputs return true")
    void testIsStrobogrammic_ValidStrobogrammaticInput(String input) {
        try {
            boolean result = StrobogrammicNumber.isStrobogrammic(input);
            assertTrue(result, "Expected true for strobogrammatic input: " + input);
        } catch (Exception e) {
            fail("No exception should be thrown for input: " + input);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "6",    // Single '6' not strobogrammatic by itself
            "2",    // '2' not in map
            "666",  // Middle '6' fails
            "01",   // '0' and '1' mismatch
            "9",    // Single '9' fails
            "90"    // '9' and '0' mismatch
    })
    @DisplayName("Check invalid strobogrammatic inputs return false")
    void testIsStrobogrammic_InvalidStrobogrammaticInput(String input) {
        try {
            boolean result = StrobogrammicNumber.isStrobogrammic(input);
            assertFalse(result, "Expected false for non-strobogrammatic input: " + input);
        } catch (Exception e) {
            fail("No exception should be thrown for input: " + input);
        }
    }
}