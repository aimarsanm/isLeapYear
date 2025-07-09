package isstrobogrammic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class GPTo3miniTest {

    @BeforeAll
    public static void initAll() {
        System.out.println("Starting tests for StrobogrammicNumber...");
    }

    @BeforeEach
    public void init() {
        // Setup resources if needed before each test.
    }

    @AfterEach
    public void tearDown() {
        // Cleanup resources if needed after each test.
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("Completed tests for StrobogrammicNumber.");
    }

    @Test
    @DisplayName("Test null input returns true")
    public void testNullInput() {
        try {
            boolean result = StrobogrammicNumber.isStrobogrammic(null);
            assertTrue(result, "Null input should return true.");
        } catch (Exception e) {
            fail("Exception should not be thrown for null input: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test empty string returns true")
    public void testEmptyString() {
        try {
            boolean result = StrobogrammicNumber.isStrobogrammic("");
            assertTrue(result, "Empty string should return true.");
        } catch (Exception e) {
            fail("Exception should not be thrown for empty string: " + e.getMessage());
        }
    }

    @ParameterizedTest(name = "Valid strobogrammic input \"{0}\" returns true")
    @CsvSource({
        "'0', true",
        "'1', true",
        "'8', true",
        "'11', true",
        "'69', true",
        "'96', true",
        "'88', true",
        "'818', true",
        "'101', true"
    })
    @DisplayName("Parameterized Test for valid strobogrammic numbers")
    public void testValidStrobogrammicNumbers(String input, boolean expected) {
        try {
            boolean result = StrobogrammicNumber.isStrobogrammic(input);
            assertEquals(expected, result, "Input: " + input + " should be strobogrammic.");
        } catch (Exception e) {
            fail("Exception should not be thrown for valid input '" + input + "': " + e.getMessage());
        }
    }

    @ParameterizedTest(name = "Invalid strobogrammic input \"{0}\" returns false")
    @CsvSource({
        "'2', false",
        "'3', false",
        "'10', false",
        "'19', false",
        "'161', false",
        "'a', false"
    })
    @DisplayName("Parameterized Test for invalid strobogrammic numbers")
    public void testInvalidStrobogrammicNumbers(String input, boolean expected) {
        try {
            boolean result = StrobogrammicNumber.isStrobogrammic(input);
            assertEquals(expected, result, "Input: " + input + " should not be strobogrammic.");
        } catch (Exception e) {
            fail("Exception should not be thrown for invalid input '" + input + "': " + e.getMessage());
        }
    }
}