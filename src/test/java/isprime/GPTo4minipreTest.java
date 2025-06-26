package isprime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;





class GPTo4minipreTest {

    @Test
    @DisplayName("Null args should throw MissingArgumentException")
    void givenNullArgs_whenIsPrime_thenMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null));
    }

    @Test
    @DisplayName("More than one argument should throw Only1ArgumentException")
    void givenMultipleArgs_whenIsPrime_thenOnly1ArgumentException() {
        assertThrows(Only1ArgumentException.class,
                     () -> IsPrime.isPrime(new String[]{"5", "7"}));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "-0.5", "0.5"})
    @DisplayName("Non-positive or truncated-to-zero inputs should throw NoPositiveNumberException")
    void givenNonPositiveInputs_whenIsPrime_thenNoPositiveNumberException(String input) {
        assertThrows(NoPositiveNumberException.class,
                     () -> IsPrime.isPrime(new String[]{input}));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "", "1.2.3"})
    @DisplayName("Non-numeric inputs should throw NoPositiveNumberException")
    void givenNonNumericInputs_whenIsPrime_thenNoPositiveNumberException(String input) {
        assertThrows(NoPositiveNumberException.class,
                     () -> IsPrime.isPrime(new String[]{input}));
    }

    @Test
    @DisplayName("Input \"1\" should return true (implementation-specific)")
    void givenOne_whenIsPrime_thenReturnsTrue() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"1"});
            assertTrue(result, "1 should be treated as prime by implementation");
        } catch (Exception e) {
            fail("Unexpected exception for input \"1\": " + e.getClass().getSimpleName());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "3", "5", "7", "11", "13", "17", "19", "23"})
    @DisplayName("Valid prime numbers should return true")
    void givenPrimeNumbers_whenIsPrime_thenReturnsTrue(String input) {
        try {
            boolean result = IsPrime.isPrime(new String[]{input});
            assertTrue(result, input + " should be prime");
        } catch (Exception e) {
            fail("Unexpected exception for prime input \"" + input + "\": " + e.getClass().getSimpleName());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "6", "8", "9", "12", "15", "21", "25"})
    @DisplayName("Non-prime numbers greater than 1 should return false")
    void givenNonPrimeNumbers_whenIsPrime_thenReturnsFalse(String input) {
        try {
            boolean result = IsPrime.isPrime(new String[]{input});
            assertFalse(result, input + " should not be prime");
        } catch (Exception e) {
            fail("Unexpected exception for non-prime input \"" + input + "\": " + e.getClass().getSimpleName());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "7.9, true",
        "8.1, false",
        "2.99, true",
        "4.0, false"
    })
    @DisplayName("Floating inputs are truncated and then evaluated")
    void givenFloatingInputs_whenIsPrime_thenTruncateAndEvaluate(String input, boolean expected) {
        try {
            boolean result = IsPrime.isPrime(new String[]{input});
            assertEquals(expected, result,
                         () -> "Expected " + input + " truncated to " +
                               ((int) Float.parseFloat(input)) +
                               " to be " + expected);
        } catch (Exception e) {
            fail("Unexpected exception for floating input \"" + input + "\": " + e.getClass().getSimpleName());
        }
    }
}