package isprime;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;




class Sonnet37ThinkTest {

    @Test
    @DisplayName("Should throw MissingArgumentException when args is null")
    void testNullArgs() {
        assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null));
    }

    @Test
    @DisplayName("Should throw ArrayIndexOutOfBoundsException when args is empty")
    void testEmptyArgs() {
        try {
            IsPrime.isPrime(new String[]{});
            fail("Expected ArrayIndexOutOfBoundsException but none was thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Expected ArrayIndexOutOfBoundsException but got " + e.getClass().getName());
        }
    }

    @Test
    @DisplayName("Should throw Only1ArgumentException when args has more than one element")
    void testMultipleArgs() {
        assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(new String[]{"2", "3"}));
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when args contains non-numeric input")
    void testNonNumericInput() {
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[]{"abc"}));
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when args contains a negative number")
    void testNegativeNumber() {
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[]{"-5"}));
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when args contains zero")
    void testZero() {
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[]{"0"}));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "5", "7", "11", "13", "17", "19", "23", "29", "31", "97"})
    @DisplayName("Should return true for prime numbers and 1 (according to implementation)")
    void testPrimeNumbers(String input) {
        try {
            assertTrue(IsPrime.isPrime(new String[]{input}));
        } catch (MissingArgumentException | Only1ArgumentException | NoPositiveNumberException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "6", "8", "9", "10", "12", "14", "15", "16", "18", "20", "100"})
    @DisplayName("Should return false for non-prime numbers")
    void testNonPrimeNumbers(String input) {
        try {
            assertFalse(IsPrime.isPrime(new String[]{input}));
        } catch (MissingArgumentException | Only1ArgumentException | NoPositiveNumberException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should handle floating point numbers correctly")
    void testFloatingPointNumber() {
        try {
            // 5.7 should be treated as 5, which is prime
            assertTrue(IsPrime.isPrime(new String[]{"5.7"}));
            
            // 4.2 should be treated as 4, which is not prime
            assertFalse(IsPrime.isPrime(new String[]{"4.2"}));
        } catch (MissingArgumentException | Only1ArgumentException | NoPositiveNumberException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should handle edge case of large prime number correctly")
    void testLargePrimeNumber() {
        try {
            // 104729 is a prime number
            assertTrue(IsPrime.isPrime(new String[]{"104729"}));
        } catch (MissingArgumentException | Only1ArgumentException | NoPositiveNumberException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should handle edge case of large non-prime number correctly")
    void testLargeNonPrimeNumber() {
        try {
            // 104730 = 2 * 3 * 5 * 7 * 499, so it's not a prime number
            assertFalse(IsPrime.isPrime(new String[]{"104730"}));
        } catch (MissingArgumentException | Only1ArgumentException | NoPositiveNumberException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}