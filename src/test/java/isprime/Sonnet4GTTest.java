package isprime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Sonnet4GTTest {
    /*
    @Test
    public void testNullArguments() throws Exception {
        IsPrime.isPrime(null);
    }
    
    @Test
    public void testMultipleArguments() throws Exception {
        IsPrime.isPrime(new String[]{"2", "3"});
    }
    
    @Test
    public void testNegativeNumber() throws Exception {
        IsPrime.isPrime(new String[]{"-5"});
    }
    
    @Test
    public void testZero() throws Exception {
        IsPrime.isPrime(new String[]{"0"});
    }
    
    @Test
    public void testInvalidString() throws Exception {
        IsPrime.isPrime(new String[]{"abc"});
    }
     */
    @Test
    public void testPrimeNumber2() throws Exception {
        assertTrue(IsPrime.isPrime(new String[]{"2"}));
    }
    
    @Test
    public void testPrimeNumber3() throws Exception {
        assertTrue(IsPrime.isPrime(new String[]{"3"}));
    }
    
    @Test
    public void testPrimeNumber7() throws Exception {
        assertTrue(IsPrime.isPrime(new String[]{"7"}));
    }
    
    @Test
    public void testCompositeNumber4() throws Exception {
        assertFalse(IsPrime.isPrime(new String[]{"4"}));
    }
    
    @Test
    public void testCompositeNumber9() throws Exception {
        assertFalse(IsPrime.isPrime(new String[]{"9"}));
    }
    
    @Test
    public void testOne() throws Exception {
        assertTrue(IsPrime.isPrime(new String[]{"1"}));
    }
    
    @Test
    public void testFloatInput() throws Exception {
        assertTrue(IsPrime.isPrime(new String[]{"7.5"}));
    }
   /* 
    @Test
    public void testEmptyArray() throws Exception {
        IsPrime.isPrime(new String[]{});
    } */
}