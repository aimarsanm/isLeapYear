package isprime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;




@DisplayName("IsPrime Tests with White Box and Black Box methods")
public class GPTo3miniTest {

    @BeforeAll
    static void initAll() {
        // Global initialization if needed
    }

    @BeforeEach
    void init() {
        // Setup before each test if needed
    }

    @AfterEach
    void tearDown() {
        // Cleanup after each test if needed
    }

    @AfterAll
    static void tearDownAll() {
        // Global cleanup if needed
    }

    @Test
    @DisplayName("Test Null Argument - Should throw MissingArgumentException")
    void testNullArgumentThrowsMissingArgumentException() {
        try {
            Assertions.assertThrows(MissingArgumentException.class, () -> {
                IsPrime.isPrime(null);
            }, "Expected MissingArgumentException for null args");
        } catch (Exception e) {
            Assertions.fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Multiple Arguments - Should throw Only1ArgumentException")
    void testMultipleArgumentsThrowsOnly1ArgumentException() {
        try {
            String[] args = {"2", "3"};
            Assertions.assertThrows(Only1ArgumentException.class, () -> {
                IsPrime.isPrime(args);
            }, "Expected Only1ArgumentException for multiple args");
        } catch (Exception e) {
            Assertions.fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest(name = "Non-numeric input \"{0}\" should throw NoPositiveNumberException")
    @ValueSource(strings = {"", "abc", "3abc"})
    @DisplayName("Test Non-Numeric Inputs - Should throw NoPositiveNumberException")
    void testNonNumericInputsThrowsNoPositiveNumberException(String input) {
        try {
            String[] args = {input};
            Assertions.assertThrows(NoPositiveNumberException.class, () -> {
                IsPrime.isPrime(args);
            }, "Expected NoPositiveNumberException for non-numeric input: " + input);
        } catch (Exception e) {
            Assertions.fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest(name = "Non-positive input \"{0}\" should throw NoPositiveNumberException")
    @ValueSource(strings = {"0", "-1", "-10"})
    @DisplayName("Test Non-Positive Numeric Inputs - Should throw NoPositiveNumberException")
    void testNonPositiveInputsThrowsNoPositiveNumberException(String input) {
        try {
            String[] args = {input};
            Assertions.assertThrows(NoPositiveNumberException.class, () -> {
                IsPrime.isPrime(args);
            }, "Expected NoPositiveNumberException for non-positive input: " + input);
        } catch (Exception e) {
            Assertions.fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest(name = "Input \"{0}\" should be prime? Expected: {1}")
    @CsvSource({
        // Valid prime cases; note that "1" returns true due to function logic.
        "'1', true",
        "'2', true",
        "'3', true",
        "'3.5', true",  // 3.5 becomes 3 -> prime.
        // Composite cases:
        "'4', false",
        "'6', false",
        "'8', false",
        "'9', false",
        "'10', false"
    })
    @DisplayName("Test Valid Numeric Inputs - Prime or Composite")
    void testValidNumericInputs(String input, boolean expectedResult) {
        try {
            String[] args = {input};
            boolean result = IsPrime.isPrime(args);
            Assertions.assertEquals(expectedResult, result, "Unexpected result for input: " + input);
        } catch (MissingArgumentException | Only1ArgumentException | NoPositiveNumberException e) {
            Assertions.fail("Unexpected exception thrown for input \"" + input + "\": " + e.getMessage());
        }
    }
}