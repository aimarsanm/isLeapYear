
package isprime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Sonnet37GTTest {

    @Test
    public void testIsPrimeWithPrimeNumber() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"7"};
        assertTrue(IsPrime.isPrime(args));
    }

    @Test
    public void testIsPrimeWithNonPrimeNumber() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"4"};
        assertFalse(IsPrime.isPrime(args));
    }
/*
    @Test
    public void testIsPrimeWithNullArgs() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        IsPrime.isPrime(null);
    }

    @Test
    public void testIsPrimeWithMultipleArgs() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"2", "3"};
        IsPrime.isPrime(args);
    }

    @Test
    public void testIsPrimeWithNegativeNumber() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"-5"};
        IsPrime.isPrime(args);
    }

    @Test
    public void testIsPrimeWithZero() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"0"};
        IsPrime.isPrime(args);
    }

    @Test
    public void testIsPrimeWithNonNumericInput() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"abc"};
        IsPrime.isPrime(args);
    }
 */
    @Test
    public void testIsPrimeWithLargePrimeNumber() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"97"};
        assertTrue(IsPrime.isPrime(args));
    }

    @Test
    public void testIsPrimeWithOne() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"1"};
        assertTrue(IsPrime.isPrime(args));
    }

    @Test
    public void testIsPrimeWithDecimalNumber() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        String[] args = {"7.5"};
        assertTrue(IsPrime.isPrime(args)); // Should test as 7
    }
}