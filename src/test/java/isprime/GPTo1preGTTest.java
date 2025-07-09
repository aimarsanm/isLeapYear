package isprime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GPTo1preGTTest {
    
    @Test
    public void testPrimeNumber() throws Exception {
        assertTrue(IsPrime.isPrime(new String[]{"5"}));
    }

    @Test
    public void testNonPrimeNumber() throws Exception {
        assertFalse(IsPrime.isPrime(new String[]{"6"}));
    }
/*
    @Test
    public void testMissingArgument() throws Exception {
        IsPrime.isPrime(null);
    }

    @Test
    public void testOnlyOneArgumentExpected() throws Exception {
        IsPrime.isPrime(new String[]{"2","3"});
    }

    @Test
    public void testNegativeNumber() throws Exception {
        IsPrime.isPrime(new String[]{"-5"});
    }

    @Test
    public void testNonNumeric() throws Exception {
        IsPrime.isPrime(new String[]{"abc"});
    }
 */
}