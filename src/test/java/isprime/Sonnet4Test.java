package isprime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;





class Sonnet4Test {

    @Test
    @DisplayName("Should throw MissingArgumentException when args is null")
    void shouldThrowMissingArgumentExceptionWhenArgsIsNull() {
        assertThrows(MissingArgumentException.class, () -> IsPrime.isPrime(null));
    }
/* 
    @Test
    @DisplayName("Should throw MissingArgumentException when args is empty")
    void shouldThrowMissingArgumentExceptionWhenArgsIsEmpty() {
        try {
            String[] emptyArgs = {};
            boolean result = IsPrime.isPrime(emptyArgs);
            fail("Expected MissingArgumentException but method completed successfully");
        } catch (MissingArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Expected MissingArgumentException but got: " + e.getClass().getSimpleName());
        }
    }
*/
    @Test
    @DisplayName("Should throw Only1ArgumentException when args has multiple elements")
    void shouldThrowOnly1ArgumentExceptionWhenArgsHasMultipleElements() {
        assertThrows(Only1ArgumentException.class, () -> IsPrime.isPrime(new String[]{"2", "3"}));
    }

    @Test
    @DisplayName("Should throw Only1ArgumentException when args has three elements")
    void shouldThrowOnly1ArgumentExceptionWhenArgsHasThreeElements() {
        try {
            String[] multipleArgs = {"2", "3", "5"};
            boolean result = IsPrime.isPrime(multipleArgs);
            fail("Expected Only1ArgumentException but method completed successfully");
        } catch (Only1ArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Expected Only1ArgumentException but got: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when number is zero")
    void shouldThrowNoPositiveNumberExceptionWhenNumberIsZero() {
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[]{"0"}));
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when number is negative")
    void shouldThrowNoPositiveNumberExceptionWhenNumberIsNegative() {
        try {
            String[] negativeArgs = {"-5"};
            boolean result = IsPrime.isPrime(negativeArgs);
            fail("Expected NoPositiveNumberException but method completed successfully");
        } catch (NoPositiveNumberException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Expected NoPositiveNumberException but got: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when input is not a valid number")
    void shouldThrowNoPositiveNumberExceptionWhenInputIsNotValidNumber() {
        assertThrows(NoPositiveNumberException.class, () -> IsPrime.isPrime(new String[]{"abc"}));
    }

    @Test
    @DisplayName("Should throw NoPositiveNumberException when input is empty string")
    void shouldThrowNoPositiveNumberExceptionWhenInputIsEmptyString() {
        try {
            String[] emptyStringArgs = {""};
            boolean result = IsPrime.isPrime(emptyStringArgs);
            fail("Expected NoPositiveNumberException but method completed successfully");
        } catch (NoPositiveNumberException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Expected NoPositiveNumberException but got: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Should return true for number 1")
    void shouldReturnTrueForNumberOne() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"1"});
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Should return true for number 2")
    void shouldReturnTrueForNumberTwo() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"2"});
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"3", "5", "7", "11", "13", "17", "19", "23"})
    @DisplayName("Should return true for prime numbers")
    void shouldReturnTrueForPrimeNumbers(String primeNumber) {
        try {
            boolean result = IsPrime.isPrime(new String[]{primeNumber});
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception for prime number " + primeNumber + ": " + e.getClass().getSimpleName());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "6", "8", "9", "10", "12", "14", "15", "16", "18", "20"})
    @DisplayName("Should return false for composite numbers")
    void shouldReturnFalseForCompositeNumbers(String compositeNumber) {
        try {
            boolean result = IsPrime.isPrime(new String[]{compositeNumber});
            assertFalse(result);
        } catch (Exception e) {
            fail("Unexpected exception for composite number " + compositeNumber + ": " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Should handle float input that converts to integer")
    void shouldHandleFloatInputThatConvertsToInteger() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"7.0"});
            assertTrue(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Should handle float input with decimal part that gets truncated")
    void shouldHandleFloatInputWithDecimalPartThatGetsTruncated() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"7.9"});
            assertTrue(result); // 7.9 becomes 7 which is prime
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("Should return false for large composite number")
    void shouldReturnFalseForLargeCompositeNumber() {
        try {
            boolean result = IsPrime.isPrime(new String[]{"100"});
            assertFalse(result);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }
    }
}

// Exception classes needed for compilation
class MissingArgumentException extends Exception {
    public MissingArgumentException() {
        super();
    }
}

class Only1ArgumentException extends Exception {
    public Only1ArgumentException() {
        super();
    }
}

class NoPositiveNumberException extends Exception {
    public NoPositiveNumberException() {
        super();
    }
}