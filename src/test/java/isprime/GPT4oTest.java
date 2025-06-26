package isprime;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

// File: src/test/java/isprime/IsPrimeTest.java




class GPT4oTest {

    @BeforeEach
    void setUp() {
        // Setup logic if needed
    }

    @AfterEach
    void tearDown() {
        // Cleanup logic if needed
    }

    @DisplayName("Test MissingArgumentException when args is null")
    @Test
    void testMissingArgumentException() {
        String[] args = null;
        Exception exception = assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(args));
        assertEquals(MissingArgumentException.class, exception.getClass());
    }

    @DisplayName("Test Only1ArgumentException when args has more than one element")
    @ParameterizedTest
    @CsvSource({
        "'1,2'",
        "'3,5,7'",
        "'11,13,17'"
    })
    void testOnly1ArgumentException(String input) {
        String[] args = input.split(",");
        Exception exception = assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(args));
        assertEquals(Only1ArgumentException.class, exception.getClass());
    }

    @DisplayName("Test NoPositiveNumberException for invalid inputs")
    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "abc", "-5.5"})
    void testNoPositiveNumberException(String input) {
        String[] args = {input};
        Exception exception = assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(args));
        assertEquals(NoPositiveNumberException.class, exception.getClass());
    }

    @DisplayName("Test isPrime for valid prime numbers")
    @ParameterizedTest
    @ValueSource(strings = {"2", "3", "5", "7", "11", "13", "17", "19"})
    void testIsPrimeWithPrimes(String input) {
        String[] args = {input};
        try {
            assertTrue(IsPrime.isPrime(args));
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @DisplayName("Test isPrime for valid non-prime numbers")
    @ParameterizedTest
    @ValueSource(strings = {"4", "6", "8", "9", "10", "12", "14", "15"})
    void testIsPrimeWithNonPrimes(String input) {
        String[] args = {input};
        try {
            assertFalse(IsPrime.isPrime(args));
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @DisplayName("Test isPrime for boundary values")
    @ParameterizedTest
    @CsvSource({
        //"1,false", // 1 is not prime
        "2,true",  // Smallest prime
        "3,true",  // Next prime
        "4,false"  // Smallest non-prime > 2
    })
    void testIsPrimeBoundaryValues(String input, boolean expected) {
        String[] args = {input};
        try {
            assertEquals(expected, IsPrime.isPrime(args));
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }
}