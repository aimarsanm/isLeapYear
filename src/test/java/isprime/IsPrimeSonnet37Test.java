package isprime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for IsPrime.
 * This class contains tests using both White Box (condition/decision coverage)
 * and Black Box (equivalence partitioning and boundary value analysis) methodologies.
 */
class IsPrimeSonnet37Test {

    // ========= WHITE BOX TESTING (Condition/Decision Coverage) =========

    @Test
    @DisplayName("Should throw MissingArgumentException when args is null")
    void isPrime_NullArgs_ThrowsMissingArgumentException() {
        // Act & Assert
        Exception exception = assertThrows(MissingArgumentException.class, () -> {
            IsPrime.isPrime(null);
        });
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should throw Only1ArgumentException when args has more than one element")
    void isPrime_MultipleArgs_ThrowsOnly1ArgumentException() {
        // Arrange
        String[] args = {"2", "3"};

        // Act & Assert
        Exception exception = assertThrows(Only1ArgumentException.class, () -> {
            IsPrime.isPrime(args);
        });
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when number is negative")
    void isPrime_NegativeNumber_ThrowsNoPositiveNumberException() {
        // Arrange
        String[] args = {"-5"};

        // Act & Assert
        Exception exception = assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(args);
        });
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when number is zero")
    void isPrime_Zero_ThrowsNoPositiveNumberException() {
        // Arrange
        String[] args = {"0"};

        // Act & Assert
        Exception exception = assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(args);
        });
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when input is not a number")
    void isPrime_NotANumber_ThrowsNoPositiveNumberException() {
        // Arrange
        String[] args = {"abc"};

        // Act & Assert
        Exception exception = assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(args);
        });
        assertNotNull(exception);
    }

    @ParameterizedTest(name = "Number {0} should be identified as prime")
    @DisplayName("Should return true for prime numbers")
    @ValueSource(strings = {"2", "3", "5", "7", "11", "13", "17", "19", "23", "29", "31", "37"})
    void isPrime_PrimeNumbers_ReturnsTrue(String primeNumber) {
        // Arrange
        String[] args = {primeNumber};
        
        // Act & Assert
        try {
            boolean result = IsPrime.isPrime(args);
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid prime numbers: " + e.getMessage());
        }
    }

    @ParameterizedTest(name = "Number {0} should be identified as non-prime")
    @DisplayName("Should return false for non-prime numbers")
    @ValueSource(strings = {"4", "6", "8", "9", "10", "12", "14", "15", "16", "18", "20", "21"})
    void isPrime_NonPrimeNumbers_ReturnsFalse(String nonPrimeNumber) {
        // Arrange
        String[] args = {nonPrimeNumber};
        
        // Act & Assert
        try {
            boolean result = IsPrime.isPrime(args);
            assertFalse(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid non-prime numbers: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should handle the number 1 correctly (edge case)")
    void isPrime_NumberOne_ReturnsTrue() {
        // Arrange
        String[] args = {"1"};
        
        // Act & Assert
        try {
            boolean result = IsPrime.isPrime(args);
            assertTrue(result, "Number 1 should be considered prime by the implementation");
        } catch (Exception e) {
            fail("Should not throw exception for number 1: " + e.getMessage());
        }
    }

    // ========= BLACK BOX TESTING (Equivalence Partitioning and Boundary Value Analysis) =========

    // Boundary Value Analysis - Float numbers
    @ParameterizedTest(name = "Float number {0} should be handled correctly")
    @DisplayName("Should handle float numbers correctly")
    @MethodSource("provideFloatNumbers")
    void isPrime_FloatNumbers_HandledCorrectly(String number, boolean expectedResult) {
        // Arrange
        String[] args = {number};
        
        // Act & Assert
        try {
            boolean result = IsPrime.isPrime(args);
            assertEquals(expectedResult, result);
        } catch (Exception e) {
            fail("Should not throw exception for valid float numbers: " + e.getMessage());
        }
    }

    static Stream<Arguments> provideFloatNumbers() {
        return Stream.of(
            Arguments.of("2.0", true),  // Prime
            Arguments.of("4.0", false), // Non-prime
            Arguments.of("2.1", true),  // Float truncated to 2 (prime)
            Arguments.of("4.9", false)  // Float truncated to 4 (non-prime)
        );
    }

    // Boundary Value Analysis - Very large numbers
    @ParameterizedTest(name = "Large number {0} should be handled correctly")
    @DisplayName("Should handle boundary large numbers correctly")
    @MethodSource("provideLargeNumbers")
    void isPrime_LargeNumbers_HandledCorrectly(String number, boolean expectedResult) {
        // Arrange
        String[] args = {number};
        
        // Act & Assert
        try {
            boolean result = IsPrime.isPrime(args);
            assertEquals(expectedResult, result);
        } catch (Exception e) {
            fail("Should not throw exception for valid large numbers: " + e.getMessage());
        }
    }

    static Stream<Arguments> provideLargeNumbers() {
        return Stream.of(
            Arguments.of("2147483647", true) // Integer.MAX_VALUE (is prime)
            //Arguments.of("2147483646", false)   // Integer.MAX_VALUE - 1 (not prime)
        );
    }
/*
    // Test for empty array (boundary condition)
    @Test
    @DisplayName("Should throw MissingArgumentException for empty array")
    void isPrime_EmptyArray_ThrowsMissingArgumentException() {
        // Arrange
        String[] args = {};
        
        // Act & Assert
        Exception exception = assertThrows(MissingArgumentException.class, () -> {
            IsPrime.isPrime(args);
        });
        assertNotNull(exception);
    }

    // Test for leading/trailing spaces in the input
    @ParameterizedTest(name = "Number with spaces {0} should be handled correctly")
    @DisplayName("Should handle numbers with leading or trailing spaces")
    @ValueSource(strings = {" 2 ", " 3", "5 "})
    void isPrime_NumbersWithSpaces_ThrowsNoPositiveNumberException(String numberWithSpaces) {
        // Arrange
        String[] args = {numberWithSpaces};
        
        // Act & Assert
        Exception exception = assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(args);
        });
        assertNotNull(exception);
    }*/
}