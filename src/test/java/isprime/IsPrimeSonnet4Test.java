package isprime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IsPrime Class Tests")
class IsPrimeSonnet4Test {

    @Test
    @DisplayName("Should throw MissingArgumentException when args is null")
    void shouldThrowMissingArgumentExceptionWhenArgsIsNull() {
        assertThrows(MissingArgumentException.class, () -> {
            IsPrime.isPrime(null);
        });
    }
/* 
    @Test
    @DisplayName("Should throw MissingArgumentException when args is empty")
    void shouldThrowMissingArgumentExceptionWhenArgsIsEmpty() {
        assertThrows(MissingArgumentException.class, () -> {
            IsPrime.isPrime(new String[]{});
        });
    }
*/
    @Test
    @DisplayName("Should throw Only1ArgumentException when args has multiple elements")
    void shouldThrowOnly1ArgumentExceptionWhenArgsHasMultipleElements() {
        assertThrows(Only1ArgumentException.class, () -> {
            IsPrime.isPrime(new String[]{"2", "3"});
        });
    }

    @Test
    @DisplayName("Should throw Only1ArgumentException when args has three elements")
    void shouldThrowOnly1ArgumentExceptionWhenArgsHasThreeElements() {
        assertThrows(Only1ArgumentException.class, () -> {
            IsPrime.isPrime(new String[]{"2", "3", "5"});
        });
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when number is zero")
    void shouldThrowNoPositiveNumberExceptionWhenNumberIsZero() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"0"});
        });
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when number is negative")
    void shouldThrowNoPositiveNumberExceptionWhenNumberIsNegative() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"-1"});
        });
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when number is large negative")
    void shouldThrowNoPositiveNumberExceptionWhenNumberIsLargeNegative() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"-100"});
        });
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when input is not a number")
    void shouldThrowNoPositiveNumberExceptionWhenInputIsNotANumber() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"abc"});
        });
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when input is empty string")
    void shouldThrowNoPositiveNumberExceptionWhenInputIsEmptyString() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{""});
        });
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when input contains special characters")
    void shouldThrowNoPositiveNumberExceptionWhenInputContainsSpecialCharacters() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"2@3"});
        });
    }

    @Test
    @DisplayName("Should return true for number 1")
    void shouldReturnTrueForNumberOne() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"1"});
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should return true for number 2 (smallest prime)")
    void shouldReturnTrueForNumberTwo() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"2"});
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should return true for number 3 (prime)")
    void shouldReturnTrueForNumberThree() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"3"});
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should return false for number 4 (not prime)")
    void shouldReturnFalseForNumberFour() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"4"});
            assertFalse(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"5", "7", "11", "13", "17", "19", "23", "29", "31", "37"})
    @DisplayName("Should return true for prime numbers")
    void shouldReturnTrueForPrimeNumbers(String primeNumber) {
        try {
            boolean result = IsPrime.isPrime(new String[]{primeNumber});
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"6", "8", "9", "10", "12", "14", "15", "16", "18", "20", "21", "22", "24", "25", "26", "27", "28", "30"})
    @DisplayName("Should return false for composite numbers")
    void shouldReturnFalseForCompositeNumbers(String compositeNumber) {
        try {
            boolean result = IsPrime.isPrime(new String[]{compositeNumber});
            assertFalse(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should handle float input that converts to integer")
    void shouldHandleFloatInputThatConvertsToInteger() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"5.0"});
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should handle float input with decimals (truncated)")
    void shouldHandleFloatInputWithDecimals() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"5.9"});
            assertTrue(result); // 5.9 becomes 5, which is prime
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should handle large prime number")
    void shouldHandleLargePrimeNumber() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"97"});
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should handle large composite number")
    void shouldHandleLargeCompositeNumber() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"100"});
            assertFalse(result);
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should handle number with leading zeros")
    void shouldHandleNumberWithLeadingZeros() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"007"});
            assertTrue(result); // 007 becomes 7, which is prime
        } catch (Exception e) {
            fail("Should not throw exception for valid input");
        }
    }

    @Test
    @DisplayName("Should handle positive float that truncates to zero")
    void shouldHandlePositiveFloatThatTruncatesToZero() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"0.5"});
        });
    }

    @Test
    @DisplayName("Should handle negative float")
    void shouldHandleNegativeFloat() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"-2.5"});
        });
    }
}