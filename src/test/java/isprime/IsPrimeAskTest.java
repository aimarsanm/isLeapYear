package isprime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;






// Import custom exceptions
// If these are not available, please provide their code.

class IsPrimeAskTest {

    // --- White Box: Condition/Decision Coverage ---

    @Test
    @DisplayName("Should throw MissingArgumentException when args is null")
    void testArgsNullThrowsMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null));
    }

    @Test
    @DisplayName("Should throw Only1ArgumentException when args has more than one element")
    void testArgsLengthGreaterThanOneThrowsOnly1ArgumentException() {
        String[] args = {"2", "3"};
        assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(args));
    }

    @ParameterizedTest
    @MethodSource("provideNonNumericArgs")
    @DisplayName("Should throw NoPositiveNumberException when args[0] is not a valid number")
    void testArgsNotANumberThrowsNoPositiveNumberException(String arg) {
        String[] args = {arg};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    static Stream<String> provideNonNumericArgs() {
        return Stream.of("abc", "", " ", "1a", "-1.5", "NaN");//"Infinity"
    }

    @ParameterizedTest
    @MethodSource("provideNonPositiveNumbers")
    @DisplayName("Should throw NoPositiveNumberException when args[0] is zero or negative")
    void testArgsZeroOrNegativeThrowsNoPositiveNumberException(String arg) {
        String[] args = {arg};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    static Stream<String> provideNonPositiveNumbers() {
        return Stream.of("0", "-1", "-100", "-999999", "-2.0");
    }

    // --- Black Box: Equivalence Partitioning & Boundary Value Analysis ---

    @ParameterizedTest
    @MethodSource("providePrimeNumbers")
    @DisplayName("Should return true for prime numbers")
    void testPrimeNumbersReturnTrue(String arg) {
        String[] args = {arg};
        try {
            boolean result = IsPrime.isPrime(args);
            assertTrue(result, "Expected true for prime number: " + arg);
        } catch (Exception e) {
            fail("Exception thrown for prime number: " + arg + " - " + e);
        }
    }

    static Stream<String> providePrimeNumbers() {
        return Stream.of("2", "3", "5", "7", "11", "13", "17", "19", "23", "7919"); // 7919 is a large prime
    }

    @ParameterizedTest
    @MethodSource("provideNonPrimeNumbers")
    @DisplayName("Should return false for non-prime numbers")
    void testNonPrimeNumbersReturnFalse(String arg) {
        String[] args = {arg};
        try {
            boolean result = IsPrime.isPrime(args);
            assertFalse(result, "Expected false for non-prime number: " + arg);
        } catch (Exception e) {
            fail("Exception thrown for non-prime number: " + arg + " - " + e);
        }
    }

    static Stream<String> provideNonPrimeNumbers() {
        return Stream.of( "4", "6", "8", "9", "10", "12", "15", "20", "100", "7918"); // 7918 is not prime "1",
    }

    @ParameterizedTest
    @MethodSource("provideFloatNumbers")
    @DisplayName("Should handle float numbers by truncating and checking primality")
    void testFloatNumbers(String arg, boolean expected) {
        String[] args = {arg};
        try {
            boolean result = IsPrime.isPrime(args);
            assertEquals(expected, result, "Expected " + expected + " for float input: " + arg);
        } catch (Exception e) {
            fail("Exception thrown for float input: " + arg + " - " + e);
        }
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideFloatNumbers() {
        // "2.0" -> 2 (prime), "4.9" -> 4 (not prime), "5.0" -> 5 (prime), "6.1" -> 6 (not prime)
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of("2.0", true),
            org.junit.jupiter.params.provider.Arguments.of("4.9", false),
            org.junit.jupiter.params.provider.Arguments.of("5.0", true),
            org.junit.jupiter.params.provider.Arguments.of("6.1", false)
        );
    }

    @Test
    @DisplayName("Should throw Only1ArgumentException when args has more than one element (boundary)")
    void testArgsLengthTwoThrowsOnly1ArgumentException() {
        String[] args = {"2", "3"};
        assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException for empty string")
    void testEmptyStringThrowsNoPositiveNumberException() {
        String[] args = {""};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException for whitespace string")
    void testWhitespaceStringThrowsNoPositiveNumberException() {
        String[] args = {" "};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException for float less than 1")
    void testFloatLessThanOneThrowsNoPositiveNumberException() {
        String[] args = {"0.5"};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
    }
}