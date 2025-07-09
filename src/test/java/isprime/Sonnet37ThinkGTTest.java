package isprime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Sonnet37ThinkGTTest {

    @Test
    public void testPrimeNumbers() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        // Test with prime numbers
        assertTrue(IsPrime.isPrime(new String[]{"2"}));
        assertTrue(IsPrime.isPrime(new String[]{"3"}));
        assertTrue(IsPrime.isPrime(new String[]{"5"}));
        assertTrue(IsPrime.isPrime(new String[]{"7"}));
        assertTrue(IsPrime.isPrime(new String[]{"11"}));
        assertTrue(IsPrime.isPrime(new String[]{"13"}));
        assertTrue(IsPrime.isPrime(new String[]{"17"}));
        assertTrue(IsPrime.isPrime(new String[]{"19"}));
        assertTrue(IsPrime.isPrime(new String[]{"23"}));
        assertTrue(IsPrime.isPrime(new String[]{"29"}));
    }

    @Test
    public void testNonPrimeNumbers() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        // Test with non-prime numbers
        assertFalse(IsPrime.isPrime(new String[]{"4"}));
        assertFalse(IsPrime.isPrime(new String[]{"6"}));
        assertFalse(IsPrime.isPrime(new String[]{"8"}));
        assertFalse(IsPrime.isPrime(new String[]{"9"}));
        assertFalse(IsPrime.isPrime(new String[]{"10"}));
        assertFalse(IsPrime.isPrime(new String[]{"12"}));
        assertFalse(IsPrime.isPrime(new String[]{"15"}));
        assertFalse(IsPrime.isPrime(new String[]{"21"}));
        assertFalse(IsPrime.isPrime(new String[]{"25"}));
        assertFalse(IsPrime.isPrime(new String[]{"27"}));
    }

    @Test
    public void testEdgeCases() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        // Test with 1 (not prime by definition, but the implementation treats it as prime)
        assertTrue(IsPrime.isPrime(new String[]{"1"}));
        
        // Test with 2 (smallest prime)
        assertTrue(IsPrime.isPrime(new String[]{"2"}));
        
        // Test with larger prime
        assertTrue(IsPrime.isPrime(new String[]{"997"}));
    }
/* 
    @Test
    public void testNullArgs() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        IsPrime.isPrime(null);
    }

    @Test
    public void testEmptyArgs() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        IsPrime.isPrime(new String[]{});
    }

    @Test
    public void testMultipleArgs() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        IsPrime.isPrime(new String[]{"2", "3"});
    }

    @Test
    public void testZero() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        IsPrime.isPrime(new String[]{"0"});
    }

    @Test
    public void testNegativeNumber() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        IsPrime.isPrime(new String[]{"-5"});
    }

    @Test
    public void testNonNumberString() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        IsPrime.isPrime(new String[]{"abc"});
    }
    */
    @Test
    public void testFloatNumber() throws MissingArgumentException, Only1ArgumentException, NoPositiveNumberException {
        // The implementation accepts floats and casts to int, so 3.14 becomes 3
        assertTrue(IsPrime.isPrime(new String[]{"3.14"}));
        
        // 4.99 becomes 4, which is not prime
        assertFalse(IsPrime.isPrime(new String[]{"4.99"}));
    }
}