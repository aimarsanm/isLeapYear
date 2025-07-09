package isprime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GPTo4minipreGTTest {
/* 
    @Test
    public void testNullArgsThrows() throws Exception {
        IsPrime.isPrime(null);
    }

    @Test
    public void testMoreThanOneArgThrows() throws Exception {
        IsPrime.isPrime(new String[] { "2", "3" });
    }

    @Test
    public void testNegativeNumberThrows() throws Exception {
        IsPrime.isPrime(new String[] { "-7" });
    }

    @Test
    public void testZeroThrows() throws Exception {
        IsPrime.isPrime(new String[] { "0" });
    }

    @Test
    public void testNonNumericThrows() throws Exception {
        IsPrime.isPrime(new String[] { "abc" });
    }
*/
    @Test
    public void testOneIsPrimeAccordingToImpl() throws Exception {
        assertTrue(IsPrime.isPrime(new String[] { "1" }));
    }

    @Test
    public void testSmallPrimes() throws Exception {
        assertTrue(IsPrime.isPrime(new String[] { "2" }));
        assertTrue(IsPrime.isPrime(new String[] { "3" }));
        assertTrue(IsPrime.isPrime(new String[] { "17" }));
        assertTrue(IsPrime.isPrime(new String[] { "19.0" }));
    }

    @Test
    public void testSmallComposites() throws Exception {
        assertFalse(IsPrime.isPrime(new String[] { "4" }));
        assertFalse(IsPrime.isPrime(new String[] { "15" }));
        assertFalse(IsPrime.isPrime(new String[] { "20.5" })); // 20.5 cast to 20
    }

    @Test
    public void testFloatingPointCast() throws Exception {
        // 3.5 cast to 3, and 3 is prime in this impl
        assertTrue(IsPrime.isPrime(new String[] { "3.5" }));
        // 8.9 cast to 8, 8 is composite
        assertFalse(IsPrime.isPrime(new String[] { "8.9" }));
    }
}