package isstrobogrammic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;





@DisplayName("Unit Tests for StrobogrammicNumber Class")
class GPT4oTest {

    @BeforeAll
    static void setup() {
        System.out.println("Starting StrobogrammicNumber tests...");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Finished StrobogrammicNumber tests.");
    }

    @BeforeEach
    void beforeEachTest() {
        System.out.println("Starting a new test...");
    }

    @AfterEach
    void afterEachTest() {
        System.out.println("Test completed.");
    }

    @ParameterizedTest
    @CsvSource({
      //  "null, true", // Null input
        "'', true",   // Empty string
        "'69', true", // Valid strobogrammic number
        "'96', true", // Valid strobogrammic number
        "'1001', true", // Valid strobogrammic number
        "'123', false", // Invalid strobogrammic number
        "'678', false", // Invalid strobogrammic number
        "'6', false",   // Single invalid character
        "'8', true",    // Single valid character
        "'1234567890', false" // Large invalid number
    })
    @DisplayName("Parameterized Test for isStrobogrammic Method")
    void testIsStrobogrammic(String input, boolean expected) {
        boolean result = StrobogrammicNumber.isStrobogrammic(input);
        assertEquals(expected, result, "Failed for input: " + input);
    }

    @Test
    @DisplayName("Test Null Input")
    void testNullInput() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(null), "Null input should return true.");
    }

    @Test
    @DisplayName("Test Empty String")
    void testEmptyString() {
        assertTrue(StrobogrammicNumber.isStrobogrammic(""), "Empty string should return true.");
    }

    @Test
    @DisplayName("Test Valid Strobogrammic Numbers")
    void testValidStrobogrammicNumbers() {
        assertTrue(StrobogrammicNumber.isStrobogrammic("69"), "69 should be strobogrammic.");
        assertTrue(StrobogrammicNumber.isStrobogrammic("96"), "96 should be strobogrammic.");
        assertTrue(StrobogrammicNumber.isStrobogrammic("1001"), "1001 should be strobogrammic.");
    }

    @Test
    @DisplayName("Test Invalid Strobogrammic Numbers")
    void testInvalidStrobogrammicNumbers() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("123"), "123 should not be strobogrammic.");
        assertFalse(StrobogrammicNumber.isStrobogrammic("678"), "678 should not be strobogrammic.");
    }

    @Test
    @DisplayName("Test Single Character Edge Cases")
    void testSingleCharacterEdgeCases() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("6"), "6 should not be strobogrammic.");
        assertTrue(StrobogrammicNumber.isStrobogrammic("8"), "8 should be strobogrammic.");
    }

    @Test
    @DisplayName("Test Large Invalid Number")
    void testLargeInvalidNumber() {
        assertFalse(StrobogrammicNumber.isStrobogrammic("1234567890"), "1234567890 should not be strobogrammic.");
    }
}