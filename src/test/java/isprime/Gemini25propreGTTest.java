package isprime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Gemini25propreGTTest {

    @Test
    public void testIsPrimeWithPrimeNumber() throws Exception {
        assertTrue(IsPrime.isPrime(new String[]{"7"}));
    }

    @Test
    public void testIsPrimeWithCompositeNumber() throws Exception {
        assertFalse( IsPrime.isPrime(new String[]{"4"}));
    }

    @Test
    public void testIsPrimeWithOne() throws Exception {
        // The current implementation considers 1 as prime, which is mathematically incorrect
        // but follows the code's logic. A robust implementation would return false.
        assertTrue( IsPrime.isPrime(new String[]{"1"}));
    }

    @Test
    public void testIsPrimeWithTwo() throws Exception {
        assertTrue( IsPrime.isPrime(new String[]{"2"}));
    }

    @Test
    public void testIsPrimeWithLargePrime() throws Exception {
        assertTrue( IsPrime.isPrime(new String[]{"997"}));
    }

    @Test
    public void testIsPrimeWithLargeComposite() throws Exception {
        assertFalse( IsPrime.isPrime(new String[]{"999"}));
    }

    @Test
    public void testIsPrimeWithDecimalInput() throws Exception {
        // The implementation truncates the float, so "7.9" becomes 7.
        assertTrue( IsPrime.isPrime(new String[]{"7.9"}));
    }

    @Test
    public void testIsPrimeWithNullArgument() {
        assertThrows(MissingArgumentException.class, () -> {
            IsPrime.isPrime(null);
        });
    }

    @Test
    public void testIsPrimeWithMultipleArguments() {
        assertThrows(Only1ArgumentException.class, () -> {
            IsPrime.isPrime(new String[]{"5", "7"});
        });
    }

    @Test
    public void testIsPrimeWithNegativeNumber() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"-5"});
        });
    }

    @Test
    public void testIsPrimeWithZero() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"0"});
        });
    }

    @Test
    public void testIsPrimeWithNonNumericArgument() {
        assertThrows(NoPositiveNumberException.class, () -> {
            IsPrime.isPrime(new String[]{"abc"});
        });
    }
}