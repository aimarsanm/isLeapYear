package isprime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test suite for the IsPrime class.
 * This class uses White Box (Condition/Decision Coverage) and Black Box (Equivalence Partitioning, Boundary Value Analysis)
 * techniques to ensure 100% code coverage and correctness of the isPrime method.
 */
class Gemini25propreTest {

    // White Box & Black Box: Testing Exception Paths

    @Test
    @DisplayName("Should throw MissingArgumentException when the input array is null")
    void testIsPrime_whenArgsIsNull_thenThrowMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null),
                "A MissingArgumentException should be thrown for null input.");
    }

    @Test
    @DisplayName("Should throw Only1ArgumentException when more than one argument is provided")
    void testIsPrime_whenArgsLengthIsGreaterThanOne_thenThrowOnly1ArgumentException() {
        String[] args = {"5", "10"};
        assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(args),
                "An Only1ArgumentException should be thrown for multiple arguments.");
    }

    @Test
    @DisplayName("Should throw ArrayIndexOutOfBoundsException when the input array is empty")
    void testIsPrime_whenArgsIsEmpty_thenThrowArrayIndexOutOfBoundsException() {
        String[] args = {};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> IsPrime.isPrime(args),
                "An ArrayIndexOutOfBoundsException is expected for an empty array due to implementation logic.");
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when the argument is not a valid number")
    void testIsPrime_whenArgIsNotANumber_thenThrowNoPositiveNumberException() {
        String[] args = {"not-a-number"};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args),
                "A NoPositiveNumberException should be thrown for non-numeric input.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-100"})
    @DisplayName("Should throw NoPositiveNumberException for zero or negative numbers")
    void testIsPrime_whenNumberIsZeroOrNegative_thenThrowNoPositiveNumberException(String number) {
        String[] args = {number};
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args),
                "A NoPositiveNumberException should be thrown for zero or negative input.");
    }

    // White Box & Black Box: Testing Return Paths (True/False)

    @ParameterizedTest
    @ValueSource(strings = {"2", "3", "5", "7", "11", "97"})
    @DisplayName("Should return true for prime numbers")
    void testIsPrime_whenNumberIsPrime_thenReturnTrue(String number) {
        String[] args = {number};
        try {
            assertTrue(IsPrime.isPrime(args), "The number " + number + " should be identified as prime.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "6", "8", "9", "10", "100"})
    @DisplayName("Should return false for non-prime (composite) numbers")
    void testIsPrime_whenNumberIsNotPrime_thenReturnFalse(String number) {
        String[] args = {number};
        try {
            assertFalse(IsPrime.isPrime(args), "The number " + number + " should be identified as not prime.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    // Boundary Value Analysis & Edge Cases

    @Test
    @DisplayName("Should return true for the number 1 as per current implementation")
    void testIsPrime_whenNumberIsOne_thenReturnTrue() {
        String[] args = {"1"};
        try {
            assertTrue(IsPrime.isPrime(args),
                    "The number 1 should return true based on the loop condition (i < 1) being initially false.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should return true when input is a float whose integer part is prime")
    void testIsPrime_whenInputIsFloatAndIntegerPartIsPrime_thenReturnTrue() {
        String[] args = {"7.9"};
        try {
            assertTrue(IsPrime.isPrime(args),
                    "A float input should be truncated to its integer part, and 7 is prime.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should return false when input is a float whose integer part is not prime")
    void testIsPrime_whenInputIsFloatAndIntegerPartIsNotPrime_thenReturnFalse() {
        String[] args = {"8.2"};
        try {
            assertFalse(IsPrime.isPrime(args),
                    "A float input should be truncated to its integer part, and 8 is not prime.");
        } catch (Exception e) {
            fail("Test failed with an unexpected exception: " + e.getMessage());
        }
    }
}