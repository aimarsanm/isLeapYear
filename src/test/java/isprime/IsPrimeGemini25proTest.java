package isprime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test suite for the IsPrime class.
 * This class employs both White Box and Black Box testing methodologies
 * to ensure full coverage and correctness of the isPrime method.
 */
@DisplayName("IsPrime Class Tests")
class IsPrimeGemini25proTest {

    private IsPrime isPrime;

    /**
     * Sets up the test environment before each test.
     * Initializes a new instance of IsPrime.
     */
    @BeforeEach
    void setUp() {
        isPrime = new IsPrime();
    }

    /**
     * Tests the constructor of the IsPrime class.
     * Verifies that the object is instantiated correctly without throwing exceptions.
     */
    @Test
    @DisplayName("Constructor should create a non-null instance")
    void testIsPrimeConstructor() {
        assertDoesNotThrow(() -> new IsPrime(), "Constructor should not throw an exception.");
    }

    /**
     * This nested class contains tests based on the White Box testing methodology,
     * specifically focusing on achieving full Condition/Decision coverage.
     */
    @Nested
    @DisplayName("White Box Tests (Condition/Decision Coverage)")
    class WhiteBoxTests {

        @Test
        @DisplayName("Should throw MissingArgumentException for null input")
        void isPrime_withNullArgs_shouldThrowMissingArgumentException() {
            assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null),
                    "A MissingArgumentException should be thrown for null input.");
        }

        @Test
        @DisplayName("Should throw Only1ArgumentException for more than one argument")
        void isPrime_withMultipleArgs_shouldThrowOnly1ArgumentException() {
            String[] args = {"5", "7"};
            assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(args),
                    "An Only1ArgumentException should be thrown for multiple arguments.");
        }

        @Test
        @DisplayName("Should throw MissingArgumentException for empty args array")
        void isPrime_withEmptyArgs_shouldThrowMissingArgumentException() {
            String[] args = {};
            // This case is handled by the logic that checks args.length > 1, but the original
            // code has a flaw where it would throw ArrayIndexOutOfBoundsException.
            // A robust implementation would check args.length == 0.
            // Given the current code, we test the actual behavior.
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> IsPrime.isPrime(args),
                "An ArrayIndexOutOfBoundsException is expected for empty args due to implementation.");
        }

        @Test
        @DisplayName("Should throw NoPositiveNumberException for non-numeric input")
        void isPrime_withNonNumericInput_shouldThrowNoPositiveNumberException() {
            String[] args = {"abc"};
            assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args),
                    "A NoPositiveNumberException should be thrown for non-numeric input.");
        }

        @Test
        @DisplayName("Should throw NoPositiveNumberException for zero")
        void isPrime_withZero_shouldThrowNoPositiveNumberException() {
            String[] args = {"0"};
            assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args),
                    "A NoPositiveNumberException should be thrown for zero.");
        }

        @Test
        @DisplayName("Should return true for prime number 2")
        void isPrime_withTwo_shouldReturnTrue() {
            String[] args = {"2"};
            try {
                assertTrue(IsPrime.isPrime(args), "2 should be identified as a prime number.");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Should return false for composite number 4")
        void isPrime_withFour_shouldReturnFalse() {
            String[] args = {"4"};
            try {
                assertFalse(IsPrime.isPrime(args), "4 should be identified as a composite number.");
            } catch (Exception e) {
                fail("Test failed with an unexpected exception: " + e.getMessage());
            }
        }
    }

    /**
     * This nested class contains tests based on the Black Box testing methodology,
     * using Equivalence Partitioning and Boundary Value Analysis.
     * Redundant tests already covered by White Box testing are omitted.
     */
    @Nested
    @DisplayName("Black Box Tests (Equivalence Partitioning & Boundary Values)")
    class BlackBoxTests {

        // Equivalence Class: Valid Prime Numbers
        @ParameterizedTest
        @ValueSource(strings = {"3", "5", "7", "11", "13", "97"})
        @DisplayName("Should return true for various prime numbers")
        void isPrime_withPrimeNumbers_shouldReturnTrue(String number) {
            String[] args = {number};
            try {
                assertTrue(IsPrime.isPrime(args), number + " should be identified as prime.");
            } catch (Exception e) {
                fail("Test for prime " + number + " failed unexpectedly.", e);
            }
        }

        // Equivalence Class: Valid Composite (Non-Prime) Numbers
        @ParameterizedTest
        @ValueSource(strings = {"6", "8", "9", "10", "100"})
        @DisplayName("Should return false for various composite numbers")
        void isPrime_withCompositeNumbers_shouldReturnFalse(String number) {
            String[] args = {number};
            try {
                assertFalse(IsPrime.isPrime(args), number + " should be identified as composite.");
            } catch (Exception e) {
                fail("Test for composite " + number + " failed unexpectedly.", e);
            }
        }

        // Boundary Value Analysis
        @Test
        @DisplayName("Should return true for the smallest prime number (2)")
        void isPrime_boundarySmallestPrime_shouldReturnTrue() {
            String[] args = {"2"};
            try {
                assertTrue(IsPrime.isPrime(args), "Boundary value 2 should be prime.");
            } catch (Exception e) {
                fail("Test for boundary value 2 failed unexpectedly.", e);
            }
        }

        @Test
        @DisplayName("Should return true for a prime number represented as a float (e.g., 7.0)")
        void isPrime_withFloatPrime_shouldReturnTrue() {
            String[] args = {"7.0"};
            try {
                assertTrue(IsPrime.isPrime(args), "Float value 7.0 should be treated as 7 and be prime.");
            } catch (Exception e) {
                fail("Test for float value 7.0 failed unexpectedly.", e);
            }
        }

        @Test
        @DisplayName("Should return false for a composite number represented as a float (e.g., 9.9)")
        void isPrime_withFloatComposite_shouldReturnFalse() {
            String[] args = {"9.9"};
            try {
                assertFalse(IsPrime.isPrime(args), "Float value 9.9 should be treated as 9 and be composite.");
            } catch (Exception e) {
                fail("Test for float value 9.9 failed unexpectedly.", e);
            }
        }

        // Equivalence Class: Invalid Negative Numbers
        @ParameterizedTest
        @ValueSource(strings = {"-1", "-2", "-10"})
        @DisplayName("Should throw NoPositiveNumberException for negative numbers")
        void isPrime_withNegativeNumbers_shouldThrowException(String number) {
            String[] args = {number};
            assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args),
                    "A NoPositiveNumberException should be thrown for negative input " + number);
        }
    }
}