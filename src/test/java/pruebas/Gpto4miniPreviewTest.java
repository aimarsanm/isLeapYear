package pruebas;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class Gpto4miniPreviewTest {

    @Test
    @DisplayName("Null args should throw MissingArgumentException")
    void testNullArgsThrowsMissingArgumentException() {
        assertThrows(MissingArgumentException.class,
            () -> IsPrime.isPrime(null),
            "Expected MissingArgumentException for null args");
    }

    @Test
    @DisplayName("More than one arg should throw Only1ArgumentException")
    void testMultipleArgsThrowsOnly1ArgumentException() {
        String[] args = {"2", "3"};
        assertThrows(Only1ArgumentException.class,
            () -> IsPrime.isPrime(args),
            "Expected Only1ArgumentException for args length > 1");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-3.5"})
    @DisplayName("Numeric strings ≤ 0 should throw NoPositiveNumberException")
    void testNonPositiveNumbersThrowNoPositiveNumberException(String input) {
        String[] args = {input};
        assertThrows(NoPositiveNumberException.class,
            () -> IsPrime.isPrime(args),
            () -> "Expected NoPositiveNumberException for input: " + input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "", "  ", "1.2.3"})
    @DisplayName("Invalid numeric format should throw NoPositiveNumberException")
    void testInvalidFormatThrowsNoPositiveNumberException(String input) {
        String[] args = {input};
        assertThrows(NoPositiveNumberException.class,
            () -> IsPrime.isPrime(args),
            () -> "Expected NoPositiveNumberException for malformed input: " + input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "3.0", "5.7"})
    @DisplayName("Inputs that map to prime numbers should return true")
    void testPrimeInputsReturnTrue(String input) {
        String[] args = {input};
        try {
            boolean result = IsPrime.isPrime(args);
            assertTrue(result, "Expected true for prime input: " + input);
        } catch (Exception e) {
            fail("No exception expected for prime input '" + input + "', but got: " + e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "4.0", "4.9", "9", "10"})
    @DisplayName("Inputs that map to composite numbers should return false")
    void testCompositeInputsReturnFalse(String input) {
        String[] args = {input};
        try {
            boolean result = IsPrime.isPrime(args);
            assertFalse(result, "Expected false for composite input: " + input);
        } catch (Exception e) {
            fail("No exception expected for composite input '" + input + "', but got: " + e);
        }
    }
}