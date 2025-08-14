package isprime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for IsPrime")
class IsPrimeGPTo4minipreviewTest {

    @DisplayName("Should throw MissingArgumentException when args is null")
    @Test
    void whenArgsIsNull_thenMissingArgumentException() {
        assertThrows(MissingArgumentException.class,
                     () -> IsPrime.isPrime(null),
                     "Expected MissingArgumentException when args is null");
    }

    @DisplayName("Should throw Only1ArgumentException when more than one argument provided")
    @Test
    void whenMoreThanOneArgument_thenOnly1ArgumentException() {
        String[] inputs = { "2", "3" };
        assertThrows(Only1ArgumentException.class,
                     () -> IsPrime.isPrime(inputs),
                     "Expected Only1ArgumentException when more than one argument is provided");
    }

    @DisplayName("Should return true for prime or truncated‐prime inputs")
    @ParameterizedTest(name = "Input \"{0}\" is prime")
    @ValueSource(strings = { "2", "3", "2.9", "3.0" })
    void givenPrimeInputs_returnTrue(String input) {
        try {
            assertTrue(IsPrime.isPrime(new String[]{ input }),
                       () -> "Expected \"" + input + "\" to be prime");
        } catch (Exception e) {
            fail("Did not expect exception for valid prime input \"" + input + "\"", e);
        }
    }

    @DisplayName("Should return false for composite or truncated‐composite inputs")
    @ParameterizedTest(name = "Input \"{0}\" is not prime")
    @ValueSource(strings = { "4", "9", "16", "4.5" })
    void givenCompositeInputs_returnFalse(String input) {
        try {
            assertFalse(IsPrime.isPrime(new String[]{ input }),
                        () -> "Expected \"" + input + "\" to be not prime");
        } catch (Exception e) {
            fail("Did not expect exception for composite input \"" + input + "\"", e);
        }
    }

    @DisplayName("Should throw NoPositiveNumberException for non-positive or non-numeric inputs")
    @ParameterizedTest(name = "Input \"{0}\" throws NoPositiveNumberException")
    @ValueSource(strings = { "0", "-1", "0.9", "abc" })
    void whenNonPositiveOrNonNumericInput_thenNoPositiveNumberException(String input) {
        assertThrows(NoPositiveNumberException.class,
                     () -> IsPrime.isPrime(new String[]{ input }),
                     () -> "Expected NoPositiveNumberException for input \"" + input + "\"");
    }
}